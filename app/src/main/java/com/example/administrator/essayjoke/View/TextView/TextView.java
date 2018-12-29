package com.example.administrator.essayjoke.View.TextView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.example.administrator.essayjoke.MessageService;
import com.example.administrator.essayjoke.R;

/**
 * @author lsz
 * @time 2018/10/4 10:50
 * 作用：
 */
public class TextView extends android.support.v7.widget.AppCompatTextView {
    private String mText;
    private int mTextColor=Color.BLACK;
    private int mTextSize=15;
    private Paint mPaint;


    public TextView(Context context) {
        this(context,null);
    }

    public TextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.TextView);
        mText=array.getString(R.styleable.TextView_text);
        mTextColor=array.getColor(R.styleable.TextView_textColor,mTextColor);
        mTextSize=array.getDimensionPixelSize(R.styleable.TextView_textSize,sp2px(mTextSize));
        array.recycle();
        mPaint=new Paint();
        mPaint.setAntiAlias(true);//抗锯齿
        //设置字体的大小和颜色
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);

    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);

        //如果是固定值，不需要计算宽高
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);

        //如果给的是warp_content 需要计算
        if (widthMode==MeasureSpec.AT_MOST){
            Rect bounds=new Rect();
            mPaint.getTextBounds(mText,0,mText.length(),bounds);
            width=bounds.width()+getPaddingLeft()+getPaddingRight();
        }
        if (heightMode==MeasureSpec.AT_MOST){
            Rect bounds=new Rect();
            mPaint.getTextBounds(mText,0,mText.length(),bounds);
            height=bounds.height()+getPaddingTop()+getPaddingBottom();
        }
        //设置控件的宽高
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint.FontMetricsInt fontMetricsInt =mPaint.getFontMetricsInt();
        int dy=(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
        int baseLine=getHeight()/2+dy;
        int x=getPaddingLeft();
        canvas.drawText(mText,x,baseLine,mPaint);

    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }
}
