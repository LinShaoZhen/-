package com.example.framelibrary;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.baselibrary.navigationbar.AbsNavigationBar;

/**
 * Created by nana on 2018/8/29.
 */

public class DefaultNavigationBar extends AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationParams> {
    public DefaultNavigationBar(Builder.DefaultNavigationParams params) {
        super(params);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.title_bar;
    }

    @Override
    public void applyView() {
        //绑定效果
        setText(R.id.title, getParams().mTtitle);
        setText(R.id.right_title, getParams().mRightText);

        setOnClickListener(R.id.right_icon, getParams().mRightClickListener);
        //左边默认的点击事件
        setOnClickListener(R.id.back,getParams().mLeftClickListener);
        setRightRes(R.id.right_icon, getParams().mRightRes);

    }


    public static class Builder extends AbsNavigationBar.Builder {
        DefaultNavigationParams P;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new DefaultNavigationParams(context, parent);
        }

        public Builder(Context context) {
            super(context, null);
            P = new DefaultNavigationParams(context, null);
        }

        @Override
        public DefaultNavigationBar budiler() {
            DefaultNavigationBar navigationBar = new DefaultNavigationBar(P);
            return navigationBar;
        }

        //设置所有的效果
        public DefaultNavigationBar.Builder setTitle(String title) {
            P.mTtitle = title;
            return this;
        }

        public DefaultNavigationBar.Builder setRightText(String rightText) {
            P.mRightText = rightText;
            return this;
        }

        /**
         * 设置右边的点击事件
         *
         * @param onClickListener
         * @return
         */
        public DefaultNavigationBar.Builder setRightClickListener(View.OnClickListener onClickListener) {
            P.mRightClickListener = onClickListener;
            return this;
        }

        /**
         * 设置左边的点击事件
         *
         * @param onClickListener
         * @return
         */
        public DefaultNavigationBar.Builder setLeftClickListener(View.OnClickListener onClickListener) {
            P.mLeftClickListener = onClickListener;
            return this;
        }


        /**
         * 设置右边图片
         *
         * @param rightRes
         * @return
         */
        public DefaultNavigationBar.Builder setRightIcon(int rightRes) {
            P.mRightRes = rightRes;
            return this;
        }

        public static class DefaultNavigationParams extends AbsNavigationBar.Builder.AbsNavigationParams {
            //放置所有的效果
            public String mTtitle;//中间的标题
            public String mRightText;//右边的文字
            public int mRightRes = 0;
            public View.OnClickListener mRightClickListener;//右边的点击事件
            public View.OnClickListener mLeftClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //关闭当前Activity
                    ((Activity) mContext).finish();
                }
            };//左边的点击事件


            DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }
    }
}
