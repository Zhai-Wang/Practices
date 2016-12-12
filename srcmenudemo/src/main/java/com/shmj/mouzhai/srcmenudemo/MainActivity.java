package com.shmj.mouzhai.srcmenudemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.shmj.mouzhai.srcmenudemo.view.SrcMenu;

public class MainActivity extends AppCompatActivity {

    private SrcMenu mSrcMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSrcMenu = (SrcMenu) findViewById(R.id.src_menu);
        mSrcMenu.setOnMenuItemClickListener(new SrcMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(MainActivity.this, position + ":" + view.getTag(), Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
