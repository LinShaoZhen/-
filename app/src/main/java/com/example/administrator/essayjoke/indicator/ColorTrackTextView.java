package com.example.administrator.essayjoke.indicator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.essayjoke.R;

/**
 * Created by nana on 2018/9/12.
 * 自定义字体变色
 */

public class ColorTrackTextView extends android.support.v7.widget.AppCompatTextView {
    private Paint mOriginPaint;//绘制不变色的画笔
    private Paint mChangePaint;//绘制变色的画笔
    private float mCurrentProgress = 0.0f;//当前的进度

    //实现不同朝向
    private Direction mDirection = Direction.LEFT_TO_RIGHT;


    public enum Direction {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT

    }


    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context, attrs);
    }

    /**
     * 初始化画笔
     *
     * @param context
     * @param attrs
     */

    private void initPaint(Context context, @Nullable AttributeSet attrs) {
        //获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
        int originColor = array.getColor(R.styleable.ColorTrackTextView_origninColor, getTextColors().getDefaultColor());
        int changeColor = array.getColor(R.styleable.ColorTrackTextView_changeColor, getTextColors().getDefaultColor());

        mOriginPaint = getPaintByColor(originColor);
        mChangePaint = getPaintByColor(changeColor);
        //回收
        array.recycle();
    }

    private Paint getPaintByColor(int color) {
        Paint paint = new Paint();
        //设置画笔颜色
        paint.setColor(color);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置防抖动
        paint.setDither(true);
        //设置字体大小
        paint.setTextSize(getTextSize());
        return paint;
    }

    //重写onDraw()方法实现一个字体两种颜色
    @Override
    protected void onDraw(Canvas canvas) {
        Rect bounds = new Rect();
        mChangePaint.getTextBounds(getText().toString(), 0, getText().toString().length(), bounds);
        int width = bounds.width();
        int x = getWidth() / 2 - (bounds.width()-5) / 2;

        //根据进度把中间值算出来
        int middle = (int) (mCurrentProgress * width);

        if (mDirection == Direction.LEFT_TO_RIGHT) {
            //绘制变色的
            drawText(canvas, mChangePaint, x, middle + x);
            //绘制不变色的
            drawText(canvas, mOriginPaint, middle + x, width +x);
        } else {
            //绘制变色的
            drawText(canvas, mChangePaint, width - middle + x, width+x);
            //绘制不变色的
            drawText(canvas, mOriginPaint, x, width - middle + x);
        }
    }

    /**
     * 绘制text
     *
     * @param canvas
     * @param paint
     * @param start
     * @param end
     */
    private void drawText(Canvas canvas, Paint paint, int start, int end) {
        canvas.save();
        //进行裁剪操作-----利用裁剪将字体分成两份，两个画笔同时画，来实现效果
        Rect rect = new Rect(start, 0, end, getHeight());
        canvas.clipRect(rect);

        // super.onDraw(canvas);
        //字体需要我们自己画
        String text = getText().toString();
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = getWidth() / 2 - bounds.width() / 2;
        //基线baseLine
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, x, baseLine, paint);
        canvas.restore();
    }

    /**
     * 设置朝向
     *
     * @param direction
     */
    public void setDirection(Direction direction) {
        this.mDirection = direction;

    }

    /**
     * 设置当前进度
     *
     * @param progress
     */
    public void setCurrentProgress(float progress) {
        this.mCurrentProgress = progress;
        invalidate();
    }

    /**
     * 设置变色的颜色
     *
     * @param changeColor
     */
    public void setChangeColor(int changeColor) {
        this.mChangePaint.setColor(changeColor);
    }

    /**
     * 设置没变色的颜色
     *
     * @param originColor
     */
    public void setOriginColor(int originColor) {
        this.mChangePaint.setColor(originColor);
    }


}
