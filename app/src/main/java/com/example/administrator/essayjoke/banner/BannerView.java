package com.example.administrator.essayjoke.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.util.Measure;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.essayjoke.R;

/**
 * Created by nana on 2018/9/10.
 */

public class BannerView extends RelativeLayout {
    private BannerViewPager mBannerVp;
    private TextView mBannerDescTv;
    private LinearLayout mDotContainerView;
    private BannerAdapter mAdapter;
    private Context mContext;
    private Drawable mIndictorFocusDrawable;//点选中的Drawable
    private Drawable mIndictorNormalDrawable;//点默认的Drawable
    private int mCurrentPosition = 0;//当前位置
    //点的显示位置
    private int mDotGravity = 1;
    //点的大小
    private int mDotSzie = 8;
    //点的间距
    private int mDotDistance = 8;
    //底部容器
    private View mBannerBv;
    //底部容器颜色，默认透明
    private int mBottomColor = Color.TRANSPARENT;
    //宽度比例
    private float mWidthProportion;
    //高度比例
    private float mHeightProportion;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        //吧布局加载到这个view里面
        inflate(context, R.layout.ui_banner_layout, this);
        initView();
        initAttribute(attrs);
    }


    /**
     * 初始化自定义属性
     *
     * @param attrs
     */
    private void initAttribute(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.BannerView);
        //获取自定义属性
        mDotGravity = array.getInt(R.styleable.BannerView_dotGravity, mDotGravity);
        //获取点的颜色
        mIndictorFocusDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorFocus);
        if (mIndictorFocusDrawable == null) {
            //如果在布局文件中没有配置点的颜色有一个默认值
            mIndictorFocusDrawable = new ColorDrawable(Color.RED);
        }
        mIndictorNormalDrawable = array.getDrawable(R.styleable.BannerView_dotIndicatorNormal);
        if (mIndictorNormalDrawable == null) {
            //如果在布局文件中没有配置点的颜色有一个默认值
            mIndictorNormalDrawable = new ColorDrawable(Color.WHITE);
        }
        //获取点的大小和距离
        mDotSzie = (int) array.getDimension(R.styleable.BannerView_dotSize, dip2px(mDotSzie));
        mDotDistance = (int) array.getDimension(R.styleable.BannerView_dotDistance, dip2px(mDotDistance));
        //获取底部容器的颜色
        mBottomColor = array.getColor(R.styleable.BannerView_bottomColor, mBottomColor);
        //获取宽高比例
        mWidthProportion = array.getFloat(R.styleable.BannerView_withProportion, mWidthProportion);
        mHeightProportion = array.getFloat(R.styleable.BannerView_heightProportion, mHeightProportion);
        array.recycle();
    }

    /**
     * 初始化View
     */
    private void initView() {
        mBannerVp = (BannerViewPager) findViewById(R.id.banner_vp);
        mBannerDescTv = findViewById(R.id.banner_desc_tv);
        mDotContainerView = findViewById(R.id.dot_container);
        mBannerBv = findViewById(R.id.banner_bottom_view);
        mBannerBv.setBackgroundColor(mBottomColor);
    }


    /**
     * 设置适配器
     *
     * @param adapter
     */
    public void setAdapter(BannerAdapter adapter) {
        mAdapter = adapter;
        mBannerVp.setAdapter(adapter);
        initDotIndicator();
        //bug修复
        mBannerVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //监听当前选中的位置
                pagerSelect(position);
            }
        });
        //初始化的时候获取第一条描述
        String fristDesc = mAdapter.getBannerDesc(0);
        mBannerDescTv.setText(fristDesc);

        WindowManager wm = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();

        //动态指定高度
        if (mWidthProportion == 0 || mHeightProportion == 0) {
            return;
        }
        int height = (int) (width * mHeightProportion / mWidthProportion);
        getLayoutParams().height = height;
    }

    private void pagerSelect(int position) {
        //把之前亮着的点设置为默认
        DotIndicatorView oldIndicatorView = (DotIndicatorView) mDotContainerView.getChildAt(mCurrentPosition);
        oldIndicatorView.setDrawable(mIndictorNormalDrawable);
        //把当前位置的点，点亮
        mCurrentPosition = position % mAdapter.getCount();
        DotIndicatorView currentIndicatorView = (DotIndicatorView) mDotContainerView.getChildAt(position % mAdapter.getCount());
        currentIndicatorView.setDrawable(mIndictorFocusDrawable);

        //设置广告描述
        String bannerDesc = mAdapter.getBannerDesc(mCurrentPosition);
        mBannerDescTv.setText(bannerDesc);


    }

    /**
     * 初始化点的指示器
     */
    private void initDotIndicator() {
        int count = mAdapter.getCount();
        mDotContainerView.setGravity(getDotGravity());
        for (int i = 0; i < count; i++) {
            //不断的往点的指示器添加内容
            DotIndicatorView indicatorView = new DotIndicatorView(mContext);
            //设置大小
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mDotSzie, mDotSzie);
            //设置左右间距
            params.leftMargin = mDotDistance;
            indicatorView.setLayoutParams(params);
            if (i == 0) {//选中位置
                indicatorView.setDrawable(mIndictorFocusDrawable);
            } else {//未选中的
                indicatorView.setDrawable(mIndictorNormalDrawable);
            }
            mDotContainerView.addView(indicatorView);

        }
    }

    /**
     * 获取点的位置
     *
     * @return
     */
    private int getDotGravity() {
        switch (mDotGravity) {
            case 0:
                return Gravity.CENTER;
            case -1:
                return Gravity.LEFT;
            case 1:
                return Gravity.RIGHT;
        }
        return Gravity.LEFT;
    }

    /**
     * 把dip转成px
     *
     * @param dip
     * @return
     */
    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    /**
     * 开始滚动
     */

    public void startRoll() {
        mBannerVp.startRoll();

    }
}
