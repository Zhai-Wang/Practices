package com.shmj.mouzhai.festivalsms.Bean;

/**
 * 节日信息实体类
 * <p>
 * Created by Mouzhai on 2016/11/16.
 */

public class Festival {

    private int id;
    private String name;

    public Festival(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
