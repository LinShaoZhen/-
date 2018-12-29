package com.example.administrator.essayjoke.View.QQStepView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.essayjoke.R;

/**
 * @author lsz
 * @time 2018/10/5 0:43
 * 作用：
 */
public class QQStepView extends View {
    private int mOuterColor;//外圆弧的颜色
    private int mInnerColor;//内圆弧的颜色
    private int mBorderWidth;//圆弧的宽度
    private int mStepTextSize;//文字的大小
    private int mStepTextColor;//文字的颜色

    //总共的（外圆弧的）
    private int mStepMax=100;
    //当前的（内圆弧的）
    private int mCurrentStep=50;

    private Paint mOutPaint,mInnerPaint,mTextPaint;

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mOuterColor = array.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
        mInnerColor = array.getColor(R.styleable.QQStepView_innerColor, mInnerColor);
        mBorderWidth = array.getDimensionPixelSize(R.styleable.QQStepView_borderWidth, mBorderWidth);
        mStepTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize, mStepTextSize);
        mStepTextColor = array.getColor(R.styleable.QQStepView_stepTextColor, mStepTextColor);
        array.recycle();

        mOutPaint=new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setStrokeWidth(mBorderWidth);
        mOutPaint.setColor(mOuterColor);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);//设置起始和末尾的形状为圆形（默认为方形）
        mOutPaint.setStyle(Paint.Style.STROKE);

        mInnerPaint=new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);//设置起始和末尾的形状为圆形（默认为方形）
        mInnerPaint.setStyle(Paint.Style.STROKE);

        mTextPaint=new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mStepTextColor);
        mTextPaint.setTextSize(mStepTextSize);

        //1.分析效果
        //2.确定自定义属性
        //3.在布局中使用
        //4.在自定义View中获取自定属性
        //5.重写onMeasure()方法
        //6.重写onraw()方法
        //画外圆弧，内圆弧，文字
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //确保是个正方形，取较小的那个值
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width > height ? height : width, width > height ? height : width);
    }
    //画外圆弧，画内圆弧，画文字

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画外圆弧
        int center=getWidth()/2;
        int radius=getWidth()/2-mBorderWidth/2;
        RectF rectF = new RectF(center-radius, center-radius, center+radius, center+radius);
        canvas.drawArc(rectF, 135, 270, false, mOutPaint);

        //画内圆弧
        if (mStepMax==0){
            return;
        }
        float sweepAngle=(float)mCurrentStep/mStepMax;
        canvas.drawArc(rectF,135,sweepAngle*270,false,mInnerPaint);

        //画文字
        String stepText=mCurrentStep+"";
        Rect textBounds=new Rect();
        mTextPaint.getTextBounds(stepText,0,stepText.length(),textBounds);
        int dx=getWidth()/2-textBounds.width()/2;
        //基线
        Paint.FontMetricsInt fontMetricsInt=mTextPaint.getFontMetricsInt();
        int dy=(fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom;
        int baseLine=getHeight()/2+dy;
        canvas.drawText(stepText,dx,baseLine,mTextPaint);
    }

    public void setStepMax(int stepMax){
        this.mStepMax=stepMax;
    }
    public void setCurrentStep(int cuttentStep){
        this.mCurrentStep=cuttentStep;
        //不断绘制
        invalidate();
    }
}
