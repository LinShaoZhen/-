package com.example.baselibrary.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by nana on 2018/8/29.
 */

public abstract class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationParams> implements INavigationBar {
    private P mParams;
    private View mNavigationView;

    public AbsNavigationBar(P params) {
        this.mParams = params;
        createAndBindView();

    }

    public P getParams() {
        return mParams;
    }

    protected void setText(int viewId, String text) {
        TextView tv = findViewById(viewId);
        if (!TextUtils.isEmpty(text)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }

    public <T extends View> T findViewById(int viewId) {
        return (T) mNavigationView.findViewById(viewId);
    }

    protected void setOnClickListener(int viewId, View.OnClickListener listener) {
        findViewById(viewId).setOnClickListener(listener);

    }

    protected void setRightRes(int viewId, int mRightRes) {
        ImageView iv = findViewById(viewId);
        if (mRightRes != 0) {
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(mRightRes);
        }
    }

    /**
     * 绑定和创建View
     */
    private void createAndBindView() {
        //1.创建View

        if (mParams.mParent == null) {
            //获取Activity的根布局，因为继承自application，所以需要这样获取
            ViewGroup activityRoot = (ViewGroup) ((Activity) mParams.mContext).getWindow().getDecorView();
            mParams.mParent = (ViewGroup) activityRoot.getChildAt(0);
        }
        //处理继承自Activity的情况，后面补充
        if (mParams.mParent == null) {
            return;
        }

        mNavigationView = LayoutInflater.from(mParams.mContext).inflate(bindLayoutId(), mParams.mParent, false);
        //添加
        mParams.mParent.addView(mNavigationView, 0);
        applyView();
    }


    //使用Builder设计模式
    public abstract static class Builder {


        public Builder(Context context, ViewGroup parent) {


        }

        public abstract AbsNavigationBar budiler();


        public static class AbsNavigationParams {
            public Context mContext;
            public ViewGroup mParent;


            public AbsNavigationParams(Context context, ViewGroup parent) {
                this.mContext = context;
                this.mParent = parent;

            }

        }
    }

}
