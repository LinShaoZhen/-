package com.example.administrator.essayjoke.RecycleView.BaseUse;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by nana on 2018/9/17.
 * ListView样式的分割线
 */

public class GridLayoutItemDecoation extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    public GridLayoutItemDecoation(Context context, int drawableResId) {
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
        //在item的底部和右边留出分割线位置
        outRect.bottom = mDivider.getIntrinsicHeight();

        outRect.right = mDivider.getIntrinsicWidth();

        int bottom = mDivider.getIntrinsicHeight();
        int right = mDivider.getIntrinsicWidth();
        //如果是最右边不要留，如果是最下边也不要留
        if (isLastCloumn(view, parent)) {//最后一列
            bottom = 0;

        }
        if (isLastRow(view, parent)) {//最后一行
            right = 0;
        }
        outRect.bottom = bottom;
        outRect.right = right;
    }

    private boolean isLastRow(View view, RecyclerView parent) {
        //获取当前位置
        int currentPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        //获取列数
        int spanCount = getSpanCount(parent);
        return (currentPosition + 1) % spanCount == 0;
    }

    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int spanCount = gridLayoutManager.getSpanCount();
            return spanCount;
        }
        return 1;
    }

    private boolean isLastCloumn(View view, RecyclerView parent) {
        //当前位置>(行数-1)*列数
        //列数
        int spanCount = getSpanCount(parent);
        //行数=总的条目/列数
        int rowNumber = parent.getAdapter().getItemCount() % spanCount == 0 ?
                parent.getAdapter().getItemCount() / spanCount :
                parent.getAdapter().getItemCount() / spanCount + 1;
        //获取当前位置
        int currentPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        return (currentPosition+1) > (rowNumber - 1) * spanCount;
    }

    /**
     * 绘制分割线
     *
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        //绘制水平方向分割线
        drawHorizontal(canvas, parent);
        //绘制垂直方向分割线
        drawVertical(canvas, parent);

    }

    /**
     * 绘制垂直方向的分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int top = childView.getTop() - params.topMargin;
            int bottom = childView.getBottom() + params.bottomMargin;
            int left = childView.getRight() + params.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            //计算水平分割线的位置
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
    }

    /**
     * 绘制水平方向分割线
     *
     * @param canvas
     * @param parent
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getLeft() - params.leftMargin;
            int right = childView.getRight() + mDivider.getIntrinsicWidth() + params.rightMargin;
            int top = childView.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            //计算水平分割线的位置
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(canvas);
        }
    }
}
