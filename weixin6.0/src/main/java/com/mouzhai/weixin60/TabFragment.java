package com.mouzhai.weixin60;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 自定义内容 Fragment，这里简单地显示了一行文本
 * <p>
 * Created by Mouzhai on 2016/12/26.
 */

public class TabFragment extends android.support.v4.app.Fragment {

    public static final String TITLE = "title";
    private String mTitle = "Default";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE);
        }

        TextView textView = new TextView(getActivity());
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(Color.parseColor("#ffffffff"));
        textView.setText(mTitle);

        return textView;
    }
}
