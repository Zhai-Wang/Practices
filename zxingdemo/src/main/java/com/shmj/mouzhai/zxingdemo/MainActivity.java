package com.shmj.mouzhai.zxingdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private EditText etWords;
    private ImageView ivCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = (TextView) findViewById(R.id.tv_result);
        etWords = (EditText) findViewById(R.id.et_words);
        ivCode = (ImageView) findViewById(R.id.iv_code);
    }

    /**
     * 点击扫码按钮，跳转至扫码界面,开始扫码
     * 在此简单定制扫码界面
     */
    public void startScan(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);

        integrator.setOrientationLocked(false)//设置扫码的方向
                .setPrompt("将条码放置于框内")//设置下方提示文字
                .setCameraId(0)//前置或后置摄像头
                .setBeepEnabled(false)//扫码提示音，默认开启
                .setOrientationLocked(false)//解锁屏幕方向锁定
                .setCaptureActivity(ScanActivity.class)//设置扫码界面为自定义样式
                .initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //扫描成功
            if (result.getContents() == null) {
                //结束扫码
                Toast.makeText(this, "取消", Toast.LENGTH_SHORT).show();
            } else {
                //扫码出结果
                tvResult.setText(result.getContents());
                Toast.makeText(this, "扫码成功", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 点击生成按钮，根据字符串生成对应的二维码
     */
    public void createCode(View view) {
        Bitmap bitmap;
        BitMatrix matrix;
        MultiFormatWriter writer = new MultiFormatWriter();
        String words = etWords.getText().toString();//输入的内容
        try {
            matrix = writer.encode(words, BarcodeFormat.QR_CODE, 500, 500);
            BarcodeEncoder encoder = new BarcodeEncoder();
            bitmap = encoder.createBitmap(matrix);
            ivCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
