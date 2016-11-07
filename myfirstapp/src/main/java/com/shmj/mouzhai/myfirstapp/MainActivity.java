package com.shmj.mouzhai.myfirstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.shmj.mouzhai.myfirstapp.firstapp.MyFirstActivity;
import com.shmj.mouzhai.myfirstapp.fragment.FragmentActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnFirstApp, btnFragment;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnFirstApp = (Button) findViewById(R.id.btn_first_app);
        btnFragment = (Button) findViewById(R.id.btn_fragment);

        btnFirstApp.setOnClickListener(this);
        btnFragment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_first_app:
                intent = new Intent(MainActivity.this, MyFirstActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_fragment:
                intent = new Intent(MainActivity.this, FragmentActivity.class);
                startActivity(intent);
                break;
        }
    }
}
