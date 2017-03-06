package com.mouzhai.wechattalk;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_RECORD_AUDIO = 1;
    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        //申请录音与存储权限
        //偷懒放在了这里——放在按钮的点击事件里是不是更合理？
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            //运行时申请权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_RECORD_AUDIO);
        } else {
            //权限已申请
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_RECORD_AUDIO){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }else {
                Toast.makeText(this, "没有录音权限！", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        if (requestCode == PERMISSION_WRITE_EXTERNAL_STORAGE){
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED){

            }else {
                Toast.makeText(this, "没有存储权限！", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
