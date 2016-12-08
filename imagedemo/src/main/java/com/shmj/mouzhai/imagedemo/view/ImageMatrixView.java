package com.shmj.mouzhai.imagedemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import com.shmj.mouzhai.imagedemo.R;

/**
 * 自定义包含两张图片的 View
 * <p>
 * Created by Mouzhai on 2016/12/8.
 */

public class ImageMatrixView extends View {

    private Bitmap mBitmap;
    private Matrix mMatrix;

    public ImageMatrixView(Context context) {
        super(context);
        initView();
    }

    public ImageMatrixView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ImageMatrixView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化 Bitmap 与 Matrix
     */
    private void initView() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        setImageMatrix(new Matrix());
    }

    /**
     * 获取 Matrix 对象
     */
    public void setImageMatrix(Matrix matrix) {
        mMatrix = matrix;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, null);//原图
        canvas.drawBitmap(mBitmap, mMatrix, null);//对比图
    }
}
