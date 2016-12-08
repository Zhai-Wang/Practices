package com.shmj.mouzhai.imagedemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.shmj.mouzhai.imagedemo.R;

/**
 * 倒影效果
 * <p>
 * Created by Mouzhai on 2016/12/8.
 */

public class Reflect extends View {

    private Bitmap srcBitmap;
    private Bitmap refBitmap;
    private Paint paint;

    public Reflect(Context context) {
        super(context);
        initView();
    }

    public Reflect(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public Reflect(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test3);
        //图形变换矩阵
        Matrix matrix = new Matrix();
        matrix.setScale(1, -1);
        //创建镜像
        refBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(), srcBitmap.getHeight(),
                matrix, true);
        //设置线性渐变的画笔
        paint = new Paint();
        paint.setShader(new LinearGradient(0, srcBitmap.getHeight(), 0, srcBitmap.getHeight() * 1.8f,
                0xDD000000, 0x10000000, Shader.TileMode.CLAMP));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(srcBitmap, 0, 0, null);
        canvas.drawBitmap(refBitmap, 0, srcBitmap.getHeight(), null);
        canvas.drawRect(0, srcBitmap.getHeight(), srcBitmap.getWidth(), refBitmap.getHeight() * 2,
                paint);
    }
}
