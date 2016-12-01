package com.shmj.mouzhai.downloaddemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shmj.mouzhai.downloaddemo.R;
import com.shmj.mouzhai.downloaddemo.entities.FileInfo;
import com.shmj.mouzhai.downloaddemo.services.DownloadService;

import java.util.List;

/**
 * 文件列表适配器
 * <p>
 * Created by Mouzhai on 2016/12/1.
 */

public class FileListAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;
    private List<FileInfo> fileInfos;
    private FileInfo fileInfo = null;

    public FileListAdapter(Context context, List<FileInfo> fileInfos) {
        this.context = context;
        this.fileInfos = fileInfos;
    }

    @Override
    public int getCount() {
        return fileInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return fileInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_main, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.tvFileName = (TextView) view.findViewById(R.id.tv_file_name);
            viewHolder.btnStart = (Button) view.findViewById(R.id.btn_start);
            viewHolder.btnStop = (Button) view.findViewById(R.id.btn_stop);
            viewHolder.pbProgress = (ProgressBar) view.findViewById(R.id.pb_progress);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        fileInfo = fileInfos.get(i);
        viewHolder.tvFileName.setText(fileInfo.getFileName());
        viewHolder.pbProgress.setMax(100);
        viewHolder.pbProgress.setProgress(fileInfo.getFinished());
        viewHolder.btnStart.setOnClickListener(this);
        viewHolder.btnStop.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                Intent startIntent = new Intent(context, DownloadService.class);
                startIntent.setAction(DownloadService.ACTION_START);
                startIntent.putExtra("fileInfo", fileInfo);
                context.startService(startIntent);
                break;
            case R.id.btn_stop:
                Intent stopIntent = new Intent(context, DownloadService.class);
                stopIntent.setAction(DownloadService.ACTION_STOP);
                stopIntent.putExtra("fileInfo", fileInfo);
                context.startService(stopIntent);
                break;
        }
    }

    private static class ViewHolder {
        TextView tvFileName;
        Button btnStart, btnStop;
        ProgressBar pbProgress;
    }
}
