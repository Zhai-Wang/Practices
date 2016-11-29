package com.shmj.mouzhai.downloaddemo.services;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.shmj.mouzhai.downloaddemo.db.ThreadPort;
import com.shmj.mouzhai.downloaddemo.db.ThreadPortImpl;
import com.shmj.mouzhai.downloaddemo.entities.FileInfo;
import com.shmj.mouzhai.downloaddemo.entities.ThreadInfo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 下载任务类
 * <p>
 * Created by Mouzhai on 2016/11/28.
 */

public class DownloadTask {
    private Context context;
    private FileInfo fileInfo;
    private ThreadPort threadPort;

    private int finished = 0;
    public boolean isPause = false;

    public DownloadTask(Context context, FileInfo fileInfo) {
        this.context = context;
        this.fileInfo = fileInfo;
        threadPort = new ThreadPortImpl(context);
    }

    public void download() {
        //读取数据库的连接信息
        List<ThreadInfo> threadInfos = threadPort.getThreads(fileInfo.getUrl());
        ThreadInfo threadInfo = null;
        if (threadInfos.size() == 0) {
            //初始化线程信息
            threadInfo = new ThreadInfo(fileInfo.getId(), fileInfo.getUrl(), 0, fileInfo.getLength(), 0);
        } else {
            //取得已有的线程信息
            threadInfo = threadInfos.get(0);
        }
        //创建新线程进行下载
        new DownloadThread(threadInfo).start();
    }

    /**
     * 下载线程
     */
    class DownloadThread extends Thread {
        private ThreadInfo threadInfo;

        HttpURLConnection connection;
        RandomAccessFile randomAccessFile;
        InputStream inputStream;

        public DownloadThread(ThreadInfo threadInfo) {
            this.threadInfo = threadInfo;
        }

        @Override
        public void run() {
            //向数据库插入线程信息
            if (!threadPort.isExists(threadInfo.getUrl(), threadInfo.getId())) {
                threadPort.insertThread(threadInfo);
            }
            try {
                URL url = new URL(threadInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                //设置下载位置
                int start = threadInfo.getStart() + threadInfo.getFinished();
                connection.setRequestProperty("Range", "bytes = " + start + "-" + threadInfo.getEnd());
                //设置文件写入位置
                File file = new File(DownloadService.DOWNLOAD_PATH, fileInfo.getFileName());
                randomAccessFile = new RandomAccessFile(file, "rwd");
                randomAccessFile.seek(start);

                Intent intent = new Intent(DownloadService.ACTION_UPDATE);
                finished += fileInfo.getFinished();
                //开始下载
                if (connection.getResponseCode() == HttpURLConnection.HTTP_PARTIAL ||
                        connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    //读取数据
                    inputStream = connection.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len;
                    long time = System.currentTimeMillis();
                    while ((len = inputStream.read(buffer)) != -1) {
                        //写入文件
                        randomAccessFile.write(buffer, 0, len);
                        //把下载进度发送广播给 Activity
                        finished += len;
                        if (System.currentTimeMillis() - time > 500) {
                            time = System.currentTimeMillis();
                            intent.putExtra("finished", (int) (finished * 100.0 / fileInfo.getLength()));
                            context.sendBroadcast(intent);
                        }
                        //在下载暂停时保存进度
                        if (isPause) {
                            threadPort.updateThread(fileInfo.getUrl(), fileInfo.getId(), finished);
                            return;
                        }
                    }
                    //删除线程信息
                    threadPort.deleteThread(fileInfo.getUrl(), fileInfo.getId());
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
