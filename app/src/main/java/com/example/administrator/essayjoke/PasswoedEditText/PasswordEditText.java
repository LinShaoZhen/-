package com.example.administrator.essayjoke.PasswoedEditText;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.example.administrator.essayjoke.R;

/**
 * Created by nana on 2018/9/14.
 * 自定义输入密码框
 */

public class PasswordEditText extends android.support.v7.widget.AppCompatEditText {
    //画笔
    private Paint mPaint;
    //一个密码所占的宽度
    private int mPasswordItemWidth;
    //密码的个数,默认为6位
    private int mPasswordNumber = 6;
    //背景边框颜色
    private int mBgColor = Color.parseColor("#d1d2d6");
    //背景边框大小
    private int mBgSize = 1;
    //背景边框圆角大小
    private int mBgCorner = 0;
    //分割线的颜色
    private int mDivisionLineColor = mBgColor;
    private int mDivisionLineSize = 1;
    //密码圆点的颜色
    private int mPasswordColor = mDivisionLineColor;
    //密码圆点的半径大小
    private int mPasswordRadius = 4;

    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(context, attrs);
        initPaint();
        //密码默认只能设置数字和字母
        setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//设置抗锯齿
        mPaint.setDither(true);//设置防抖动
    }

    /**
     * 初始化属性
     *
     * @param context
     * @param attrs
     */

    private void initAttributeSet(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText);
        //获取大小
        mDivisionLineSize = (int) array.getDimension(R.styleable.PasswordEditText_divisionLineSize, dip2px(mDivisionLineSize));
        mPasswordRadius = (int) array.getDimension(R.styleable.PasswordEditText_passwordRadius, dip2px(mPasswordRadius));
        mBgSize = (int) array.getDimension(R.styleable.PasswordEditText_bgSize, dip2px(mBgSize));
        mBgCorner = (int) array.getDimension(R.styleable.PasswordEditText_bgCorner, mBgCorner);
        mPasswordNumber = array.getInteger(R.styleable.PasswordEditText_passwordNumber, mPasswordNumber);
        //获取颜色
        mBgColor = array.getColor(R.styleable.PasswordEditText_bgColor, mBgColor);
        mDivisionLineColor = array.getColor(R.styleable.PasswordEditText_divisionLineColor, mDivisionLineColor);
        mPasswordColor = array.getColor(R.styleable.PasswordEditText_passwordColor, mPasswordColor);
        array.recycle();
    }

    /**
     * dip转px
     *
     * @param dip
     * @return
     */
    private float dip2px(int dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    /**
     * 重写OnDraw方法来画布局
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //一个密码的宽度
        mPasswordItemWidth = (getWidth() - 2 * mBgSize - (mPasswordNumber - 1) * mDivisionLineSize) / mPasswordNumber;
        //话背景
        drawBg(canvas);
        //画分割线
        drawDivisionLine(canvas);
        //绘制密码
        drawPassword(canvas);

        if(mListener!=null){
            String password=getText().toString().trim();
            if (password.length()>=mPasswordNumber) {
                mListener.passwordFull(password);
            }
        }
    }

    /**
     * 绘制密码
     */
    private void drawPassword(Canvas canvas) {
        //绘制实心的
        mPaint.setStyle(Paint.Style.FILL);
        //设置密码的颜色
        mPaint.setColor(mPasswordColor);
        //获取密码的文本
        String text = getText().toString().trim();
        //获取密码文本的长度
        int passwordLength = text.length();
        //不断的绘制实心的圆
        for (int i = 0; i < passwordLength; i++) {
            int cx = mBgSize + i * mPasswordItemWidth + i * mDivisionLineSize + mPasswordItemWidth / 2;
            int cy = getHeight() / 2;
            canvas.drawCircle(cx, cy, mPasswordRadius, mPaint);
        }
    }

    /**
     * 绘制分割线
     *
     * @param canvas
     */
    private void drawDivisionLine(Canvas canvas) {
        //设置画笔大小
        mPaint.setStrokeWidth(mDivisionLineSize);
        //设置分割线的颜色
        mPaint.setColor(mDivisionLineColor);
        for (int i = 0; i < mPasswordNumber - 1; i++) {
            int startX = mBgSize + (i + 1) * mPasswordItemWidth + i * mDivisionLineSize;
            int startY = mBgSize;
            int endX = startX;
            int endY = getHeight() - mBgSize;
            canvas.drawLine(startX, startY, endX, endY, mPaint);
        }
    }

    /**
     * 绘制背景
     */
    private void drawBg(Canvas canvas) {
        //判断是否有矩形，如果没有用drawRect（）绘制，如果有使用drawRoundRext（）绘制
        RectF rect = new RectF(mBgSize, mBgSize, getWidth() - mBgSize, getHeight() - mBgSize);
        //设置画笔宽度
        mPaint.setStrokeWidth(mBgSize);
        //设置背景的颜色
        mPaint.setColor(mBgColor);
        //设置画笔的模式（这里是空心）
        mPaint.setStyle(Paint.Style.STROKE);
        if (mBgCorner == 0) {
            canvas.drawRect(rect, mPaint);
        } else {
            canvas.drawRoundRect(rect, mBgCorner, mBgCorner, mPaint);
        }
    }

    /**
     * 添加一个密码
     *
     * @param number
     */
    public void addPassword(String number) {
        //把之前的密码取出来
        String password = getText().toString().trim();
        //密码不能超过当前设置的最大密码个数
        if (password.length() >= mPasswordNumber) {
            return;
        }
        //密码叠加
        password += number;
        setText(password);
    }

    /**
     * 删除最后一个密码
     */
    public void deleteLastPassword() {
        String password = getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            return;
        }
        password = password.substring(0, password.length() - 1);
        setText(password);
    }

    //设置当前密码是否为满的接口回调
    private PasswordFullListener mListener;

    public void setOnPasswordFullListener(PasswordFullListener listener) {
        this.mListener = listener;
    }

    /**
     * 密码已经填满了
     */
    public interface PasswordFullListener {
        public void passwordFull(String password);
    }
}
