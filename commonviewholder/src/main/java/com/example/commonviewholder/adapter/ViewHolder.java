package com.example.commonviewholder.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 某宅 on 2016/9/17.
 */
public class ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;
    private int mPosition;

    private ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        mViews = new SparseArray<>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mConvertView.setTag(this);
        mPosition = position;
    }

    /**
     * 获取 ViewHolder 的实例
     * 如果 convertView 存在，直接从 convertView 获取
     * 否则 new 一个
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId    布局id
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        } else {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mPosition = position;//防止 item 误复用
            return (ViewHolder) convertView.getTag();
        }
    }

    /**
     * 通过 ViewId 获取控件实例
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 子控件的通用方法
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }
}
