package com.example.administrator.essayjoke.PasswoedEditText;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.essayjoke.R;

/**
 * Created by nana on 2018/9/15.
 * 自定义键盘
 */

public class CustomerKeyboard extends LinearLayout implements View.OnClickListener {
    public CustomerKeyboard(Context context) {
        this(context, null);
    }

    public CustomerKeyboard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerKeyboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //直接加载布局
        inflate(context, R.layout.ui_customer_keyboard, this);
        setItemOnClickListener(this);
    }

    /**
     * 为每个view设置点击事件
     *
     * @param view
     */
    private void setItemOnClickListener(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            //不断递归调用，设置所有View 的点击事件监听
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childView = viewGroup.getChildAt(i);
                setItemOnClickListener(childView);
            }
        } else {
            view.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v instanceof TextView) {//点击的是数字
            String number = ((TextView) v).getText().toString().trim();
            if (mListener!=null){
                mListener.click(number);
            }
        }
        if (v instanceof ImageView) {//点击的是图片
            if (mListener!=null){
                mListener.delete();
            }

        }
    }

    private CustomerKeyboardClickListener mListener;
    //设置点击回调监听
    public void setOnCustomeKeyboardClickListener(CustomerKeyboardClickListener listener) {
        this.mListener=listener;

    }

    /**
     * 点击键盘的回调监听
     */
    public interface CustomerKeyboardClickListener {
        public void click(String number);

        public void delete();
    }

}
