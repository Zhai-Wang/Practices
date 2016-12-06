package com.shmj.mouzhai.downloaddemo.services;

import android.content.Context;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.shmj.mouzhai.downloaddemo.db.ThreadPortImpl;
import com.shmj.mouzhai.downloaddemo.entities.FileInfo;
import com.shmj.mouzhai.downloaddemo.entities.ThreadInfo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 下载任务类
 * <p>
 * Created by Mouzhai on 2016/11/28.
 */

public class DownloadTask {
    private Context context;
    private FileInfo fileInfo;
    private ThreadPortImpl threadPortImpl;
    private Timer timer = new Timer();
    private Messenger messenger;

    private int finished = 0;
    private int threadCount = 1;//线程数量
    public boolean isPause = false;

    private List<DownloadThread> downloadThreads;//线程集合
    public static ExecutorService executorService = Executors.newCachedThreadPool();

    public DownloadTask(Context context, Messenger messenger, FileInfo fileInfo, int threadCount) {
        this.context = context;
        this.fileInfo = fileInfo;
        this.threadCount = threadCount;
        this.messenger = messenger;
        threadPortImpl = new ThreadPortImpl(context);
    }

    public void download() {
        //读取数据库的连接信息
        List<ThreadInfo> threadInfos = threadPortImpl.getThreads(fileInfo.getUrl());
        if (threadInfos.size() == 0) {
            //每个线程的下载进度
            int length = fileInfo.getLength() / threadCount;
            for (int i = 0; i < threadCount; i++) {
                //创建线程信息
                ThreadInfo threadInfo = new ThreadInfo(i, fileInfo.getUrl(), length * i,
                        (i + 1) * length - 1, 0);
                if (i == threadCount - 1) {
                    threadInfo.setEnd(fileInfo.getLength());
                }
                //添加线程信息
                threadInfos.add(threadInfo);
                //向数据库插入线程信息
                threadPortImpl.insertThread(threadInfo);
            }
        }
        //开始进行多线程下载
        downloadThreads = new ArrayList<>();
        for (ThreadInfo info : threadInfos) {
            DownloadThread downloadThread = new DownloadThread(info);
            DownloadTask.executorService.execute(downloadThread);
            //添加线程到集合中
            downloadThreads.add(downloadThread);
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //把下载进度发送给 Activity
                Message msgUpdate = new Message();
                msgUpdate.what = DownloadService.MSG_UPDATE;
                msgUpdate.arg1 = finished * 100 / fileInfo.getLength();
                msgUpdate.arg2 = fileInfo.getId();
                try {
                    messenger.send(msgUpdate);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }, 1000, 1000);
    }

    /**
     * 判断是否所有线程都下载完毕
     */
    private synchronized void checkAllThreadsIsFinished() {
        boolean allFinished = true;
        //遍历线程集合，检查线程是否执行完毕
        for (DownloadThread thread : downloadThreads) {
            if (!thread.isFinished) {
                allFinished = false;
                break;
            }
        }
        if (allFinished) {
            //取消定时器
            timer.cancel();
            //删除线程信息
            threadPortImpl.deleteThread(fileInfo.getUrl());
            //通知 UI 任务执行完毕
            Message msgStop = new Message();
            msgStop.what = DownloadService.MSG_STOP;
            msgStop.obj = fileInfo;
            try {
                messenger.send(msgStop);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 下载线程
     */
    private class DownloadThread extends Thread {
        private ThreadInfo mThreadInfo;

        private boolean isFinished = false;

        DownloadThread(ThreadInfo threadInfo) {
            this.mThreadInfo = threadInfo;
        }

        HttpURLConnection connection;
        RandomAccessFile randomAccessFile;
        InputStream inputStream;

        @Override
        public void run() {
            try {
                URL url = new URL(mThreadInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                //设置下载位置
                long start = mThreadInfo.getStart() + mThreadInfo.getFinished();
                connection.setRequestProperty("Range", "bytes = " + start + "-" + mThreadInfo.getEnd());
                //设置文件写入位置
                File file = new File(DownloadService.DOWNLOAD_PATH, fileInfo.getFileName());
                randomAccessFile = new RandomAccessFile(file, "rwd");
                randomAccessFile.seek(start);

                finished += mThreadInfo.getFinished();
                //开始下载
                if (connection.getResponseCode() == HttpURLConnection.HTTP_PARTIAL ||
                        connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    //读取数据
                    inputStream = connection.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len;
                    long time = System.currentTimeMillis();
                    while ((len = inputStream.read(buffer)) != -1) {
                        //在下载暂停时保存进度
                        if (isPause) {
                            threadPortImpl.updateThread(fileInfo.getUrl(), fileInfo.getId(),
                                    mThreadInfo.getFinished());
                            return;
                        }
                        //写入文件
                        randomAccessFile.write(buffer, 0, len);
                        //整个文件的完成进度
                        finished += len;
                        //每个线程完成的进度
                        mThreadInfo.setFinished(mThreadInfo.getFinished() + len);
                    }
                    //标识线程下载完毕
                    isFinished = true;
                    //检查线程是否全部下载完毕
                    checkAllThreadsIsFinished();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.disconnect();
                    randomAccessFile.close();
                    if (inputStream != null)
                        inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
