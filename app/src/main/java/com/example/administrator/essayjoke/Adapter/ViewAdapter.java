package com.example.administrator.essayjoke.Adapter;

import android.content.Context;

import com.example.administrator.essayjoke.R;
import com.example.administrator.essayjoke.RecycleView.CommonAdapter.RecyclerCommonAdapter;
import com.example.administrator.essayjoke.RecycleView.CommonAdapter.ViewHolder;

import java.util.List;

/**
 * @author lsz
 * @time 2018/10/4 10:05
 * 作用：
 */
public class ViewAdapter extends RecyclerCommonAdapter<String> {
    private Context mContext;
    private List<String> mList;
    public ViewAdapter(Context context, List list) {
        super(context, list, R.layout.item_view);
        this.mContext=context;
        this.mList=list;

    }

    @Override
    protected void convert(ViewHolder holder, String item, int position) {
        holder.setText(R.id.view_item_text,item);

    }
}
