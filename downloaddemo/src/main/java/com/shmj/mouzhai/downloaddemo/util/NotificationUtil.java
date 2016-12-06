package com.shmj.mouzhai.downloaddemo.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.shmj.mouzhai.downloaddemo.MainActivity;
import com.shmj.mouzhai.downloaddemo.R;
import com.shmj.mouzhai.downloaddemo.entities.FileInfo;
import com.shmj.mouzhai.downloaddemo.services.DownloadService;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义通知帮助类
 * <p>
 * Created by Mouzhai on 2016/12/6.
 */

public class NotificationUtil {

    private NotificationManager notificationManager = null;
    private Context context;

    private Map<Integer, Notification> mNotifications = null;

    public NotificationUtil(Context context) {
        this.context = context;
        //获取系统通知服务
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //创建通知的集合
        mNotifications = new HashMap<>();
    }

    /**
     * 显示通知
     */
    public void showNotification(FileInfo fileInfo) {
        //判断通知是否已经显示了
        if (!mNotifications.containsKey(fileInfo.getId())) {
            //创建通知
            Notification notification = new Notification();
            //设置滚动文字
            notification.tickerText = fileInfo.getFileName() + "开始下载";
            //设置显示时间
            notification.when = System.currentTimeMillis();
            //设置图标
            notification.icon = R.mipmap.ic_launcher;
            //设置特性
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            //设置通知栏点击事件,点击后跳转到 MainActivity
            Intent intent = new Intent(context, MainActivity.class);
            notification.contentIntent = PendingIntent.getActivity(context, 0, intent, 0);
            //创建 RemoteView
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification);
            //设置开始按钮点击事件
            Intent intentStart = new Intent(context, DownloadService.class);
            intentStart.setAction(DownloadService.ACTION_START);
            intentStart.putExtra("fileInfo", fileInfo);
            PendingIntent pendingIntentStart = PendingIntent.getService(context, 0, intentStart, 0);
            remoteViews.setOnClickPendingIntent(R.id.btn_start, pendingIntentStart);
            //设置结束按钮点击事件
            Intent intentStop = new Intent(context, DownloadService.class);
            intentStop.setAction(DownloadService.ACTION_STOP);
            intentStop.putExtra("fileInfo", fileInfo);
            PendingIntent pendingIntentStop = PendingIntent.getService(context, 0, intentStop, 0);
            remoteViews.setOnClickPendingIntent(R.id.btn_stop, pendingIntentStop);
            //设置通知栏的 TextView
            remoteViews.setTextViewText(R.id.tv_file_name, fileInfo.getFileName());
            //设置 Notification 视图
            notification.contentView = remoteViews;
            //发出通知
            notificationManager.notify(fileInfo.getId(), notification);
            //添加通知到集合中
            mNotifications.put(fileInfo.getId(), notification);
        }
    }

    /**
     * 取消通知
     */
    public void cancleNotification(int id){
        notificationManager.cancel(id);
        mNotifications.remove(id);
    }

    /**
     * 更新进度条
     */
    public void updateNotification(int id, int progress){
        Notification notification = mNotifications.get(id);
        if (notification != null){
            //修改进度条
            notification.contentView.setProgressBar(R.id.pb_progress, 100, progress, false);
            notificationManager.notify(id, notification);
        }
    }
}
