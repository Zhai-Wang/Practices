package com.shmj.mouzhai.downloaddemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shmj.mouzhai.downloaddemo.entities.FileInfo;
import com.shmj.mouzhai.downloaddemo.services.DownloadService;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pbProgress;
    private Button btnStart;
    private Button btnStop;

    private FileInfo fileInfo;

    public static final String FILE_URL = "http://www.imooc.com/mobile/mukewang.apk";
    public static final String FILE_NAME = "mukewang.apk";

    //更新 UI 的广播接收器
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService.ACTION_UPDATE.equals(intent.getAction())) {
                int finished = intent.getIntExtra("finished", 0);
                pbProgress.setProgress(finished);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDatas();
        initEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void initView() {
        pbProgress = (ProgressBar) findViewById(R.id.pb_progress);
        btnStart = (Button) findViewById(R.id.btn_start);
        btnStop = (Button) findViewById(R.id.btn_stop);
    }

    private void initDatas() {
        fileInfo = new FileInfo(0, FILE_URL, FILE_NAME, 0, 0);

        pbProgress.setMax(100);

        //注册广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadService.ACTION_UPDATE);
        registerReceiver(receiver, intentFilter);
    }

    private void initEvents() {
        //通过 intent 给 service 传递数据
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DownloadService.class);
                intent.setAction(DownloadService.ACTION_START);
                intent.putExtra("fileInfo", fileInfo);
                startService(intent);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DownloadService.class);
                intent.setAction(DownloadService.ACTION_STOP);
                intent.putExtra("fileInfo", fileInfo);
                startService(intent);
            }
        });
    }
}
