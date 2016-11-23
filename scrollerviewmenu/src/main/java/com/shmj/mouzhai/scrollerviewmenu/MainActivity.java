package com.shmj.mouzhai.scrollerviewmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.shmj.mouzhai.scrollerviewmenu.view.SlidingMenu;

public class MainActivity extends AppCompatActivity {

    private SlidingMenu leftMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);

        leftMenu = (SlidingMenu) findViewById(R.id.left_menu);
    }

    public void toggleMenu(View view) {
        leftMenu.toggle();
    }
}
