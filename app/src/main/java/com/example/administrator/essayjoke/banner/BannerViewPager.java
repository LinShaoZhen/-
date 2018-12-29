package com.example.administrator.essayjoke.banner;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by nana on 2018/9/10.
 */

public class BannerViewPager extends ViewPager {
    private BannerAdapter mAdapter;
    //发送消息的msg What
    private final int SCROLL_MSG = 0X0011;
    //页面切换间隔时间
    private int mCutDownTime = 3500;
    //自定义的页面切换的Scroller
    private BannerScroller mScrolle;
    //复用的View
    private List<View> mConverView;
    private Activity mActivity;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //每隔xs后切换到下个页面
            setCurrentItem(getCurrentItem() + 1);
            //不断循环
            startRoll();
        }
    };

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActivity = (Activity) context;
        //改变ViewPager切换的速率
        //1.改变duration （持续时间） 2.通过mScroller设置 private 通过反射设置
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            //设置参数
            field.setAccessible(true);
            //第一个参数是设置在哪个类
            mScrolle = new BannerScroller(context);
            field.set(this, mScrolle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mConverView = new ArrayList<>();
    }

    public void setScrollerDuration(int scrollerDuration) {
        mScrolle.setDuration(scrollerDuration);
    }

    /**
     * 设置自定义的Adapter
     *
     * @param adapter
     */
    public void setAdapter(BannerAdapter adapter) {
        this.mAdapter = adapter;
        //设置父类ViewPager的Adapter
        setAdapter(new BannerPagerAdapter());
        //管理Activity生命周期--绑定
        mActivity.getApplication().registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }


    /**
     * 实现自动轮播
     */
    public void startRoll() {
        //清除消息
        mHandler.removeMessages(SCROLL_MSG);
        mHandler.sendEmptyMessageDelayed(SCROLL_MSG, mCutDownTime);
    }

    /**
     * 销毁handler停止发送   解决内存泄漏
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(SCROLL_MSG);
        mHandler = null;
        //取消注册
        mActivity.getApplication().unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }

    /**
     * 给ViewPager设置适配器
     */
    public class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;//设置循环的个数为int最大值，实现无限轮播
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        /**
         * 创建ViewPager条目回调的方法
         *
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View bannerItemView = mAdapter.getView(position % mAdapter.getCount(), getConvertView());
            //采用Adapter设计模式为了完全让用户自定义
            container.addView(bannerItemView);
            return bannerItemView;
        }

        /**
         * 销毁条目会回调的方法
         *
         * @param container
         * @param position
         * @param object
         */

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            mConverView.add((View) object);
        }
    }

    /**
     * 获取复用界面
     *
     * @return
     */
    private View getConvertView() {
        for (int i = 0; i < mConverView.size(); i++) {
            if (mConverView.get(i).getParent() == null) {
                return mConverView.get(i);
            }
        }
        return null;
    }

    //管理Activity的生命周期（界面看不见时轮播不滚动）
    private Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks = new DefaultActivityLifecycleabcks() {

        @Override
        public void onActivityResumed(Activity activity) {
            //判断是不是监听的当前Activity的生命周期
            if (activity == mActivity) {
                //开启轮播
                mHandler.sendEmptyMessageDelayed(mCutDownTime, SCROLL_MSG);
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            if (activity == mActivity) {
                //停止轮播
                mHandler.removeMessages(SCROLL_MSG);
            }
        }

    };
}
