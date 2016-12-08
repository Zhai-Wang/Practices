package com.shmj.mouzhai.imagedemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.shmj.mouzhai.imagedemo.R;

import static android.graphics.PorterDuff.Mode.SRC_IN;

/**
 * 自动圆角转化
 * <p>
 * Created by Mouzhai on 2016/12/8.
 */

public class RoundRectXfermodeView extends View {

    private Bitmap mBitmap;
    private Bitmap mOut;
    private Paint mPaint;

    public RoundRectXfermodeView(Context context) {
        super(context);
        initView();
    }

    public RoundRectXfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RoundRectXfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        //为了使用 Xfermode，禁用硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test1);
        mOut = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mOut);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //绘制圆角矩形
        canvas.drawRoundRect(new RectF(0, 0, mBitmap.getWidth(), mBitmap.getHeight()), 50, 50, mPaint);
        //设置 Xfermode 属性为交集
        mPaint.setXfermode(new PorterDuffXfermode(SRC_IN));
        //绘制图片
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        //改回 Paint 的属性
        mPaint.setXfermode(null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mOut, 0, 0, null);
    }
}
