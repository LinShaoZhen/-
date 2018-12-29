package com.example.administrator.essayjoke.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.administrator.essayjoke.R;

/**
 * Created by nana on 2018/9/12.
 * Viewpage的指示器
 */

public class TrackIndicatorView extends HorizontalScrollView implements ViewPager.OnPageChangeListener {
    private IndicatorAdaper mAdapter;
    //指示器条目的容器（ScrollView只能有一个孩子）
    private IndicatorGroupView mIndicatorGroup;
    //一屏幕可以显示多少个
    private int mTabVisibleNums = 0;
    //item的宽度（如果不设置一屏幕有多少个，就会触发这个宽度）
    private int mItemWidth;
    //当前指示器徐亚居中
    private ViewPager mViewPager;
    //当前位置
    private int mCurrentPosition = 0;
    //判断是否执行Scroll
    private boolean mIsExecuteScroll = false;
    private boolean mSmoothScroll;


    public TrackIndicatorView(Context context) {
        this(context, null);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //创建一个LinearLayout,将它放在ScrollView里面
        mIndicatorGroup = new IndicatorGroupView(context);
        addView(mIndicatorGroup);
        initAttribute(context, attrs);
    }

    /**
     * 初始化自定义属性
     *
     * @param context
     * @param attrs
     */
    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TrackIndicatorView);
        mTabVisibleNums = array.getInteger(R.styleable.TrackIndicatorView_tabVisibleNum, mTabVisibleNums);
        array.recycle();
    }


    /**
     * 设置适配器
     *
     * @param adapter
     */
    public void setAdapter(IndicatorAdaper adapter) {
        if (adapter == null) {
            throw new NullPointerException("adapter is null!");
        }
        this.mAdapter = adapter;
        //动态添加View 首先是获取都多少条数据
        int itemCount = mAdapter.getCount();
        //循环添加ItemView
        for (int i = 0; i < itemCount; i++) {
            View itemView = mAdapter.getView(i, mIndicatorGroup);
            //将所有的孩子放在LinearLayout里面，这样ScrollView就只有一个孩子了
            mIndicatorGroup.addItemView(itemView);
            if (mViewPager != null) {
                //设置每个孩子条目的点击事件
                switchItemClick(itemView, i);
            }
        }
        //设置默认第一个高亮显示
        mAdapter.highLightIndicator(mIndicatorGroup.getItemtAt(0));

    }

    /**
     * 每个孩子条目的点击事件
     *
     * @param itemView
     * @param position
     */
    private void switchItemClick(View itemView, final int position) {
        itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(position,mSmoothScroll);
                //移动IndicatorView
                smoothScrollIndicator(position);
                //滚动底部指示器
                mIndicatorGroup.scrollBottomTrack( position);
            }
        });
    }


    //点击移动IndicatorView，带动画
    private void smoothScrollIndicator(int position) {
        //当前总共的位置
        float totalScroll=(position)*mItemWidth;
        //右边的偏移量
        int offsetScroll=(getWidth()-mItemWidth)/2;
        //最终的偏移量
        final int finalScroll= (int) (totalScroll-offsetScroll);
        //调用ScrollView自带的方法，而且带动画
        smoothScrollTo(finalScroll,0);
    }

    public void setAdapter(IndicatorAdaper adapter, ViewPager viewPager) {
    setAdapter(adapter,viewPager,true);
    }

    public void setAdapter(IndicatorAdaper adapter, ViewPager viewPager,boolean smoothScroll) {
        this.mSmoothScroll=smoothScroll;
        if (adapter == null) {
            throw new NullPointerException("ViewPager is null!");
        }
        mViewPager = viewPager;
        //设置滑动监听（使条目保持在中间）
        mViewPager.addOnPageChangeListener(this);
        setAdapter(adapter);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //这里只能使用onLayout方法，不然会影响后面的指示器
        if (changed && mItemWidth == 0) {
            // 指定Item的宽度
            mItemWidth = getItemWidth();
            int itemCounts = mAdapter.getCount();
            for (int i = 0; i < itemCounts; i++) {
                // 指定每个Item的宽度
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mIndicatorGroup.getItemtAt(i).getLayoutParams();
                params.width = mItemWidth;
                mIndicatorGroup.getItemtAt(i).setLayoutParams(params);
            }
            mIndicatorGroup.addButtomTrackView(mAdapter.getBottomTackView(), mItemWidth);
            Log.e("lsz", "mItemWidth -> " + mItemWidth);
        }
    }

    /**
     * 获取每一个条目的宽度
     */
    public int getItemWidth() {
        int itemWidth = 0;
        // 获取当前控件的宽度
        int width = getWidth();
        if (mTabVisibleNums != 0) {
            // 在布局文件中指定一屏幕显示多少个
            itemWidth = width / mTabVisibleNums;
            return itemWidth;
        }
        // 如果没有指定获取最宽的一个作为ItemWidth
        int maxItemWidth = 0;
        int mItemCounts = mAdapter.getCount();
        // 总的宽度
        int allWidth = 0;

        for (int i = 0; i < mItemCounts; i++) {
            View itemView = mIndicatorGroup.getItemtAt(i);
            int childWidth = itemView.getMeasuredWidth();
            maxItemWidth = Math.max(maxItemWidth, childWidth);
            allWidth += childWidth;
        }
        itemWidth = maxItemWidth;

        // 如果不足一个屏那么宽度就为  width/mItemCounts
        if (allWidth < width) {
            itemWidth = width / mItemCounts;
        }
        return itemWidth;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //滚动时会不断的调用
        if (mIsExecuteScroll) {
            scrollCurrentIndicator(position, positionOffset);
            //滚动时指示器跟着一起移动
            mIndicatorGroup.scrollButtomTrack(position, positionOffset);
            //如果是点击就不要执行onPageScrolled这个方法
        }

    }

    /**
     * 不断的滚动当前的指示器
     *
     * @param position
     * @param positionOffset
     */
    private void scrollCurrentIndicator(int position, float positionOffset) {
        //当前总共的位置
        float totalSocroll = (position + positionOffset) * mItemWidth;
        //左边的偏移
        int offsetScroll = (getWidth() - mItemWidth) / 2;
        //最终的偏移量
        final int fanalScroll = (int) (totalSocroll - offsetScroll);
        //d调用Scroll自带的方法来实现移动
        scrollTo(fanalScroll, 0);
    }

    @Override
    public void onPageSelected(int position) {
        //将上一个位置重置，当前位置高亮显示
        mAdapter.restoreIndicator(mIndicatorGroup.getItemtAt(mCurrentPosition));
        mCurrentPosition = position;
        mAdapter.highLightIndicator(mIndicatorGroup.getItemtAt(mCurrentPosition));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 1) {
            mIsExecuteScroll = true;
        }
        if (state == 0) {
            mIsExecuteScroll = false;
        }

    }
}
