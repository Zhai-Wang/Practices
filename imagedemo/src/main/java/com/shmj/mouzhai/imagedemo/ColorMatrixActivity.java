package com.shmj.mouzhai.imagedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

public class ColorMatrixActivity extends AppCompatActivity {

    private ImageView imageView;
    private GridLayout gridLayout;
    private Bitmap bitmap;

    private int mEtWidth, mEtHeight;
    private EditText[] mEts = new EditText[20];
    private float[] mColorMatrix = new float[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_matrix);
        initView();
        initData();
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.image_view_cm);
        gridLayout = (GridLayout) findViewById(R.id.grid_layout);
    }

    private void initData() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test1);
        imageView.setImageBitmap(bitmap);

        //在控件绘制完成之后，获取 GridLayout 的宽高,绘制图形
        gridLayout.post(new Runnable() {
            @Override
            public void run() {
                mEtWidth = gridLayout.getWidth() / 5;
                mEtHeight = gridLayout.getHeight() / 4;
                addEt();
                initMatrix();
            }
        });
    }

    /**
     * 向 GridLayout 中添加 EditText
     */
    private void addEt() {
        for (int i = 0; i < 20; i++) {
            EditText editText = new EditText(ColorMatrixActivity.this);
            mEts[i] = editText;
            gridLayout.addView(editText, mEtWidth, mEtHeight);
        }
    }

    /**
     * 初始化矩阵
     */
    private void initMatrix() {
        for (int i = 0; i < 20; i++) {
            if (i % 6 == 0) {
                mEts[i].setText(String.valueOf(1));
            } else {
                mEts[i].setText(String.valueOf(0));
            }
        }
    }

    /**
     * 获取用户在矩阵中输入的值
     */
    private void getMatrix(){
        for (int i = 0; i < 20; i++) {
            mColorMatrix[i] = Float.valueOf(mEts[i].getText().toString());
        }
    }

    /**
     * 根据矩阵中值得改变，更新图片
     */
    private void setImageMatrix(){
        Bitmap mBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(mColorMatrix);

        Canvas canvas = new Canvas(mBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        imageView.setImageBitmap(mBitmap);
    }

    public void clickChange(View view) {
        getMatrix();
        setImageMatrix();
    }

    public void clickReset(View view) {
        initMatrix();
        getMatrix();
        setImageMatrix();
    }
}
