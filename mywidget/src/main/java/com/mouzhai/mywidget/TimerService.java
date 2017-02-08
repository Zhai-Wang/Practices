package com.mouzhai.mywidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 时钟小组件功能
 * <p>
 * Created by Mouzhai on 2017/1/3.
 */

public class TimerService extends Service {

    private Timer timer;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //开始计时
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateViews();
            }
        }, 0, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //停止计时
        timer = null;
    }

    /**
     * 在 RemoteView 上显示时间文本
     */
    private void updateViews() {
        String time = format.format(new Date());
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.new_app_widget);
        remoteViews.setTextViewText(R.id.appwidget_text, time);

        //利用 AppWidgetManager 调用 updateAppWidget() 方法，更新 view
        AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
        ComponentName name = new ComponentName(getApplicationContext(), NewAppWidget.class);
        manager.updateAppWidget(name, remoteViews);
    }
}
