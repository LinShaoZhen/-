package com.example.administrator.essayjoke.banner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by nana on 2018/9/10.
 */

public class BannerScroller extends Scroller {
    //动画持续的时间
    private int mScrollerDuration = 850;

    /**
     * 设置切换动画持续的时间
     * @param srocllerDuration
     */
    public void setDuration(int srocllerDuration) {
        this.mScrollerDuration = srocllerDuration;
    }

    public BannerScroller(Context context) {
        super(context);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollerDuration);
    }
}
