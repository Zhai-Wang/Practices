package com.shmj.mouzhai.downloaddemo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.shmj.mouzhai.downloaddemo.adapter.FileListAdapter;
import com.shmj.mouzhai.downloaddemo.entities.FileInfo;
import com.shmj.mouzhai.downloaddemo.services.DownloadService;
import com.shmj.mouzhai.downloaddemo.util.NotificationUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvFile;

    private List<FileInfo> fileInfos;
    private FileInfo fileInfo, fileInfo1, fileInfo2, fileInfo3;
    private FileListAdapter fileListAdapter;
    private NotificationUtil notificationUtil;
    private Messenger serviceMessenger;//Service 中的 Messenger

    public static final String FILE_URL = "http://www.imooc.com/mobile/mukewang.apk";
    public static final String FILE_URL1 = "http://music.163.com/api/android/download/latest2";
    public static final String FILE_URL2 = "http://dlsw.baidu.com/sw-search-sp/soft/91/14506/wdj2.80.0.7144.1437707943.exe";
    public static final String FILE_URL3 = "http://sw.bos.baidu.com/sw-search-sp/software/dd98d1b0b1a7c/GoogleEarth_7.1.7.2606.exe";
    public static final String FILE_NAME = "imooc.apk";
    public static final String FILE_NAME1 = "CloudMusic.apk";
    public static final String FILE_NAME2 = "wandoujia.apk";
    public static final String FILE_NAME3 = "GoogleEarth.exe";

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case DownloadService.MSG_UPDATE:
                    //更新进度条
                    int finished = msg.arg1;
                    int id = msg.arg2;
                    fileListAdapter.updateProgress(id, finished);
                    //更新通知里的进度条
                    notificationUtil.updateNotification(id, finished);
                    break;
                case DownloadService.MSG_STOP:
                    //结束进度，更新进度为0
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    fileListAdapter.updateProgress(fileInfo.getId(), 0);
                    Toast.makeText(MainActivity.this, fileInfo.getFileName() + "下载完毕", Toast.LENGTH_SHORT).show();
                    //取消通知
                    notificationUtil.cancleNotification(fileInfo.getId());
                    break;
                case DownloadService.MSG_START:
                    //结束进度，更新进度为0
                    notificationUtil.showNotification((FileInfo) msg.obj);
                    break;
            }
        }
    };

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //获得 Service 中的 Messenger
            serviceMessenger = new Messenger(iBinder);
            //设置适配器中的 Messenger
            fileListAdapter.setMessenger(serviceMessenger);
            //创建 Activity 中的 Messenger
            Messenger activityMessenger = new Messenger(handler);
            //创建消息
            Message message = new Message();
            message.what = DownloadService.MSG_BIND;
            message.replyTo = activityMessenger;
            //用 Service 中的 Messenger 发送 Activity 中的 Messenger
            try {
                serviceMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDatas();
    }

    private void initView() {
        lvFile = (ListView) findViewById(R.id.lv_file);
    }

    private void initDatas() {
        //创建文件集合
        fileInfos = new ArrayList<>();
        //创建文件对象
        fileInfo = new FileInfo(0, FILE_URL, FILE_NAME, 0, 0);
        fileInfo1 = new FileInfo(1, FILE_URL1, FILE_NAME1, 0, 0);
        fileInfo2 = new FileInfo(2, FILE_URL2, FILE_NAME2, 0, 0);
        fileInfo3 = new FileInfo(3, FILE_URL3, FILE_NAME3, 0, 0);
        fileInfos.add(fileInfo);
        fileInfos.add(fileInfo1);
        fileInfos.add(fileInfo2);
        fileInfos.add(fileInfo3);
        //创建适配器
        fileListAdapter = new FileListAdapter(this, fileInfos);
        //设置适配器
        lvFile.setAdapter(fileListAdapter);

        notificationUtil = new NotificationUtil(this);

        //绑定 Service
        Intent intent = new Intent(this, DownloadService.class);
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
    }
}
