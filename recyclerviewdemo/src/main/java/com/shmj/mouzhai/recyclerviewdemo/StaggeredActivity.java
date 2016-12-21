package com.shmj.mouzhai.recyclerviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.shmj.mouzhai.recyclerviewdemo.adapter.MyAdapter;
import com.shmj.mouzhai.recyclerviewdemo.adapter.StaggeredAdapter;

import java.util.ArrayList;
import java.util.List;

public class StaggeredActivity extends AppCompatActivity {
    private RecyclerView rvContent;
    private List<String> data;
    private StaggeredAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();
        initViews();

        adapter = new StaggeredAdapter(this, data, R.layout.item_single_textview);
        rvContent.setAdapter(adapter);
        setAsStaggeredView();

        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(StaggeredActivity.this, "CLICK: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {
                adapter.deleteItem(position);
            }
        });
    }

    private void setAsStaggeredView() {
        //设置为瀑布流
        rvContent.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
    }

    private void initDatas() {
        data = new ArrayList<>();

        for (int i = 'A'; i <= 'z'; i++) {
            data.add("" + (char) i);
        }
    }

    private void initViews() {
        rvContent = (RecyclerView) findViewById(R.id.rv_content);
    }
}
