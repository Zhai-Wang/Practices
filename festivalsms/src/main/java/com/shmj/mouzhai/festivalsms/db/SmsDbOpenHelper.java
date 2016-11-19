package com.shmj.mouzhai.festivalsms.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.shmj.mouzhai.festivalsms.Bean.SendedMsg;

/**
 * Created by Mouzhai on 2016/11/19.
 */

public class SmsDbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "sms.db";
    private static final int DB_VERSION = 1;

    private SmsDbOpenHelper(Context context) {
        super(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
    }

    private static SmsDbOpenHelper smsDbOpenHelper;

    public static SmsDbOpenHelper getInstance(Context context){
        if (smsDbOpenHelper == null){
            synchronized (SmsDbOpenHelper.class){
                if(smsDbOpenHelper == null){
                    smsDbOpenHelper = new SmsDbOpenHelper(context);
                }
            }
        }
        return smsDbOpenHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table " + SendedMsg.TABLE_NAME + " ( " +
                " _id integer primary key autoincrement , " +
                SendedMsg.COLUMN_DATE + " integer , " +
                SendedMsg.COLUMN_FES_NAME + " text , " +
                SendedMsg.COLUMN_MSG + " text , " +
                SendedMsg.COLUMN_NAME + " text , " +
                SendedMsg.COLUMN_NUMBER + " text " + " ) ";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
