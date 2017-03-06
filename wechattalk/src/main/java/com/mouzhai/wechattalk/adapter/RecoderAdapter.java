package com.mouzhai.wechattalk.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mouzhai.wechattalk.R;
import com.mouzhai.wechattalk.model.Recorder;

import java.util.List;

/**
 * 自定义 Adapter
 * <p>
 * Created by Mouzhai on 2017/3/6.
 */

public class RecoderAdapter extends ArrayAdapter<Recorder> {

    private LayoutInflater inflater;

    private int minRecordWidth;
    private int maxRecordWidth;

    public RecoderAdapter(Context context, List<Recorder> data) {
        super(context, -1, data);
        inflater = LayoutInflater.from(context);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        minRecordWidth = (int) (displayMetrics.widthPixels * 0.15f);
        maxRecordWidth = (int) (displayMetrics.widthPixels * 0.7f);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_record, parent, false);
            holder = new ViewHolder();
            holder.seconds = (TextView) convertView.findViewById(R.id.tv_record_time);
            holder.length = convertView.findViewById(R.id.fl_record_length);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.seconds.setText(Math.round(getItem(position).getTime()) + "\'");
        ViewGroup.LayoutParams params = holder.length.getLayoutParams();
        params.width = (int) (minRecordWidth + (maxRecordWidth / 60f * getItem(position).getTime()));

        return convertView;
    }

    private class ViewHolder{
        TextView seconds;
        View length;
    }
}
