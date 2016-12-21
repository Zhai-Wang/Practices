package com.shmj.mouzhai.recyclerviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.shmj.mouzhai.recyclerviewdemo.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvContent;
    private List<String> data;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();
        initViews();

        adapter = new MyAdapter(this, data, R.layout.item_single_textview);
        rvContent.setAdapter(adapter);

        setAsListView();

        //默认的动画效果
        rvContent.setItemAnimator(new DefaultItemAnimator());

        //点击事件
        setClickEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                adapter.addItem(1);
                break;
            case R.id.action_delete:
                adapter.deleteItem(1);
                break;
            case R.id.action_listView:
                //切换到 ListView
                adapter = new MyAdapter(this, data, R.layout.item_single_textview);
                setClickEvents();
                rvContent.setAdapter(adapter);
                rvContent.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.action_gridView:
                //切换到 GridView
                adapter = new MyAdapter(this, data, R.layout.item_single_textview);
                setClickEvents();
                rvContent.setAdapter(adapter);
                rvContent.setLayoutManager(new GridLayoutManager(this, 3));
                break;
            case R.id.action_hor_gridView:
                //切换到横向的 GridView
                adapter = new MyAdapter(this, data, R.layout.item_hor_textview);
                setClickEvents();
                rvContent.setAdapter(adapter);
                rvContent.setLayoutManager(new StaggeredGridLayoutManager(5,
                        StaggeredGridLayoutManager.HORIZONTAL));
                break;
            case R.id.action_staggered_gridView:
                //瀑布流
                Intent intent = new Intent(this, StaggeredActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setAsListView() {
        //设置为 ListView 的样式
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rvContent.setLayoutManager(linearLayoutManager);

        //添加分割线
//        rvContent.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
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

    //设置点击事件
    private void setClickEvents() {
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "CLICK : " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "LONG CLICK : " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
