package com.mouzhai.weixin60;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private List<Fragment> mTabs = new ArrayList<>();
    private FragmentPagerAdapter adapter;
    private ChangeIconColorWithText indicatorOne, indicatorTwo, indicatorThree, indicatorFour;

    private List<ChangeIconColorWithText> mTabIndicators = new ArrayList<>();

    private String[] mTitles = new String[]{
            "First Fragment", "Second Fragment", "Third Fragment", "Forth Fragment"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //使各子菜单可以显示
        setOverflowButtonAlways();

        if (getActionBar() != null) {
            getActionBar().setDisplayShowHomeEnabled(false);
        }

        initView();
        initDatas();
        initEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        //使弹出的子菜单项显示图标
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onClick(View view) {
        //重置各项按钮的颜色
        resetOtherTabs();

        //使被点击的按钮变绿，同时切换到相应的界面
        switch (view.getId()) {
            case R.id.indicator_one:
                mTabIndicators.get(0).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.indicator_two:
                mTabIndicators.get(1).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.indicator_three:
                mTabIndicators.get(2).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(2, false);
                break;
            case R.id.indicator_four:
                mTabIndicators.get(3).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(3, false);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            ChangeIconColorWithText left = mTabIndicators.get(position);
            ChangeIconColorWithText right = mTabIndicators.get(position + 1);
            left.setIconAlpha(1.0f - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        indicatorOne = (ChangeIconColorWithText) findViewById(R.id.indicator_one);
        mTabIndicators.add(indicatorOne);
        indicatorTwo = (ChangeIconColorWithText) findViewById(R.id.indicator_two);
        mTabIndicators.add(indicatorTwo);
        indicatorThree = (ChangeIconColorWithText) findViewById(R.id.indicator_three);
        mTabIndicators.add(indicatorThree);
        indicatorFour = (ChangeIconColorWithText) findViewById(R.id.indicator_four);
        mTabIndicators.add(indicatorFour);

        //默认第一个按钮是选中状态
        indicatorOne.setIconAlpha(1.0f);
    }

    private void initDatas() {
        for (String title : mTitles) {
            TabFragment fragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putString(TabFragment.TITLE, title);
            fragment.setArguments(bundle);
            mTabs.add(fragment);
        }

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return mTabs.get(position);
            }

            @Override
            public int getCount() {
                return mTabs.size();
            }
        };

        mViewPager.setAdapter(adapter);
    }

    private void initEvents() {
        indicatorOne.setOnClickListener(this);
        indicatorTwo.setOnClickListener(this);
        indicatorThree.setOnClickListener(this);
        indicatorFour.setOnClickListener(this);

        mViewPager.addOnPageChangeListener(this);
    }

    /**
     * 显示更多菜单项
     */
    private void setOverflowButtonAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKey = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKey.setAccessible(true);
            menuKey.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重置各按钮颜色，使得各项按钮均不显示颜色
     */
    private void resetOtherTabs() {
        for (int i = 0; i < mTabIndicators.size(); i++) {
            mTabIndicators.get(i).setIconAlpha(0);
        }
    }
}
