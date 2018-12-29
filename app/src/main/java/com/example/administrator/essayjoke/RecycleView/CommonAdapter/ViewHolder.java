package com.example.administrator.essayjoke.RecycleView.CommonAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by nana on 2018/9/19.
 * RecyclerView的通用ViewHolder
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    //用于缓存已缓存的界面
    private SparseArray<View> mViews;

    public ViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    /**
     * 优化ViewHolder，减少订单ViewById的次数
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        //多次掉用 对已有的View做缓存
        View view = mViews.get(viewId);
        //使用缓存减少fandviewbyid的次数
        if (view == null) {
            view = itemView.findViewById(viewId);
        }
        return (T) view;
    }

    /**
     * 设置View的Visibility
     */
    public ViewHolder setViewVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    //通用功能的封装 设置文本 设置条目点击事件 设置图片

    //设置文本
    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        //能够链式调用
        return this;
    }

    //设置图片
    public ViewHolder setImageResource(int viewId, int resourceId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resourceId);
        //能够链式调用
        return this;
    }

    /**
     * 图片处理问题，不能直接使用某个第三方的，不方便别人使用，定义一套自己的规范
     */
    public ViewHolder setImagePath(int viewId, HolderImageLoader imageLoader) {
        ImageView iv = getView(viewId);
        imageLoader.loadImage(iv, imageLoader.getPath());
        return this;
    }

    /**
     * 图片的加载
     */
    public abstract static class HolderImageLoader {
        private Object mPath;

        public HolderImageLoader(Object path) {
            this.mPath = path;
        }

        /**
         * 需要去复写写个方法加载图片
         *
         * @param view
         * @param path
         */
        public abstract void loadImage(ImageView view, Object path);

        public Object getPath() {
            return mPath;
        }
    }


}
