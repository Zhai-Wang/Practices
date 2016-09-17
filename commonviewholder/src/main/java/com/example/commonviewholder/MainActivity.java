package com.example.commonviewholder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.commonviewholder.adapter.Adapter;
import com.example.commonviewholder.bean.Bean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private List<Bean> mDatas;
    private Bean mBean;
    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    /**
     * 初始化 View
     */
    private void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(mAdapter);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mDatas = new ArrayList<>();
        //假装自己有很多数据
        mBean = new Bean("TITLE1", "CONTENT", "2016-9-18");
        mDatas.add(mBean);
        mBean = new Bean("TITLE2", "CONTENT", "2016-9-18");
        mDatas.add(mBean);
        mBean = new Bean("TITLE3", "CONTENT", "2016-9-18");
        mDatas.add(mBean);
        mBean = new Bean("TITLE4", "CONTENT", "2016-9-18");
        mDatas.add(mBean);
        mBean = new Bean("TITLE5", "CONTENT", "2016-9-18");
        mDatas.add(mBean);
        mBean = new Bean("TITLE6", "CONTENT", "2016-9-18");
        mDatas.add(mBean);
        mBean = new Bean("TITLE7", "CONTENT", "2016-9-18");
        mDatas.add(mBean);
        mBean = new Bean("TITLE8", "CONTENT", "2016-9-18");
        mDatas.add(mBean);
        mBean = new Bean("TITLE9", "CONTENT", "2016-9-18");
        mDatas.add(mBean);
        mBean = new Bean("TITLE10", "CONTENT", "2016-9-18");
        mDatas.add(mBean);
        //获得自定义 Adapter 的实例
        mAdapter = new Adapter(this, mDatas);
    }
}
