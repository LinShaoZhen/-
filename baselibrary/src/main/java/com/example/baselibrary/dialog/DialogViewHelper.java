package com.example.baselibrary.dialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.baselibrary.R;

import java.lang.ref.WeakReference;

/**
 * Created by nana on 2018/8/28.
 * Dialog View的辅助处理类
 */

class DialogViewHelper {
    private View mContentView = null;
    private SparseArray<WeakReference<View>> mViews;


    public DialogViewHelper(Context context, int layoutResId) {
        this();
        mContentView= LayoutInflater.from(context).inflate(layoutResId,null);
    }

    public DialogViewHelper() {
        mViews=new SparseArray<>();
    }

    /**
     * 设置布局View
     * @param mView
     */
    public void setContentView(View mView) {
        this.mContentView=mContentView;
    }

    /**
     * 设置文本
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        //减少findViewById的次数
        TextView tv=getView(viewId);
        if (tv!=null){
            tv.setText(text);
        }
    }

    public  <T extends View>T getView(int viewId) {
        WeakReference<View> viewWeakReference=mViews.get(viewId);
        View view=null;
        if (viewWeakReference!=null){
            view=viewWeakReference.get();
        }
        if (view==null){
            view=mContentView.findViewById(viewId);
            if (view!=null){
                mViews.put(viewId,new WeakReference<>(view));
            }
        }
        return (T) view;
    }

    /**
     * 设置点击事件
     * @param viewId
     * @param listener
     */
    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view=getView(viewId);
        if (view!=null){
            view.setOnClickListener(listener);
        }
    }

    /**
     * 获取Content的View
     * @return
     */
    public View getContentView() {
        return mContentView;
    }
}
