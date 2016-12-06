package com.shmj.mouzhai.downloaddemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.shmj.mouzhai.downloaddemo.entities.FileInfo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 下载服务与下载子线程
 * <p>
 * Created by Mouzhai on 2016/11/28.
 */

public class DownloadService extends Service {

    public static final String DOWNLOAD_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/downloads/";

    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String ACTION_FINISHED = "ACTION_FINISHED";
    public static final int MSG_INIT = 0x1;//初始化标识
    public static final int MSG_BIND = 0x2;//绑定标识
    public static final int MSG_START = 0x3;
    public static final int MSG_STOP = 0x4;
    public static final int MSG_UPDATE = 0x5;
    public static final int MSG_FINISHED = 0x6;

    //下载任务集合
    private Map<Integer, DownloadTask> tasks = new LinkedHashMap<>();

    private Messenger activityMessenger;//来自 Activity 的 Messenger

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            FileInfo fileInfo;
            DownloadTask task;
            switch (msg.what) {
                case MSG_INIT:
                    fileInfo = (FileInfo) msg.obj;
                    Log.e("test", "Init: " + fileInfo.toString());
                    //启动下载任务
                    task = new DownloadTask(DownloadService.this, activityMessenger, fileInfo, 3);
                    task.download();
                    //把下载任务添加到集合中
                    tasks.put(fileInfo.getId(), task);
                    //启动通知
                    Message msgStart = new Message();
                    msgStart.what = MSG_START;
                    msgStart.obj = fileInfo;
                    try {
                        activityMessenger.send(msgStart);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case MSG_BIND:
                    //处理绑定的 Messenger
                    activityMessenger = msg.replyTo;
                    break;
                case MSG_START:
                    fileInfo = (FileInfo) msg.obj;
                    //启动线程
                    InitThread initThread = new InitThread(fileInfo);
                    DownloadTask.executorService.execute(initThread);
                    break;
                case MSG_STOP:
                    //暂停下载
                    //从集合中取出下载任务
                    fileInfo = (FileInfo) msg.obj;
                    DownloadTask downloadTask = tasks.get(fileInfo.getId());
                    if (downloadTask != null) {
                        downloadTask.isPause = true;
                    }
                    break;
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //创建 Messenger 对象，包含 Handler 引用
        Messenger messenger = new Messenger(handler);
        //返回 Messenger 的 Binder
        return messenger.getBinder();
    }

    /**
     * 初始化子线程
     */
    class InitThread extends Thread {
        private FileInfo mFileInfo = null;

        public InitThread(FileInfo mFileInfo) {
            this.mFileInfo = mFileInfo;
        }

        @Override
        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile randomAccessFile = null;
            try {
                //连接网络文件
                URL url = new URL(mFileInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                int length = -1;
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    //获得文件长度
                    length = connection.getContentLength();
                }
                if (length <= 0) {
                    return;
                }
                //创建文件目录
                File dir = new File(DOWNLOAD_PATH);
                if (!dir.exists()) {
                    if (!dir.mkdir()) {
                        return;
                    }
                }
                //在本地创建文件
                File file = new File(dir, mFileInfo.getFileName());
                randomAccessFile = new RandomAccessFile(file, "rwd");
                //设置文件长度
                randomAccessFile.setLength(length);
                //传回长度信息
                mFileInfo.setLength(length);
                handler.obtainMessage(MSG_INIT, mFileInfo).sendToTarget();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
