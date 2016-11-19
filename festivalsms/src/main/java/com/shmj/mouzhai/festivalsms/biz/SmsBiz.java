package com.shmj.mouzhai.festivalsms.biz;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.telephony.SmsManager;

import com.shmj.mouzhai.festivalsms.Bean.SendedMsg;
import com.shmj.mouzhai.festivalsms.db.SmsProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

/**
 * 发送短信类
 * <p>
 * Created by Mouzhai on 2016/11/19.
 */

public class SmsBiz {

    private Context context;

    public SmsBiz(Context context){
        this.context = context;
    }

    public int sendMsg(String number, String msg, PendingIntent sentPi, PendingIntent deliverPi) {
        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> contents = smsManager.divideMessage(msg);
        for (String content : contents) {
            smsManager.sendTextMessage(number, null, content, sentPi, deliverPi);
        }
        return contents.size();
    }

    public int sendMsg(Set<String> numbers, SendedMsg sendedMsg, PendingIntent sentPi, PendingIntent deliverPi) {
        save(sendedMsg);
       int result = 0;
        for(String number: numbers){
            int count = sendMsg(number, sendedMsg.getMsg(), sentPi, deliverPi);
            result += count;
        }
        return result;
    }

    private void save (SendedMsg sendedMsg){
        sendedMsg.setDate(new Date());
        ContentValues contentValues = new ContentValues();
        contentValues.put(SendedMsg.COLUMN_DATE, sendedMsg.getDate().getTime());
        contentValues.put(SendedMsg.COLUMN_FES_NAME, sendedMsg.getFestivalName());
        contentValues.put(SendedMsg.COLUMN_MSG, sendedMsg.getMsg());
        contentValues.put(SendedMsg.COLUMN_NAME, sendedMsg.getName());
        contentValues.put(SendedMsg.COLUMN_NUMBER, sendedMsg.getNumber());

        context.getContentResolver().insert(SmsProvider.URI_SMS_ALL, contentValues);
    }
}
