package com.example.baselibrary.ioc;

import android.app.Activity;
import android.view.View;

/**
 * Created by Administrator on 2018/08/08.
 * View的FandViewById的辅助类
 */

public class ViewFinder {
    private Activity mActivity;
    private View mView;

    public ViewFinder(Activity activity) {
        this.mActivity = activity;
    }

    public ViewFinder(View view) {
        this.mView = view;
    }

    public View findVeiwById(int viewId) {
        return mActivity != null ? mActivity.findViewById(viewId) : mView.findViewById(viewId);
    }
}
