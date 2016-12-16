package com.shmj.mouzhai.treeviewdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shmj.mouzhai.treeviewdemo.R;
import com.shmj.mouzhai.treeviewdemo.utils.Node;
import com.shmj.mouzhai.treeviewdemo.utils.TreeHelper;

import java.util.List;

/**
 * 设置 View 的 Adapter
 * <p>
 * Created by Mouzhai on 2016/12/15.
 */

public class SimpleTreeListViewAdapter<T> extends TreeListViewAdapter<T> {

    public SimpleTreeListViewAdapter(Context context, ListView tree, List<T> datas, int defaultLevel)
            throws IllegalArgumentException, IllegalAccessException {
        super(tree, context, datas, defaultLevel);
    }

    @Override
    public View getConvertView(Node node, int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_treeview, parent, false);
            holder = new ViewHolder();
            holder.mIv = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.mTv = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //设置图标
        if (node.getIcon() == -1) {
            holder.mIv.setVisibility(View.INVISIBLE);
        } else {
            holder.mIv.setVisibility(View.VISIBLE);
            holder.mIv.setImageResource(node.getIcon());
        }
        //设置文字内容
        holder.mTv.setText(node.getName());

        return convertView;
    }

    /**
     * 动态添加节点
     */
    public void addExtraNode(int position, String s) {
        Node node = mVisibleNodes.get(position);
        int indexOf = mAllNodes.indexOf(node);

        Node extraNode = new Node(-1, node.getId(), s);
        extraNode.setParent(node);
        node.getChildren().add(extraNode);

        mAllNodes.add(indexOf + 1, extraNode);
        mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
        notifyDataSetChanged();
    }

    private class ViewHolder {
        private TextView mTv;
        private ImageView mIv;
    }
}
