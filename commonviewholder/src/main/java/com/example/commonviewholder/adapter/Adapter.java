package com.example.commonviewholder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.commonviewholder.R;
import com.example.commonviewholder.bean.Bean;

import java.util.List;

/**
 * Created by 某宅 on 2016/9/17.
 */
public class Adapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Bean> mDatas;

    public Adapter(Context context, List<Bean> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_listview, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.mTitle = (TextView) view.findViewById(R.id.item_listview_title);
            viewHolder.mContent = (TextView) view.findViewById(R.id.item_listview_content);
            viewHolder.mTime = (TextView) view.findViewById(R.id.item_listview_time);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Bean bean = mDatas.get(i);
        viewHolder.mTitle.setText(bean.getItemTitle());
        viewHolder.mContent.setText(bean.getItemContent());
        viewHolder.mTime.setText(bean.getItemTime());
        return view;
    }

    private class ViewHolder {
        TextView mTitle;
        TextView mContent;
        TextView mTime;
    }
}
