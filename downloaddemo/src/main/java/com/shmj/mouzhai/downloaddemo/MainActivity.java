package com.shmj.mouzhai.downloaddemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.shmj.mouzhai.downloaddemo.adapter.FileListAdapter;
import com.shmj.mouzhai.downloaddemo.entities.FileInfo;
import com.shmj.mouzhai.downloaddemo.services.DownloadService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvFile;

    private List<FileInfo> fileInfos;
    private FileInfo fileInfo, fileInfo1, fileInfo2, fileInfo3;
    private FileListAdapter fileListAdapter;

    public static final String FILE_URL = "http://www.imooc.com/mobile/mukewang.apk";
    public static final String FILE_URL1 = "http://music.163.com/api/android/download/latest2";
    public static final String FILE_URL2 = "http://dlsw.baidu.com/sw-search-sp/soft/91/14506/wdj2.80.0.7144.1437707943.exe";
    public static final String FILE_URL3 = "http://sw.bos.baidu.com/sw-search-sp/software/dd98d1b0b1a7c/GoogleEarth_7.1.7.2606.exe";
    public static final String FILE_NAME = "imooc.apk";
    public static final String FILE_NAME1 = "CloudMusic.apk";
    public static final String FILE_NAME2 = "wandoujia.apk";
    public static final String FILE_NAME3 = "GoogleEarth.exe";

    //更新 UI 的广播接收器
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService.ACTION_UPDATE.equals(intent.getAction())) {
                //更新进度条
                int finished = intent.getIntExtra("finished", 0);
                int id = intent.getIntExtra("id", 0);
                fileListAdapter.updateProgress(id, finished);
            } else if (DownloadService.ACTION_FINISHED.equals(intent.getAction())) {
                //结束进度，更新进度为0
                FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
                fileListAdapter.updateProgress(fileInfo.getId(), 0);
                Toast.makeText(MainActivity.this, fileInfo.getFileName() + "下载完毕", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDatas();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
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

        //注册广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadService.ACTION_UPDATE);
        intentFilter.addAction(DownloadService.ACTION_FINISHED);
        registerReceiver(receiver, intentFilter);
    }
}
