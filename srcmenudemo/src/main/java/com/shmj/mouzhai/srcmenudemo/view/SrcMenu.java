package com.shmj.mouzhai.srcmenudemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.shmj.mouzhai.srcmenudemo.R;

/**
 * 自定义卫星按钮 View
 * <p>
 * Created by Mouzhai on 2016/12/9.
 */

public class SrcMenu extends ViewGroup implements View.OnClickListener {

    private static final int POS_LEFT_TOP = 0;
    private static final int POS_LEFT_BOTTOM = 1;
    private static final int POS_RIGHT_TOP = 2;
    private static final int POS_RIGHT_BOTTOM = 3;
    private Position mPosition = Position.RIGHT_BOTTOM;//菜单位置
    private int mRadius;//菜单半径
    private Status mStatus = Status.CLOSE;//菜单开闭状态
    private View mCButton;//菜单主按钮

    private OnMenuItemClickListener mOnMenuItemClickListener;//菜单点击事件的成员变量


    public SrcMenu(Context context) {
        this(context, null);
    }


    public SrcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SrcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,
                getResources().getDisplayMetrics());//设置菜单半径默认值

        //获取自定义属性值
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SrcMenu,
                defStyleAttr, 0);
        //获取位置值
        int pos = array.getInt(R.styleable.SrcMenu_position, POS_RIGHT_BOTTOM);
        switch (pos) {
            case POS_LEFT_TOP:
                mPosition = Position.LEFT_TOP;
                break;
            case POS_LEFT_BOTTOM:
                mPosition = Position.LEFT_BOTTOM;
                break;
            case POS_RIGHT_TOP:
                mPosition = Position.RIGHT_TOP;
                break;
            case POS_RIGHT_BOTTOM:
                mPosition = Position.RIGHT_BOTTOM;
                break;
        }
        //获取菜单半径值
        mRadius = (int) array.getDimension(R.styleable.SrcMenu_radius,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,
                        getResources().getDisplayMetrics()));
        array.recycle();
    }

    /**
     * 设置菜单点击事件
     */
    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        mOnMenuItemClickListener = onMenuItemClickListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            //测量 child
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        if (b) {
            layoutCButton();
            int count = getChildCount();

            for (int j = 0; j < count - 1; j++) {
                View child = getChildAt(j + 1);

                //开始时设置子菜单不可见
                child.setVisibility(GONE);

                //默认按钮位于左上时
                int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * j));
                int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * j));
                int cWidth = child.getMeasuredWidth();
                int cHeight = child.getMeasuredHeight();
                //按钮位于左下、右下时
                if (mPosition == Position.LEFT_BOTTOM || mPosition == Position.RIGHT_BOTTOM) {
                    ct = getMeasuredHeight() - cHeight - ct;
                }
                //按钮位于右上、右下时
                if (mPosition == Position.RIGHT_TOP || mPosition == Position.RIGHT_BOTTOM) {
                    cl = getMeasuredWidth() - cWidth - cl;
                }

                child.layout(cl, ct, cl + cWidth, ct + cHeight);
            }
        }
    }

    /**
     * 定位主菜单按钮
     */
    private void layoutCButton() {
        mCButton = getChildAt(0);
        mCButton.setOnClickListener(this);

        int l = 0;
        int t = 0;
        int width = mCButton.getMeasuredWidth();
        int height = mCButton.getMeasuredHeight();

        //设置按钮显示的位置
        switch (mPosition) {
            case LEFT_BOTTOM:
                l = 0;
                t = getMeasuredHeight() - height;
                break;
            case LEFT_TOP:
                l = 0;
                t = 0;
                break;
            case RIGHT_TOP:
                l = getMeasuredWidth() - width;
                t = 0;
                break;
            case RIGHT_BOTTOM:
                l = getMeasuredWidth() - width;
                t = getMeasuredHeight() - height;
                break;
        }
        mCButton.layout(l, t, l + width, t + height);
    }

    @Override
    public void onClick(View view) {
        rotateCButton(view, 0f, 360f, 300);
        toggleMenu();
    }

    /**
     * 切换菜单
     */
    private void toggleMenu() {
        int count = getChildCount();
        for (int i = 0; i < count - 1; i++) {
            final View childView = getChildAt(i + 1);
            childView.setVisibility(VISIBLE);

            int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * i));
            int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * i));

            //根据菜单所处位置的不同，设置不同的参数值
            int xFlag = 1;
            int yFlag = 1;
            if (mPosition == Position.LEFT_TOP || mPosition == Position.LEFT_BOTTOM) {
                xFlag = -1;
            }
            if (mPosition == Position.LEFT_TOP || mPosition == Position.RIGHT_TOP) {
                yFlag = -1;
            }

            //平移动画
            AnimationSet animationSet = new AnimationSet(true);
            Animation animation;

            if (mStatus == Status.CLOSE) {
                //打开按钮的动画
                animation = new TranslateAnimation(xFlag * cl, 0, yFlag * ct, 0);
                childView.setFocusable(true);
                childView.setClickable(true);
            } else {
                //关闭按钮的动画
                animation = new TranslateAnimation(0, xFlag * cl, 0, yFlag * ct);
                childView.setFocusable(false);
                childView.setClickable(false);
            }
            animation.setDuration(300);
            animation.setFillAfter(true);
            animation.setStartOffset((i * 100) / count);
            //监听动画状态
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mStatus == Status.CLOSE) {
                        childView.setVisibility(GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            //旋转动画
            RotateAnimation rotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF,
                    0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setFillAfter(true);
            rotateAnimation.setDuration(300);

            //添加动画
            animationSet.addAnimation(rotateAnimation);
            animationSet.addAnimation(animation);
            childView.startAnimation(animationSet);

            //为子菜单项添加点击事件
            final int pos = i + 1;
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnMenuItemClickListener != null) {
                        mOnMenuItemClickListener.onClick(childView, pos);
                        menuItemAnim(pos - 1);
                        changeStatus();
                    }
                }
            });
        }
        changeStatus();
    }

    /**
     * 点击子菜单项的动画
     */
    private void menuItemAnim(int pos) {
        for (int i = 0; i < getChildCount() - 1; i++) {
            View childView = getChildAt(i + 1);
            if (i == pos) {
                childView.startAnimation(scaleBigAnim(300));
            } else {
                childView.startAnimation(scaleSmallAnim(300));
            }

            //设置子菜单隐藏
            childView.setClickable(false);
            childView.setFocusable(false);
        }
    }

    /**
     * 使菜单项变小并消失的动画
     */
    private Animation scaleSmallAnim(int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    /**
     * 使菜单项变大，透明度降低的动画
     */
    private Animation scaleBigAnim(int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 4.0f, 1.0f, 4.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    /**
     * 改变菜单状态值
     */
    private void changeStatus() {
        mStatus = (mStatus == Status.CLOSE ? Status.OPEN : Status.CLOSE);
    }

    /**
     * 按钮旋转动画
     */
    private void rotateCButton(View view, float start, float end, int duration) {
        RotateAnimation animation = new RotateAnimation(start, end, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }

    /**
     * 菜单状态枚举类
     */
    public enum Status {
        OPEN, CLOSE
    }

    /**
     * 菜单位置枚举类
     */
    public enum Position {
        LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
    }

    /**
     * 菜单按钮点击事件回调接口
     */
    public interface OnMenuItemClickListener {
        void onClick(View view, int position);
    }
}
