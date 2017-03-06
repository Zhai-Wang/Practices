package com.mouzhai.wechattalk;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mouzhai.wechattalk.adapter.RecoderAdapter;
import com.mouzhai.wechattalk.model.Recorder;
import com.mouzhai.wechattalk.view.AudioFinishedListener;
import com.mouzhai.wechattalk.view.AudioRecordButton;
import com.mouzhai.wechattalk.view.MediaManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_RECORD_AUDIO = 1;
    private static final int PERMISSION_WRITE_EXTERNAL_STORAGE = 2;

    private ListView mLvRecord;
    private ArrayAdapter<Recorder> mAdapter;
    private List<Recorder> mDatas = new ArrayList<>();
    private AudioRecordButton mButton;
    private ImageView mAnimView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        //申请录音与存储权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            //运行时申请权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_RECORD_AUDIO);
        } else {
            //权限已申请
            Toast.makeText(this, "权限已申请", Toast.LENGTH_SHORT).show();
            initEvents();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_RECORD_AUDIO) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "权限已申请", Toast.LENGTH_SHORT).show();
                initEvents();
            } else {
                Toast.makeText(this, "没有权限！", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initEvents() {
        mLvRecord = (ListView) findViewById(R.id.lv_chat);
        mButton = (AudioRecordButton) findViewById(R.id.btn_record);

        mButton.setAudioFinishedListener(new AudioFinishedListener() {
            @Override
            public void onFinish(float seconds, String filePath) {
                //add record files to listView
                Recorder recorder = new Recorder(seconds, filePath);
                mDatas.add(recorder);
                mAdapter.notifyDataSetChanged();
                mLvRecord.setSelection(mDatas.size() - 1);
            }
        });

        mAdapter = new RecoderAdapter(this, mDatas);
        mLvRecord.setAdapter(mAdapter);

        mLvRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //play animation
                //stop original animation when click new item
                if (mAnimView != null){
                    mAnimView.setImageResource(R.drawable.adj);
                    mAnimView = null;
                }
                mAnimView = (ImageView) view.findViewById(R.id.record_anim);
                mAnimView.setImageResource(R.drawable.play_anim);
                AnimationDrawable anim = (AnimationDrawable) mAnimView.getDrawable();
                anim.start();
                //play audio
                MediaManager.playSound(mDatas.get(i).getFilePath(), new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        //reset animView when sound playback completed
                        mAnimView.setImageResource(R.drawable.adj);
                    }
                });
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaManager.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaManager.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaManager.release();
    }
}
