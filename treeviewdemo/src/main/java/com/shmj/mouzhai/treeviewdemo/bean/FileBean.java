package com.shmj.mouzhai.treeviewdemo.bean;

import com.shmj.mouzhai.treeviewdemo.annotation.TreeNodeId;
import com.shmj.mouzhai.treeviewdemo.annotation.TreeNodeLabel;
import com.shmj.mouzhai.treeviewdemo.annotation.TreeNodePid;

/**
 * 文件信息实体类
 * <p>
 * Created by Mouzhai on 2016/12/13.
 */

public class FileBean {

    //为相应字段添加注解
    @TreeNodeId
    private int id;
    @TreeNodePid
    private int pId;
    @TreeNodeLabel
    private String label;

    private String des;

    public FileBean(int id, int pId, String label) {
        this.id = id;
        this.pId = pId;
        this.label = label;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
