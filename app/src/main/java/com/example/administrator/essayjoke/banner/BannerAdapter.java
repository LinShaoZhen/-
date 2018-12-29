package com.example.administrator.essayjoke.banner;

import android.view.View;

/**
 * Created by nana on 2018/9/10.
 */

public abstract class BannerAdapter {
    //根据位置获取V以ewPager里面的子view
    public abstract View getView(int position,View convertView);

    /**
     * 获取轮播的数量
     * @return
     */
    public abstract int getCount();

    /**
     * 根据位置获取广告位描述
     * @param mCurrentPosition
     * @return
     */
    public String getBannerDesc(int mCurrentPosition) {
        return "";
    }
}
