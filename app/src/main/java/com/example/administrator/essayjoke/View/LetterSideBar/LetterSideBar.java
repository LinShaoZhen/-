package com.example.administrator.essayjoke.View.LetterSideBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author lsz
 * @time 2018/10/5 15:33
 * 作用：
 */
public class LetterSideBar extends View {
    private Paint mPaint;
    //定义26个字母
    private static String[] mLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    private String mCurrentTouchLetter;//当前触摸的位置

    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(sp2px(12));
        mPaint.setColor(Color.BLUE);
    }

    private float sp2px(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //计算指定宽度=左右的padding+字母的宽度
        int textWidth = (int) mPaint.measureText("A");
        int width = getPaddingLeft() + getPaddingRight() + textWidth;

        //高度可以直接获取
        int height = MeasureSpec.getSize(heightMeasureSpec);

        //设置宽高
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画26个字母
        //每个item的高度
        int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / mLetters.length;
        for (int i = 0; i < mLetters.length; i++) {
            //
            int textWidth = (int) mPaint.measureText(mLetters[i]);
            int x = getWidth() / 2 - textWidth / 2;
            //每个字母的中间位置
            int letterCenterY = i * itemHeight + itemHeight / 2 + getPaddingTop();
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
            int baseLine = letterCenterY + dy;

            //当前字母高亮
            if (mLetters[i].equals(mCurrentTouchLetter)) {
                mPaint.setColor(Color.RED);
                canvas.drawText(mLetters[i], x, baseLine, mPaint);
            } else {
                mPaint.setColor(Color.BLUE);
                canvas.drawText(mLetters[i], x, baseLine, mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                //计算出当前触摸的位置
                float currentMoveY = event.getY();
                int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / mLetters.length;
                int currentPosition = (int) (currentMoveY / itemHeight);
                //防止數組越界
                if (currentPosition < 0) {
                    currentPosition = 0;
                }
                if (currentPosition > mLetters.length - 1) {
                    currentPosition = mLetters.length - 1;
                }
                mCurrentTouchLetter = mLetters[currentPosition];

                //触摸回调
                if (mListener != null) {
                    mListener.touch(mCurrentTouchLetter,true);
                }


                //重新绘制
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                //触摸回调
                if (mListener != null) {
                    mListener.touch(mCurrentTouchLetter,false);
                }
                break;
        }
        return true;
    }

    private LetterTouchListener mListener;

    public void setOnLetterTouchListener(LetterTouchListener listener) {
        this.mListener = listener;
    }

    //设置触摸回调
    public interface LetterTouchListener {
        void touch(CharSequence letter,boolean isTouch);
    }

}
