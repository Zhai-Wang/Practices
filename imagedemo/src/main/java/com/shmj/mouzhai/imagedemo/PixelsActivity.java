package com.shmj.mouzhai.imagedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.shmj.mouzhai.imagedemo.util.ImageHelper;

public class PixelsActivity extends AppCompatActivity {

    private ImageView imageView1, imageView2, imageView3, imageView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pixels);
        initView();
        initData();
    }

    private void initView() {
        imageView1 = (ImageView) findViewById(R.id.image_view_1);
        imageView2 = (ImageView) findViewById(R.id.image_view_2);
        imageView3 = (ImageView) findViewById(R.id.image_view_3);
        imageView4 = (ImageView) findViewById(R.id.image_view_4);
    }


    private void initData() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test2);

        imageView1.setImageBitmap(bitmap);
        imageView2.setImageBitmap(ImageHelper.handleImageNegative(bitmap));
        imageView3.setImageBitmap(ImageHelper.handleImageOldPhoto(bitmap));
        imageView4.setImageBitmap(ImageHelper.handleImagePixelRelif(bitmap));
    }
}
