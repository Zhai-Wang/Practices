package com.zhai.topbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TopBar topBar = (TopBar) findViewById(R.id.top_bar);
        topBar.setOnTopBarClickListener(new TopBar.topBarClickListener(){

            @Override
            public void leftClick() {
                Toast.makeText(MainActivity.this, "left click!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rightClick() {
                Toast.makeText(MainActivity.this, "right click!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
