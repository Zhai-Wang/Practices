package com.shmj.mouzhai.downloaddemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
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
    public static final int MSG_INIT = 0;

    //下载任务集合
    private Map<Integer, DownloadTask> tasks = new LinkedHashMap<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //获得 Activity 传来的参数
        if (ACTION_START.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            //启动线程
            InitThread initThread = new InitThread(fileInfo);
            DownloadTask.executorService.execute(initThread);
        } else if (ACTION_STOP.equals(intent.getAction())) {
            //暂停下载
            //从集合中取出下载任务
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            DownloadTask downloadTask = tasks.get(fileInfo.getId());
            if (downloadTask != null) {
                downloadTask.isPause = true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 初始化子线程
     */
    class InitThread extends Thread {
        private FileInfo mFileInfo = null;

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_INIT:
                        FileInfo fileInfo = (FileInfo) msg.obj;
                        Log.e("test", "Init: " + fileInfo.toString());
                        //启动下载任务
                        DownloadTask task = new DownloadTask(DownloadService.this, fileInfo, 3);
                        task.download();
                        //把下载任务添加到集合中
                        tasks.put(fileInfo.getId(), task);
                        break;
                }
            }
        };

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
                    if(!dir.mkdir()){
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
