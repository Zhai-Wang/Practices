package com.shmj.mouzhai.imagedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.shmj.mouzhai.imagedemo.util.ImageHelper;

public class PrimaryColorActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private ImageView imageView;
    private SeekBar sbHue, sbSaturation, sbLum;
    private Bitmap bitmap;

    private static int MAX_VALUE = 255;
    private static int MID_VALUE = 127;
    private float mHue, mSaturation, mLum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary_color);
        initView();
        initData();
        initEvents();
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.image_view);
        sbHue = (SeekBar) findViewById(R.id.sb_hue);
        sbSaturation = (SeekBar) findViewById(R.id.sb_saturation);
        sbLum = (SeekBar) findViewById(R.id.sb_lum);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test3);
    }


    private void initData() {
        //设置各滑动条的最大值与初始值
        sbHue.setMax(MAX_VALUE);
        sbSaturation.setMax(MAX_VALUE);
        sbLum.setMax(MAX_VALUE);
        sbHue.setProgress(MID_VALUE);
        sbSaturation.setProgress(MID_VALUE);
        sbLum.setProgress(MID_VALUE);

        //设置初始图片
        imageView.setImageBitmap(bitmap);
    }

    private void initEvents() {
        //注册滑动事件
        sbHue.setOnSeekBarChangeListener(this);
        sbSaturation.setOnSeekBarChangeListener(this);
        sbLum.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        //根据滑动情况，改变相应的属性值
        switch (seekBar.getId()) {
            case R.id.sb_hue:
                mHue = (progress - MID_VALUE) * 1.0f / MID_VALUE * 180;
                break;
            case R.id.sb_saturation:
                mSaturation = progress * 1.0f / MID_VALUE;
                break;
            case R.id.sb_lum:
                mLum = progress * 1.0f / MID_VALUE;
                break;
        }
        //利用工具类，将改变后的 Bitmap 重新设置给 ImageView
        imageView.setImageBitmap(ImageHelper.handleImageEffect(bitmap, mHue, mSaturation, mLum));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
