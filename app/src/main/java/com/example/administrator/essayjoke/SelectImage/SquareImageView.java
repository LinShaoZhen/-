package com.example.administrator.essayjoke.SelectImage;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by nana on 2018/9/26.
 * 正方形的图片
 */

public class SquareImageView extends android.support.v7.widget.AppCompatImageView{
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //自定义view
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=width;
        //设置宽高一样
        setMeasuredDimension(width,height);
    }
}
