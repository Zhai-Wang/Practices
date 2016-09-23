package com.example.commonviewholder.adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.commonviewholder.R;
import com.example.commonviewholder.bean.Bean;

import java.util.List;

/**
 * Created by 某宅 on 2016/9/17.
 */
public class Adapter extends CommonAdapter<Bean> {

    public Adapter(Context context, List<Bean> datas) {
        super(context, datas, R.layout.item_listview);
    }

    @Override
    public void convert(ViewHolder viewHolder, Bean bean) {
        viewHolder.setText(R.id.item_listview_title, bean.getItemTitle())
                .setText(R.id.item_listview_content, bean.getItemContent())
                .setText(R.id.item_listview_time, bean.getItemTime());
    }
}
