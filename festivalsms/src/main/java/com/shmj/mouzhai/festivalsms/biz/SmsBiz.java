package com.shmj.mouzhai.festivalsms.biz;

import android.app.PendingIntent;
import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 发送短信类
 * <p>
 * Created by Mouzhai on 2016/11/19.
 */

public class SmsBiz {

    public int sendMsg(String number, String msg, PendingIntent sentPi, PendingIntent deliverPi) {
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> contents = smsManager.divideMessage(msg);
        for (String content : contents) {
            smsManager.sendTextMessage(number, null, content, sentPi, deliverPi);
        }
        return contents.size();
    }

    public int sendMsg(Set<String> numbers, String msg, PendingIntent sentPi, PendingIntent deliverPi) {
       int result = 0;
        for(String number: numbers){
            int count = sendMsg(number, msg, sentPi, deliverPi);
            result += count;
        }
        return result;
    }
}
