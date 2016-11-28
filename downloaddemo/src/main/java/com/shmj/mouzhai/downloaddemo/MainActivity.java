package com.shmj.mouzhai.downloaddemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shmj.mouzhai.downloaddemo.entities.FileInfo;
import com.shmj.mouzhai.downloaddemo.services.DownloadService;

public class MainActivity extends AppCompatActivity {

    private TextView tvFileName;
    private ProgressBar pbProgress;
    private Button btnStart;
    private Button btnStop;

    private  FileInfo fileInfo;

    public static final String FILE_URL = "http://music.163.com/api/pc/download/latest";
    public static final String FILE_NAME = "cloudmusicsetup_2_1_1[161566].exe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDatas();
        initEvents();
    }

    private void initView() {
        tvFileName = (TextView) findViewById(R.id.tv_file_name);
        pbProgress = (ProgressBar) findViewById(R.id.pb_progress);
        btnStart = (Button) findViewById(R.id.btn_start);
        btnStop = (Button) findViewById(R.id.btn_stop);
    }

    private void initDatas() {
       fileInfo = new FileInfo(0, FILE_URL, FILE_NAME, 0, 0);
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
