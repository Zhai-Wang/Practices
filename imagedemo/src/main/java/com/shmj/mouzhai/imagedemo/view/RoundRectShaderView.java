package com.shmj.mouzhai.imagedemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.shmj.mouzhai.imagedemo.R;

/**
 * 利用 Shader 实现圆角效果
 * <p>
 * Created by Mouzhai on 2016/12/8.
 */

public class RoundRectShaderView extends View {

    private Bitmap mBitmap;
    private Paint mPaint;
    private BitmapShader mBitmapShader;

    public RoundRectShaderView(Context context) {
        super(context);
    }

    public RoundRectShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundRectShaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test3);
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(mBitmapShader);
        canvas.drawCircle(300, 200, 150, mPaint);
    }
}
