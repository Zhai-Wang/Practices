package com.shmj.mouzhai.treeviewdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.shmj.mouzhai.treeviewdemo.utils.Node;
import com.shmj.mouzhai.treeviewdemo.utils.TreeViewHelper;

import java.util.List;

/**
 * 自定义 TreeView 适配器帮助类，开放了一些设置 View 的接口
 * <p>
 * Created by Mouzhai on 2016/12/15.
 */

public abstract class TreeListViewAdapter<T> extends BaseAdapter {

    List<Node> mAllNodes;
    List<Node> mVisibleNodes;
    LayoutInflater mInflater;
    private OnTreeNodeClickListener mOnTreeNodeClickListener;

    TreeListViewAdapter(Context context, ListView tree, List<T> datas, int defaultLevel)
            throws IllegalAccessException {
        mInflater = LayoutInflater.from(context);
        mAllNodes = TreeViewHelper.getSortedNodes(datas, defaultLevel);
        mVisibleNodes = TreeViewHelper.filterVisibleNodes(mAllNodes);

        tree.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int positon, long id) {
                expandOrCollapse(positon);
                if (mOnTreeNodeClickListener != null) {
                    mOnTreeNodeClickListener.onClick(mVisibleNodes.get(positon), positon);
                }
            }
        });
    }

    public void setOnTreeNodeClickListener(OnTreeNodeClickListener mOnTreeNodeClickListener) {
        this.mOnTreeNodeClickListener = mOnTreeNodeClickListener;
    }

    /**
     * 点击收缩或展开
     */
    private void expandOrCollapse(int positon) {
        Node n = mVisibleNodes.get(positon);
        if (n != null) {
            if (!n.isLeaf()) {
                n.setExpand(!n.isExpand());
                mVisibleNodes = TreeViewHelper.filterVisibleNodes(mAllNodes);
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getCount() {
        return mVisibleNodes.size();
    }

    @Override
    public Object getItem(int i) {
        return mVisibleNodes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Node node = mVisibleNodes.get(i);
        view = getConvertView(node, i, view, viewGroup);

        //设置内边距
        view.setPadding(node.getLevel() * 30, 3, 3, 3);
        return view;
    }

    /**
     * 提供给外部设置样式的方法
     */
    public abstract View getConvertView(Node node, int position, View convertView, ViewGroup parent);

    /**
     * 自定义点击事件的回调接口
     */
    public interface OnTreeNodeClickListener {
        void onClick(Node node, int position);
    }
}
