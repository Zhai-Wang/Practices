package com.mouzhai.mywidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;

/**
 * 天气小组件
 */
public class WeatherService extends Service implements WeatherSearch.OnWeatherSearchListener {

    private WeatherSearchQuery mQuery;
    private WeatherSearch mSearch;

    @Override
    public void onCreate() {
        super.onCreate();
        //检索参数为城市和天气类型，实况天气为WEATHER_TYPE_LIVE、天气预报为WEATHER_TYPE_FORECAST
        mQuery = new WeatherSearchQuery("北京", WeatherSearchQuery.WEATHER_TYPE_LIVE);
        mSearch = new WeatherSearch(this);
        mSearch.setOnWeatherSearchListener(this);
        mSearch.setQuery(mQuery);
        mSearch.searchWeatherAsyn(); //异步搜索
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * 实时天气查询回调
     */
    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
        if (rCode == 1000) {
            if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                LocalWeatherLive weatherlive = weatherLiveResult.getLiveResult();
                String time = weatherlive.getReportTime() + "发布";//时间
                String weather = weatherlive.getWeather();//天气
                String temp = weatherlive.getTemperature() + "°";//温度
                String wind = weatherlive.getWindDirection() + "风     "
                        + weatherlive.getWindPower() + "级";//风力
                String hum = "湿度         " + weatherlive.getHumidity() + "%";//湿度

                //将信息展示到面板上
                RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.weather_widget);
                remoteViews.setTextViewText(R.id.tv_weather, weather);
                remoteViews.setTextViewText(R.id.tv_time, time);
                remoteViews.setTextViewText(R.id.tv_wind, wind);
                remoteViews.setTextViewText(R.id.tv_temp, temp);
                remoteViews.setTextViewText(R.id.tv_hum, hum);

                AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
                ComponentName name = new ComponentName(getApplicationContext(), WeatherWidget.class);
                manager.updateAppWidget(name, remoteViews);
            } else {
                Toast.makeText(this, "没有获取到天气信息", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Error: " + rCode, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 预报天气查询
     */
    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int rCode) {

    }

}
