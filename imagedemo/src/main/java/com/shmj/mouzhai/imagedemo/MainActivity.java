package com.shmj.mouzhai.imagedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickPrimaryColor(View view) {
        startActivity(new Intent(this, PrimaryColorActivity.class));
    }

    public void clickColorMatrix(View view) {
        startActivity(new Intent(this, ColorMatrixActivity.class));
    }

    public void clickPixelEffect(View view) {
        startActivity(new Intent(this, PixelsActivity.class));
    }

    public void clickImageMatrix(View view) {
        startActivity(new Intent(this, ImageMatrixActivity.class));
    }

    public void clickXFermode(View view) {
        startActivity(new Intent(this, XfermodeActivity.class));
    }

    public void clickReflect(View view) {
        startActivity(new Intent(this, ReflectActivity.class));
    }

    public void clickMesh(View view) {
        startActivity(new Intent(this, MeshActivity.class));
    }
}
