package com.shmj.mouzhai.downloaddemo.services;

import android.content.Context;

import com.shmj.mouzhai.downloaddemo.db.ThreadPort;
import com.shmj.mouzhai.downloaddemo.db.ThreadPortImpl;
import com.shmj.mouzhai.downloaddemo.entities.FileInfo;
import com.shmj.mouzhai.downloaddemo.entities.ThreadInfo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 下载任务类
 * <p>
 * Created by Mouzhai on 2016/11/28.
 */

public class DownloadTask {
    private Context context;
    private FileInfo fileInfo;
    private ThreadPort threadPort;

    public DownloadTask(Context context, FileInfo fileInfo) {
        this.context = context;
        this.fileInfo = fileInfo;
        threadPort = new ThreadPortImpl(context);
    }

    /**
     * 下载线程
     */
    class DownloadThread extends Thread{
        private ThreadInfo threadInfo;

        public DownloadThread(ThreadInfo threadInfo) {
            this.threadInfo = threadInfo;
        }

        public void run(){
            //向数据库插入线程信息
            if (!threadPort.isExists(threadInfo.getUrl(), threadInfo.getId())){
                threadPort.insertThread(threadInfo);
            }

            HttpURLConnection connection;
            try {
                URL url = new URL(threadInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(8000);
                connection.setRequestMethod("GET");
                //设置下载位置
                int start = threadInfo.getStart() + threadInfo.getFinished();
                connection.setRequestProperty("Range", "bytes = " + start + "-" + threadInfo.getEnd());
                //设置文件写入位置
                File file = new File(DownloadService.DOWNLOAD_PATH, fileInfo.getFileName());
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
                randomAccessFile.seek(start);
                //开始下载
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
