package com.shmj.mouzhai.treeviewdemo.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形节点的数据格式
 * <p>
 * Created by Mouzhai on 2016/12/14.
 */

public class Node {

    private int id;
    private int pId = 0;//根节点
    private String name;
    private int level;//树的层级
    private boolean isExpand = false;//是否是展开状态
    private int icon;
    private Node parent;
    private List<Node> children = new ArrayList<>();

    public Node(int id, int pId, String name) {
        this.id = id;
        this.pId = pId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 得到当前节点的层级
     */
    public int getLevel() {
        return parent == null ? 0 : parent.getLevel() + 1;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isExpand() {
        return isExpand;
    }

    /**
     * 设置节点展开状态为 false
     */
    public void setExpand(boolean expand) {
        isExpand = expand;
        //遍历子节点
        if (!isExpand) {
            for (Node node : children) {
                node.setExpand(false);
            }
        }
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    /**
     * 判断是否是根节点
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 判断当前根节点是否是展开状态
     */
    public boolean isParentExpand() {
        if (parent == null)
            return false;
        return parent.isExpand();
    }

    /**
     * 判断是否是叶子节点
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }
}
