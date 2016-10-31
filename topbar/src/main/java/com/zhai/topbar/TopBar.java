package com.zhai.topbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by 某宅 on 2016/10/31.
 */
public class TopBar extends RelativeLayout {

    private Button leftButton, rightButton;
    private TextView tvTitle;

    private String title;
    private float titleTextSize;
    private int titleTextColor;
    private String leftText;
    private int leftTextColor;
    private Drawable leftBackground;
    private String rightText;
    private int rightTextColor;
    private Drawable rightBackground;

    private LayoutParams leftParams, rightParams, titleParams;

    private topBarClickListener listener;

    public interface topBarClickListener{
        public void leftClick();
        public void rightClick();
    }

    public void setOnTopBarClickListener(topBarClickListener listener){
        this.listener = listener;
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Topbar);

        title = ta.getString(R.styleable.Topbar_title);
        titleTextColor = ta.getColor(R.styleable.Topbar_titleTextColor, 0);
        titleTextSize = ta.getDimension(R.styleable.Topbar_titleTextSize, 0);

        leftTextColor = ta.getColor(R.styleable.Topbar_leftTextColor, 0);
        leftText = ta.getString(R.styleable.Topbar_leftText);
        leftBackground = ta.getDrawable(R.styleable.Topbar_leftBackground);

        rightTextColor = ta.getColor(R.styleable.Topbar_rightTextColor, 0);
        rightText = ta.getString(R.styleable.Topbar_rightText);
        rightBackground = ta.getDrawable(R.styleable.Topbar_rightBackground);

        ta.recycle();

        leftButton = new Button(context);
        rightButton = new Button(context);
        tvTitle = new TextView(context);

        leftButton.setTextColor(leftTextColor);
        leftButton.setText(leftText);
        leftButton.setBackground(leftBackground);

        rightButton.setTextColor(rightTextColor);
        rightButton.setText(rightText);
        rightButton.setBackground(rightBackground);

        tvTitle.setText(title);
        tvTitle.setTextColor(titleTextColor);
        tvTitle.setTextSize(titleTextSize);
        tvTitle.setGravity(Gravity.CENTER);

        setBackgroundColor(0xFF59563);

        leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        leftParams.addRule(ALIGN_PARENT_LEFT, TRUE);
        addView(leftButton, leftParams);

        rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rightParams.addRule(ALIGN_PARENT_RIGHT, TRUE);
        addView(rightButton, rightParams);

        titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        titleParams.addRule(CENTER_IN_PARENT, TRUE);
        addView(tvTitle, titleParams);

        leftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.leftClick();
            }
        });

        rightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.rightClick();
            }
        });
    }
}
