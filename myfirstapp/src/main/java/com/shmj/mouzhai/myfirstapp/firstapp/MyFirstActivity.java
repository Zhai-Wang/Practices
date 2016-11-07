package com.shmj.mouzhai.myfirstapp.firstapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.shmj.mouzhai.myfirstapp.R;
import com.shmj.mouzhai.myfirstapp.firstapp.DisplayMessageActivity;

public class MyFirstActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.shmj.mouzhai.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_first);
        setUpActionBar();

        //noinspection StatementWithEmptyBody
        if (savedInstanceState != null){
            //获取存储的值
        }else {
            //设置默认值
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Activity 销毁时存储信息
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //如果 Bundle 对象存在，继续往下执行
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //确保所有资源得到释放，停止一切活动
        android.os.Debug.stopMethodTracing();
    }

    /**
     * send 按钮的点击事件
     * 发送 Intent
     *
     * @param view 可操作的 View 对象
     */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    /**
     * 判断当前版本是否支持控件
     */
    private void setUpActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            android.app.ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }
}
