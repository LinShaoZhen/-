package com.example.administrator.essayjoke.RecycleView.BaseUse;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by nana on 2018/9/17.
 * ListView样式的分割线
 */

public class LinearLayoutItemDecoation extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    public LinearLayoutItemDecoation(Context context, int drawableResId) {
        mDivider = ContextCompat.getDrawable(context, drawableResId);
    }

    /**
     * 留出分割线位置
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //由于parent.getChildCount()是不断变化的，所以不能再每个item的底部添加，只能在头部添加
        int position = parent.getChildAdapterPosition(view);
        if (position != 0) {
            outRect.top = mDivider.getIntrinsicHeight();
        }
    }

    /**
     * 绘制分割线
     *
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        //在除了第一个item的头部绘制
        int childCount = parent.getChildCount();
        //指定绘制区域
        Rect rect = new Rect();
        rect.left = parent.getPaddingLeft();
        rect.right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 1; i < childCount; i++) {
            rect.bottom = parent.getChildAt(i).getTop();
            rect.top = rect.bottom - mDivider.getIntrinsicHeight();
            mDivider.setBounds(rect);
            mDivider.draw(canvas);
        }
    }
}
