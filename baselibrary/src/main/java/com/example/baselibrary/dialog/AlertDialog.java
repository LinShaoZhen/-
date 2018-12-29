package com.example.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.speech.tts.Voice;
import android.support.annotation.NonNull;
import android.support.v7.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.baselibrary.R;

import java.lang.ref.WeakReference;

/**
 * Created by nana on 2018/8/28.
 * 自定义的万能Dialog
 */

public class AlertDialog extends Dialog{
    private AlertController mAlert;

    public AlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mAlert=new AlertController(this, getWindow());
    }
    /**
     * 设置文本
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
       mAlert.setText(viewId,text);
    }

    public  <T extends View>T getView(int viewId) {
       return mAlert.getView(viewId);
    }

    /**
     * 设置点击事件
     * @param viewId
     * @param listener
     */
    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        mAlert.setOnClickListener(viewId,listener);
    }



    public static class Builder{
        private final AlertController.AlertParams P;
        public Builder(Context context){
            this(context, R.style.dialog);
        }
        public Builder(Context context,int themeResId){
            P=new AlertController.AlertParams(context,themeResId);
        }


        public Builder setContentView(View view){
            P.mView=view;
            P.mViewLayoutResId=0;
            return this;
        }

        /**
         * 设置布局内容的layoutId
         * @param layoutResId
         * @return
         */
        public Builder setContentView(int layoutResId){
            P.mView=null;
            P.mViewLayoutResId=layoutResId;
            return this;
        }
        //设置文本
        public Builder setText(int viewId,CharSequence text){
            P.mTextArray.put(viewId,text);
            return this;
        }
        //设置点击事件
        public Builder setOnClickListener(int view,View.OnClickListener listener){
            P.mClickArray.put(view,listener);
            return this;
        }
        public Builder setCancelable(boolean cancelable){
            P.mCancelable=cancelable;
            return this;
        }
        public Builder setOnDismissListener(OnDismissListener onDismissListener){
            P.mOnDismissListener=onDismissListener;
            return this;
        }
        public Builder setOnKeyListener(OnKeyListener onKeyListener){
            P.mOnKeyListener=onKeyListener;
            return this;
        }
        //配置一些万能的参数
        //全屏
        public Builder fullWidth(){
            P.mWidth= ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        /**
         * 从底部弹出
         * @param isAnimation 是否有动画
         * @return
         */
        public  Builder fromButton(boolean isAnimation){
            if (isAnimation){
                P.mAnimations=R.style.dialog_from_button_anim;
            }
            P.mGravity= Gravity.BOTTOM;
            return this;
        }

        /**
         * 设置Dialog宽高
         * @param width
         * @param height
         * @return
         */
        public Builder setWidthAndHeight(int width,int height){
            P.mWidth=width;
            P.mHeight=height;

            return this;
        }

        /**
         * 添加默认动画
         * @return
         */
        public Builder addDefaultAnimation(){
          P.mAnimations=R.style.dialog_scale_anim;
            return this;
        }

        /**
         * 设置动画
         * @param styleAnimation
         * @return
         */

        public Builder setAnimations(int styleAnimation){
            P.mAnimations=styleAnimation;
            return this;

        }

        public AlertDialog create(){
            final AlertDialog dialog=new AlertDialog(P.mContext,P.mThemeResId);
            P.apply(dialog.mAlert);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable){
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener!=null){
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }
        public AlertDialog show(){
            final AlertDialog dialog=create();
            dialog.show();
            return dialog;
        }
    }
}
