package com.example.commonviewholder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.commonviewholder.R;

import java.util.List;

/**
 * Created by 某宅 on 2016/9/17.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    protected List<T> mDatas;
    protected Context mContext;
    protected LayoutInflater inflater;
    private int layoutId;

    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        mContext = context;
        mDatas = datas;
        inflater = LayoutInflater.from(mContext);
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = ViewHolder.get(mContext, view, viewGroup, layoutId, i);
        convert(viewHolder, getItem(i));
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder viewHolder, T t);
}
