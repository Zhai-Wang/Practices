package com.shmj.mouzhai.festivalsms.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mouzhai on 2016/11/16.
 */

public class FestivalLab {

    private static FestivalLab mInstance;

    private List<Festival> mList = new ArrayList<>();

    public static FestivalLab getInstance() {
        if (mInstance == null) {
            synchronized (FestivalLab.class) {//防止多线程同时判断导致互斥
                if (mInstance == null) {
                    mInstance = new FestivalLab();
                }
            }
        }
        return mInstance;
    }

    private FestivalLab() {
        //在构造方法里获取数据,显示具体有哪些节日
        mList.add(new Festival(1, "元旦节"));
        mList.add(new Festival(2, "春节节"));
        mList.add(new Festival(3, "端午节"));
        mList.add(new Festival(4, "七夕节"));
        mList.add(new Festival(5, "儿童节"));
        mList.add(new Festival(6, "国庆节"));
        mList.add(new Festival(7, "中秋节"));
        mList.add(new Festival(8, "圣诞节"));
    }

    /**
     * 获取节日名称
     *
     * @return 为了防止数据可能被修改，导致问题，此处返回一个 List
     */
    public List<Festival> getmList() {
        return new ArrayList<>(mList);
    }

    /**
     * 根据 id 获取 Festival 对象
     *
     * @param id 需要查找的 id 号
     * @return Festival 对象；如果没有 id，返回 null
     */
    public Festival getFestivalById(int id) {
        for (Festival festival : mList) {
            if (festival.getId() == id) {
                return festival;
            }
        }
        return null;
    }
}
