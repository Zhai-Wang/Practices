package com.shmj.mouzhai.recyclerviewdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shmj.mouzhai.recyclerviewdemo.R;

import java.util.List;

/**
 * 自定义 RecyclerView 适配器
 * <p>
 * Created by Mouzhai on 2016/12/20.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private LayoutInflater mInflater;
    private List<String> mDatas;
    private int layoutId;
    private OnItemClickListener mOnItemClickListener;

    public MyAdapter(Context context, List<String> data, int layoutId) {
        this.mDatas = data;
        this.layoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(layoutId, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.textView.setText(mDatas.get(position));
        setUpClickEvent(holder);
    }

    protected void setUpClickEvent(final MyViewHolder holder) {
        //设置点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onLongItemClidk(holder.itemView, holder.getLayoutPosition());
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 添加 Item
     */
    public void addItem(int position) {
        mDatas.add(position, "Insert One");
        notifyItemInserted(position);
    }

    /**
     * 删除 Item
     */
    public void deleteItem(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * item 的点击回调接口
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onLongItemClidk(View view, int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
