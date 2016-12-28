package com.mouzhai.weixin60;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * 自定义 view 类
 * <p>
 * Created by Mouzhai on 2016/12/26.
 */

public class ChangeIconColorWithText extends View {
    private static final String INSTANCE_STATUS = "instance_status";
    private static final String STATUS_ALPHA = "status_alpha";
    private int mColor = 0xff45c01a;
    private Bitmap mIconBitmap;
    private String mText = "微信";
    private int mTextSize = (int) TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mPaint;
    private Paint mTextPaint;
    private Rect mIconRect;
    private Rect mTextBound;
    private float mAlpha;

    public ChangeIconColorWithText(Context context) {
        this(context, null);
    }

    public ChangeIconColorWithText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 获取自定义属性的值
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public ChangeIconColorWithText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取自定义属性值
        TypedArray array = context.obtainStyledAttributes(
                attrs, R.styleable.ChangeIconColorWithText);

        //遍历 TypedArray，得到其中的各项具体值
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.ChangeIconColorWithText_icon:
                    BitmapDrawable drawable = (BitmapDrawable) array.getDrawable(attr);
                    mIconBitmap = drawable.getBitmap();
                    break;
                case R.styleable.ChangeIconColorWithText_color:
                    mColor = array.getColor(attr, 0xff45c01a);
                    break;
                case R.styleable.ChangeIconColorWithText_text:
                    mText = (String) array.getText(attr);
                    break;
                case R.styleable.ChangeIconColorWithText_text_size:
                    mTextSize = (int) array.getDimension(attr, TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    break;
            }
        }
        array.recycle();

        mTextBound = new Rect();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0xff555555);

        mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
        bundle.putFloat(STATUS_ALPHA, mAlpha);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mAlpha = bundle.getFloat(STATUS_ALPHA);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //设置 view 范围
        //tab 栏里 icon 的宽高，由于是正方形，宽高相等
        int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - mTextBound.height());
        //icon 是居中显示的，计算得到左方边距
        int left = getMeasuredWidth() / 2 - iconWidth / 2;
        //top 值需要考虑到 icon 下方还有 text
        int top = getMeasuredHeight() / 2 - (mTextBound.height() + iconWidth) / 2;
        mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mIconBitmap, null, mIconRect, null);

        //alpha 值向上取整
        int alpha = (int) Math.ceil(255 * mAlpha);

        //绘制图标
        setupBitmap(alpha);
        //绘制文字
        drawSourceText(canvas, alpha);
        //绘制变色文本
        drawTargetText(canvas, alpha);

        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

    /**
     * 在内存里绘制可变色的 icon
     */
    private void setupBitmap(int alpha) {
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);

        mCanvas.drawRect(mIconRect, mPaint);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(255);

        mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
    }

    /**
     * 绘制原始文本
     */
    private void drawSourceText(Canvas canvas, int alpha) {
        //设置默认颜色
        mTextPaint.setColor(0xff333333);
        //设置透明度,使得变色前后的透明度存在渐变效果
        mTextPaint.setAlpha(255 - alpha);
        //得到文本大小，进行绘制
        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = mIconRect.height() + mTextBound.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }

    /**
     * 绘制变色文本
     */
    private void drawTargetText(Canvas canvas, int alpha) {
        //设置颜色
        mTextPaint.setColor(mColor);
        //设置透明度
        mTextPaint.setAlpha(alpha);
        //得到文本大小，进行绘制
        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = mIconRect.height() + mTextBound.height();
        canvas.drawText(mText, x, y, mTextPaint);
    }

    /**
     * 提供给外界设置透明度的方法
     */
    public void setIconAlpha(float alpha) {
        this.mAlpha = alpha;

        invalidateView();
    }

    /**
     * 重绘 view
     */
    private void invalidateView() {
        //判断是否是在主线程中，并且刷新 view
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }
}
