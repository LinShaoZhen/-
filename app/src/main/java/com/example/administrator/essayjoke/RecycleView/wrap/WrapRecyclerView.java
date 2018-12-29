package com.example.administrator.essayjoke.RecycleView.wrap;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by nana on 2018/9/20.
 */

public class WrapRecyclerView extends RecyclerView {
    private WrapRecyclerAdapter mAdapter;
    private AdapterDataObserver mAdapterObserver=new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            mAdapter.notifyDataSetChanged();
        }
        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
           mAdapter.notifyItemRangeRemoved(positionStart,itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
           mAdapter.notifyItemMoved(fromPosition,toPosition);
        }
    };

    public WrapRecyclerView(Context context) {
        this(context, null);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WrapRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter instanceof WrapRecyclerAdapter) {
            mAdapter= (WrapRecyclerAdapter) adapter;
        }else {
            mAdapter=new WrapRecyclerAdapter(adapter);
            adapter.registerAdapterDataObserver(mAdapterObserver);
        }
        super.setAdapter(mAdapter);
    }

    //添加头部的方法
    public void addHeaderView(View view) {
        if (mAdapter!=null){
            mAdapter.addHeaderView(view);
        }
    }

    //添加底部的方法
    public void addFooterView(View view) {
        if (mAdapter!=null){
            mAdapter.addFooterView(view);
        }
    }

    //移除头部的方法
    public void removeHeaderView(View view) {
        if (mAdapter!=null){
            mAdapter.removeHeaderView(view);
        }
    }

    //移除底部的方法
    public void removeFooterView(View view) {
        if (mAdapter!=null){
            mAdapter.removeFooterView(view);
        }
    }
}
