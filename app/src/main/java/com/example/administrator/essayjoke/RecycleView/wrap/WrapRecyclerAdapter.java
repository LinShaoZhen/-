package com.example.administrator.essayjoke.RecycleView.wrap;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by nana on 2018/9/20.
 * 头部和底部包裹的Adapter
 */

public class WrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //数据列表的Adapter 不包含头部
    private RecyclerView.Adapter mAdapter;
    //头部和底部的集合，必须用Map集合进行标识
    private SparseArray<View> mHeaders, mFooters;
    private static int BASE_HEADER_KEY = 1000000;
    private static int BASE_FOOTER_KEY = 2000000;


    public WrapRecyclerAdapter(RecyclerView.Adapter adapter) {
        this.mAdapter = adapter;
        mHeaders = new SparseArray<>();
        mFooters = new SparseArray<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //区分头部和底部还是列表内容只能通过viewType
        //viewType可能是头部、底部、列表item
        if (mHeaders.indexOfKey(viewType) >= 0) {//头部
            return createFooterHeaderViewHolder(mHeaders.get(viewType));
        } else if (mFooters.indexOfKey(viewType) >= 0) {//底部
            return createFooterHeaderViewHolder(mFooters.get(viewType));
        }
        //列表
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    /**
     * 创建头部和底部的ViewHolder
     * @param view
     * @return
     */
    private RecyclerView.ViewHolder createFooterHeaderViewHolder(View view) {
        return new RecyclerView.ViewHolder(view){

        };
    }

    @Override
    public int getItemViewType(int position) {
        //会先调用这个方法设置viewType，所以可以根据位置去设置viewType
        //头部的item数量
        int numHeaders = mHeaders.size();
        //如果position小于头部的item数量，就是属于头部
        if (position < numHeaders) {
            return mHeaders.keyAt(position);
        }
        //去除头部item之后的数量
        final int adjPosition = position - numHeaders;
        //Adapter的Item数量
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }
        //底部的数量
        int footerPosition = adjPosition - adapterCount;

        return mFooters.keyAt(footerPosition);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //头部和底部都不需要绑定数据
        //头部的item数量
        int numHeaders = mHeaders.size();
        if (position<numHeaders){
            return;
        }
        //去除头部item之后的数量
        final int adjPosition = position - numHeaders;
        //Adapter的Item数量
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
               mAdapter.onBindViewHolder(holder,adjPosition);
            }
        }
        //底部的数量
        int footerPosition = adjPosition - adapterCount;
    }

    @Override
    public int getItemCount() {
        Log.e("lsz", mHeaders.size() +"-------"+ mFooters.size());
        return mAdapter.getItemCount() + mHeaders.size() + mFooters.size();
    }

    //添加头部的方法
    public void addHeaderView(View view) {
        if (mHeaders.indexOfValue(view) == -1) {
            //集合里面没有才添加，不要重复添加
            mHeaders.put(BASE_HEADER_KEY++, view);
            //更新列表
            notifyDataSetChanged();
        }
    }

    //添加底部的方法
    public void addFooterView(View view) {
        if (mFooters.indexOfValue(view) == -1) {
            //集合里面没有才添加，不要重复添加
            mFooters.put(BASE_FOOTER_KEY++, view);
            //更新列表
            notifyDataSetChanged();
        }
    }

    //移除头部的方法
    public void removeHeaderView(View view) {
        if (mHeaders.indexOfValue(view) >= 0) {
            mHeaders.removeAt(mHeaders.indexOfValue(view));
            //更新列表
            notifyDataSetChanged();
        }
    }

    //移除底部的方法
    public void removeFooterView(View view) {
        if (mFooters.indexOfValue(view) >= 0) {
            mFooters.removeAt(mFooters.indexOfValue(view));
            //更新列表
            notifyDataSetChanged();
        }
    }
}
