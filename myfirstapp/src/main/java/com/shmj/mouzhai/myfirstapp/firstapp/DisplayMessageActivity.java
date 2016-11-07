package com.shmj.mouzhai.myfirstapp.firstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shmj.mouzhai.myfirstapp.MainActivity;
import com.shmj.mouzhai.myfirstapp.R;

public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MyFirstActivity.EXTRA_MESSAGE);
        TextView textView = new TextView(this);
        textView.setText(message);
        textView.setTextSize(40);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);
    }
}
