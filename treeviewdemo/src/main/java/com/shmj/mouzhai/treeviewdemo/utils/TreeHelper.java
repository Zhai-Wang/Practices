package com.shmj.mouzhai.treeviewdemo.utils;

import com.shmj.mouzhai.treeviewdemo.R;
import com.shmj.mouzhai.treeviewdemo.annotation.TreeNodeId;
import com.shmj.mouzhai.treeviewdemo.annotation.TreeNodeLabel;
import com.shmj.mouzhai.treeviewdemo.annotation.TreeNodePid;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 有关数据转换的工具类
 * <p>
 * Created by Mouzhai on 2016/12/13.
 */

public class TreeHelper {

    /**
     * 利用反射与注解，将原始数据转化为 Node 格式的树形数据
     */
    private static <T> List<Node> convertData2Node(List<T> datas)
            throws IllegalArgumentException, IllegalAccessException {
        List<Node> nodes = new ArrayList<Node>();
        Node node = null;

        for (T t : datas) {
            int id = -1;
            int pid = -1;
            String label = null;

            //获取几个字段的值
            node = new Node();
            Class clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
            //遍历 Class 的属性，根据相应的注解获取值
            for (Field field : fields) {
                if (field.getAnnotation(TreeNodeId.class) != null) {
                    field.setAccessible(true);
                    id = field.getInt(t);
                }
                if (field.getAnnotation(TreeNodePid.class) != null) {
                    field.setAccessible(true);
                    pid = field.getInt(t);
                }
                if (field.getAnnotation(TreeNodeLabel.class) != null) {
                    field.setAccessible(true);
                    label = (String) field.get(t);
                }
            }
            node = new Node(id, pid, label);
            nodes.add(node);
        }

        //设置节点之间的对应关系
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);

            for (int j = i + 1; j < nodes.size(); j++) {
                Node m = nodes.get(j);

                if (m.getpId() == n.getId()) {
                    n.getChildren().add(m);
                    m.setParent(n);
                } else if (m.getId() == n.getpId()) {
                    m.getChildren().add(n);
                    n.setParent(m);
                }
            }
        }

        //设置图标
        for (Node n : nodes) {
            setNodeIcon(n);
        }

        return nodes;
    }

    /**
     * 为数据排序
     * 这里直接获取原始数据，在方法里转换数据格式，以减少外部调用的操作
     */
    public static <T> List<Node> getSortedNodes(List<T> datas, int defaultExpandLevel)
            throws IllegalArgumentException, IllegalAccessException {
        List<Node> result = new ArrayList<Node>();
        List<Node> nodes = convertData2Node(datas);
        //获取树的根节点
        List<Node> rootNodes = getRootNodes(nodes);

        //遍历根节点，添加相应的子节点
        for (Node node : rootNodes) {
            addNode(result, node, defaultExpandLevel, 1);
        }
        return result;
    }

    /**
     * 向根节点中添加所有子节点
     */
    private static void addNode(List<Node> results, Node node, int defaultExpandLevel, int currentLevel) {
        results.add(node);

        //比较层级，设置菜单是否为展开状态
        if (defaultExpandLevel >= currentLevel) {
            node.setExpand(true);
        }
        //判断是否为叶子节点
        if (node.isLeaf())
            return;
        //如果不是叶子节点，递归调用此方法，循环添加子节点
        for (int i = 0; i < node.getChildren().size(); i++) {
            addNode(results, node.getChildren().get(i), defaultExpandLevel, currentLevel + 1);
        }
    }

    /**
     * 过滤出需要显示的数据
     * 即所有的根节点，以及根据根节点的展开与否决定是否显示子节点
     */
    public static List<Node> filterVisibleNodes(List<Node> nodes) {
        List<Node> results = new ArrayList<>();

        //遍历集合，找出所有符合条件的节点
        for (Node node : nodes) {
            if (node.isRoot() || node.isParentExpand()) {
                setNodeIcon(node);
                results.add(node);
            }
        }
        return results;
    }

    /**
     * 从所有节点中，获取根节点集合
     */
    private static List<Node> getRootNodes(List<Node> nodes) {
        List<Node> roots = new ArrayList<>();

        for (Node node : nodes) {
            if (node.isRoot()) {
                roots.add(node);
            }
        }
        return roots;
    }

    /**
     * 为节点设置图标
     */
    private static void setNodeIcon(Node n) {
        if (n.getChildren().size() > 0 && n.isExpand()) {//有子项且展开
            n.setIcon(R.mipmap.tree_ex);
        } else if (n.getChildren().size() > 0 && !n.isExpand()) {//有子项未展开
            n.setIcon(R.mipmap.tree_ec);
        } else {//没有图标
            n.setIcon(-1);
        }
    }
}
