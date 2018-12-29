package com.example.administrator.essayjoke.indicator;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by nana on 2018/9/14.
 * Indicator容器 包含itemView和底部跟踪的指示器
 */

public class IndicatorGroupView extends FrameLayout {
    //指示器条目的容器（ScrollView只能有一个孩子）
    private LinearLayout mIndicatorGroup;
    //底部栏指示器
    private View mButtomTrackView;
    //一个条目的宽度
    private int mItemWidth;
    private FrameLayout.LayoutParams mTrackParams;
    private int mInitLeftMargin;


    public IndicatorGroupView(@NonNull Context context) {
        this(context, null);
    }

    public IndicatorGroupView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorGroupView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //创建一个LinearLayout,将它放在ScrollView里面
        mIndicatorGroup = new LinearLayout(context);
        addView(mIndicatorGroup);
    }

    /**
     * 添加ItemView
     *
     * @param itemView
     */
    public void addItemView(View itemView) {
        mIndicatorGroup.addView(itemView);
    }

    /**
     * 获取itemView
     *
     * @param position
     * @return
     */
    public View getItemtAt(int position) {
        return mIndicatorGroup.getChildAt(position);
    }

    /**
     * 添加底部跟踪的指示器
     *
     * @param bottomTackView
     */
    public void addButtomTrackView(View bottomTackView, int itemWidth) {
        if (bottomTackView == null) {
            return;
        }
        this.mItemWidth = itemWidth;
        this.mButtomTrackView = bottomTackView;
        addView(mButtomTrackView);
        //让指示器在底部
        mTrackParams = (LayoutParams) mButtomTrackView.getLayoutParams();
        mTrackParams.gravity = Gravity.BOTTOM;
        int trackWidth = mTrackParams.width;
        //没有设置宽度
        if (mTrackParams.width == LayoutParams.MATCH_PARENT) {
            trackWidth = mItemWidth;
        }
        //设置的宽度过大
        if (trackWidth > mItemWidth) {
            trackWidth = mItemWidth;
        }
        //最后确定宽度
        mTrackParams.width = trackWidth;
        mInitLeftMargin = (mItemWidth - trackWidth) / 2;
        mTrackParams.leftMargin = mInitLeftMargin;
    }

    /**
     * 滚动底部的指示器
     *
     * @param position
     * @param positionOffset
     */
    public void scrollButtomTrack(int position, float positionOffset) {
        if (mButtomTrackView == null) {
            return;
        }
        int leftMargin = (int) ((position + positionOffset) * mItemWidth) + mInitLeftMargin - 3;
        mTrackParams.leftMargin = leftMargin;
        mButtomTrackView.setLayoutParams(mTrackParams);
    }

    /**
     * 滚动底部指示器
     *
     * @param position
     */
    public void scrollBottomTrack(int position) {
        if (mButtomTrackView == null) {
            return;
        }
        //最终需要移动的位置
        int finalLeftMargin = (int) (position * mItemWidth) + mInitLeftMargin;
        //当前位置
        int currentLeftMargin = mTrackParams.leftMargin;
        //移动的距离
        int distance = finalLeftMargin - currentLeftMargin;
        //带动画
        ValueAnimator animator = ObjectAnimator.ofFloat(currentLeftMargin, finalLeftMargin).setDuration((long) (Math.abs(distance) * 0.4f));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //会不断的回调这个方法
                float currentLeftMargin = (float) animation.getAnimatedValue();
                mTrackParams.leftMargin = (int) currentLeftMargin;
                mButtomTrackView.setLayoutParams(mTrackParams);

            }
        });
        //设置动画的插值器
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

}
