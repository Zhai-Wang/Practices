package com.shmj.mouzhai.scrollerviewmenu.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.shmj.mouzhai.scrollerviewmenu.R;

/**
 * 自定义侧滑菜单
 * <p>
 * Created by Mouzhai on 2016/11/22.
 */

public class SlidingMenu extends HorizontalScrollView {

    private LinearLayout mWrapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private int mScreenWidth;
    private int mMenuWidth;

    private int mMenuRightPadding;

    private boolean once = false;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    /**
     * 未设置自定义属性时，默认调用
     */
    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 使用了自定义属性时，调用此构造方法
     */
    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取自定义属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SlidingMenu, defStyleAttr, 0);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.SlidingMenu_rightPadding:
                    //默认的距离
                    //将 dp 转化为像素值
                    int defaultPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                            50, context.getResources().getDisplayMetrics());
                    mMenuRightPadding = typedArray.getDimensionPixelSize(attr, defaultPadding);
                    break;
            }
        }
        typedArray.recycle();

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
    }

    /**
     * 设置自身及子 View 的宽和高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once) {
            mWrapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWrapper.getChildAt(0);
            mContent = (ViewGroup) mWrapper.getChildAt(1);
            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
            mContent.getLayoutParams().width = mScreenWidth;
            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 决定子 view 的位置
     * 设置偏移量来隐藏 menu
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(mMenuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();//隐藏在屏幕左侧之外的宽度
                //如果隐藏宽度大于一半则隐藏 menu，否则显示
                if (scrollX >= mMenuWidth / 2) {
                    this.smoothScrollTo(mMenuWidth, 0);
                } else {
                    this.smoothScrollTo(0, 0);
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }
}
