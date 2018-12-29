package com.example.administrator.essayjoke.RecycleView.BaseUse;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.essayjoke.R;
import com.example.administrator.essayjoke.RecycleView.CommonAdapter.ImageLoader;
import com.example.administrator.essayjoke.RecycleView.CommonAdapter.RecyclerCommonAdapter;
import com.example.administrator.essayjoke.RecycleView.CommonAdapter.ViewHolder;

import java.util.List;

/**
 * Created by nana on 2018/9/17.
 */

public class RecyclerAdapter extends RecyclerCommonAdapter<String> {

    public RecyclerAdapter(Context context, List list) {
        super(context, list, R.layout.item_home);
    }

    /**
     * @param holder   holder
     * @param item   当前位置的条目
     * @param position  当前位置
     */
    @Override
    protected void convert(ViewHolder holder, String item, int position) {
     holder.setText(R.id.id_num,item);
     holder.setImagePath(R.id.id_iv,new ImageLoader(R.drawable.welcome));
    }



}
