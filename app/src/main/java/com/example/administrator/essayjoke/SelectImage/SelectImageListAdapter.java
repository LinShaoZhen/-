package com.example.administrator.essayjoke.SelectImage;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.essayjoke.R;
import com.example.administrator.essayjoke.RecycleView.CommonAdapter.ItemClickListener;
import com.example.administrator.essayjoke.RecycleView.CommonAdapter.RecyclerCommonAdapter;
import com.example.administrator.essayjoke.RecycleView.CommonAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nana on 2018/9/25.
 */

public class SelectImageListAdapter extends RecyclerCommonAdapter<String> {
    //选择图片的集合
    private ArrayList<String> mResultImageList;
    private List<String> mList;
    private int mMaxCount;


    public SelectImageListAdapter(Context context, List<String> list, ArrayList<String> imageList,int maxCount) {
        super(context, list, R.layout.media_chooser_item);
        this.mResultImageList = imageList;
        mList=list;
        mMaxCount=maxCount;
    }

    @Override
    protected void convert(final ViewHolder holder, final String item, int position) {
        if (TextUtils.isEmpty(item)) {
            //显示拍照
            holder.setViewVisibility(R.id.cramera_ll, View.VISIBLE);
            holder.setViewVisibility(R.id.media_selected_indicator, View.INVISIBLE);
            holder.setViewVisibility(R.id.image, View.INVISIBLE);

            setOnItemClickListener(new ItemClickListener() {
                @Override
                public void setOnClickListener(int position) {
                    //调用拍照
                }
            });
        } else {
            //显示图片
            holder.setViewVisibility(R.id.cramera_ll, View.INVISIBLE);
            holder.setViewVisibility(R.id.media_selected_indicator, View.VISIBLE);
            holder.setViewVisibility(R.id.image, View.VISIBLE);
            //显示图片利用Glide
            ImageView imageView = holder.getView(R.id.image);
            Glide.with(mContext).load(item).into(imageView);

            ImageView imageView1=holder.getView(R.id.media_selected_indicator);
            if (mResultImageList.contains(mList.get(position))){
                Glide.with(mContext).load(R.drawable.icon).into(imageView1);
            }else {
                Glide.with(mContext).load(R.drawable.welcome).into(imageView1);
            }

            setOnItemClickListener(new ItemClickListener() {
                @Override
                public void setOnClickListener(int position) {

                    //没有就加入集合
                    if (!mResultImageList.contains(mList.get(position))) {
                        //不能大于最大张数
                        if (mResultImageList.size()>=mMaxCount){
                            Toast.makeText(mContext, "最多只能选取"+mMaxCount+"张图片", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mResultImageList.add(mList.get(position));
                    } else {
                        mResultImageList.remove(mList.get(position));

                    }
                    notifyDataSetChanged();
                    //通知显示布局
                    if (mListener!=null){
                        mListener.select();
                    }
                }
            });
        }
    }
    //设置选择图片监听
    private SelectImageListener mListener;

    public void setOnSelectImageListener(SelectImageListener listener){
        this.mListener=listener;
    }
}
