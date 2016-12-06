package com.shmj.mouzhai.downloaddemo.adapter;

import android.content.Context;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
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

public class FileListAdapter extends BaseAdapter{

    private Context context;
    private List<FileInfo> fileInfos;
    private Messenger messenger;

    public FileListAdapter(Context context, List<FileInfo> fileInfos) {
        this.context = context;
        this.fileInfos = fileInfos;
    }

    public void setMessenger(Messenger messenger){
        this.messenger = messenger;
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
        final FileInfo fileInfo = fileInfos.get(i);
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_main, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.tvFileName = (TextView) view.findViewById(R.id.tv_file_name);
            viewHolder.btnStart = (Button) view.findViewById(R.id.btn_start);
            viewHolder.btnStop = (Button) view.findViewById(R.id.btn_stop);
            viewHolder.pbProgress = (ProgressBar) view.findViewById(R.id.pb_progress);

            viewHolder.tvFileName.setText(fileInfo.getFileName());
            viewHolder.pbProgress.setMax(100);
            viewHolder.btnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Message msgStart = new Message();
                    msgStart.what = DownloadService.MSG_START;
                    msgStart.obj = fileInfo;
                    try {
                        messenger.send(msgStart);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
            viewHolder.btnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Message msgStart = new Message();
                    msgStart.what = DownloadService.MSG_STOP;
                    msgStart.obj = fileInfo;
                    try {
                        messenger.send(msgStart);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.pbProgress.setProgress(fileInfo.getFinished());
        return view;
    }

    /**
     * 更新列表项中的进度条
     */
    public void updateProgress(int id, int progress){
        FileInfo mFileInfo = fileInfos.get(id);
        mFileInfo.setFinished(progress);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView tvFileName;
        Button btnStart, btnStop;
        ProgressBar pbProgress;
    }
}
