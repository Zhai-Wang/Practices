package com.shmj.mouzhai.downloaddemo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.shmj.mouzhai.downloaddemo.entities.ThreadInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据访问接口实现类
 * <p>
 * Created by Mouzhai on 2016/11/28.
 */

public class ThreadPortImpl implements ThreadPort {

    private MyDBHelper myDBHelper = null;

    public ThreadPortImpl(Context context) {
        myDBHelper = new MyDBHelper(context);
    }

    @Override
    public void insertThread(ThreadInfo threadInfo) {
        SQLiteDatabase database = myDBHelper.getWritableDatabase();
        database.execSQL("insert into thread_info(thread_id, url, start, end, finished) " +
                        "values(?,?,?,?,?)",
                new Object[]{threadInfo.getId(), threadInfo.getUrl(), threadInfo.getStart(),
                        threadInfo.getEnd(), threadInfo.getFinished()});
        database.close();
    }

    @Override
    public void deleteThread(String url, int thread_id) {
        SQLiteDatabase database = myDBHelper.getWritableDatabase();
        database.execSQL("delete from thread_info where url = ? and thread_id = ?",
                new Object[]{url, thread_id});
        database.close();
    }

    @Override
    public void updateThread(String url, int thread_id, int finished) {
        SQLiteDatabase database = myDBHelper.getWritableDatabase();
        database.execSQL("update thread_info set finished = ? where url = ? and thread_id = ?",
                new Object[]{finished, url, thread_id});
        database.close();
    }

    @Override
    public List<ThreadInfo> getThreads(String url) {
        SQLiteDatabase database = myDBHelper.getWritableDatabase();
        List<ThreadInfo> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from thread_info where url = ?",
                new String[]{url});
        while(cursor.moveToNext()){
            ThreadInfo threadInfo = new ThreadInfo();
            threadInfo.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadInfo.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            threadInfo.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            threadInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            list.add(threadInfo);
        }
        database.close();
        cursor.close();
        return list;
    }

    @Override
    public boolean isExists(String url, int thread_id) {
        SQLiteDatabase database = myDBHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(
                "select * from thread_info where url = ? and thread_id = ?",
                new String[]{url, thread_id + ""});
        boolean exists = cursor.moveToNext();
        cursor.close();
        database.close();
        return exists;
    }
}
