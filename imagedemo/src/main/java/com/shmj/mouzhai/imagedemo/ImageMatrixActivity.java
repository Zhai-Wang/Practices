package com.shmj.mouzhai.imagedemo;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;

import com.shmj.mouzhai.imagedemo.view.ImageMatrixView;

public class ImageMatrixActivity extends AppCompatActivity {

    private GridLayout mGridLayout;
    private ImageMatrixView mImageMatrixView;

    private int mEtWidth, mEtHeight;
    private float[] mImageMatrix = new float[9];
    private EditText[] mEts = new EditText[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_matrix);
        initView();
        initData();
    }

    private void initView() {
        mGridLayout = (GridLayout) findViewById(R.id.grid_layout_matrix);
        mImageMatrixView = (ImageMatrixView) findViewById(R.id.view_matrix);
    }

    private void initData() {
        //填充 GridLayout
        mGridLayout.post(new Runnable() {
            @Override
            public void run() {
                mEtWidth = mGridLayout.getWidth() / 3;
                mEtHeight = mGridLayout.getHeight() / 3;
                addEts();
                initMatrix();
            }
        });
    }

    /**
     * 向 GridLayout 中循环添加 EditText
     */
    private void addEts() {
        for (int i = 0; i < 9; i++) {
            EditText editText = new EditText(this);
            editText.setGravity(Gravity.CENTER);
            mEts[i] = editText;
            mGridLayout.addView(editText, mEtWidth, mEtHeight);
        }
    }

    /**
     * 初始化矩阵
     */
    private void initMatrix() {
        for (int i = 0; i < 9; i++) {
            if (i % 4 == 0) {
                mEts[i].setText(String.valueOf(1));
            } else {
                mEts[i].setText(String.valueOf(0));
            }
        }
    }

    /**
     * 获取矩阵中的数值
     */
    private void getImageMatrix() {
        for (int i = 0; i < 9; i++) {
            mImageMatrix[i] = Float.parseFloat(mEts[i].getText().toString());
        }
    }

    public void clickChange(View view) {
        //更新矩阵数据
        getImageMatrix();
        Matrix matrix = new Matrix();
        matrix.setValues(mImageMatrix);
        mImageMatrixView.setImageMatrix(matrix);
        mImageMatrixView.invalidate();
    }

    public void clickReset(View view) {
        //更新矩阵数据
        initMatrix();
        getImageMatrix();
        Matrix matrix = new Matrix();
        matrix.setValues(mImageMatrix);
        mImageMatrixView.setImageMatrix(matrix);
        mImageMatrixView.invalidate();
    }
}
