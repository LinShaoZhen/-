package com.example.administrator.essayjoke.RecycleView.CommonAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by nana on 2018/9/19.
 * RecyclerView的通用Adapter
 */

public abstract class RecyclerCommonAdapter<DATA> extends RecyclerView.Adapter<ViewHolder> {
    //条目id不一样那么只能通过参数传递
    private int mLayoutId;
    //参数通用只能用泛型
    protected List<DATA> mData;
    protected Context mContext;
    //实例化View的LayoutInflate
    private LayoutInflater mInflater;

    public RecyclerCommonAdapter(Context context, List<DATA> data, int layoutId) {
        this.mData = data;
        this.mLayoutId = layoutId;
        this.mContext = context;
        //创建View
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(mLayoutId, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //ViewHolder优化
        convert(holder, mData.get(position), position);
        //条目的点击事件
        if (mItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.setOnClickListener(position);
                }
            });
        }
        if (mItemLongClickListener!=null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return mItemLongClickListener.onItemLongClick(position);
                }
            });
        }
    }

    /**
     * 把必要参数传递出去
     *
     * @param holder   holder
     * @param item     当前位置的条目
     * @param position 当前位置
     */
    protected abstract void convert(ViewHolder holder, DATA item, int position);


    @Override
    public int getItemCount() {
        return mData.size();
    }

    //只能利用接口回调设置点击事件
    private ItemClickListener mItemClickListener;
    private ItemLongClickListener mItemLongClickListener;

    public void setOnItemClickListener(ItemClickListener listener) {
        this.mItemClickListener = listener;
    }
    public void setOnItemLongClickListener(ItemLongClickListener listener) {
        this.mItemLongClickListener = listener;
    }
}
