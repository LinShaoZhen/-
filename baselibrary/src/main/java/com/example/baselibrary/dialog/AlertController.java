package com.example.baselibrary.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by nana on 2018/8/28.
 */

class AlertController {
    private AlertDialog mDialog;
    private Window mWindow;
    private DialogViewHelper mViewHelper;

    public AlertController(AlertDialog alertDialog, Window window) {
        this.mDialog=alertDialog;
        this.mWindow=window;
    }

    public void setViewHelper(DialogViewHelper viewHelper) {
        this.mViewHelper = viewHelper;
    }

    /**
     * 设置文本
     * @param viewId
     * @param text
     */
    public void setText(int viewId, CharSequence text) {
        mViewHelper.setText(viewId,text);
    }

    public  <T extends View>T getView(int viewId) {
        return mViewHelper.getView(viewId);
    }

    /**
     * 设置点击事件
     * @param viewId
     * @param listener
     */
    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        mViewHelper.setOnClickListener(viewId,listener);
    }
    /**
     * 获取Dialog
     * @return
     */

    public AlertDialog getDialog() {
        return mDialog;
    }

    /**
     * 获取Dialog的Window
     * @return
     */
    public Window getWindow() {
        return mWindow;
    }

    public static class AlertParams {
        public Context mContext;
        public int mThemeResId;
        public boolean mCancelable=true;//点击空白处是否能够取消
        public DialogInterface.OnCancelListener mOnCancelListener;//Dialog cancel监听
        public DialogInterface.OnDismissListener mOnDismissListener;//Dialog Dismiss监听
        public DialogInterface.OnKeyListener mOnKeyListener;//Dialog按键监听
        public View mView;//布局View
        public int mViewLayoutResId;//布局Layout id
        //存放字体的修改
        public SparseArray<CharSequence> mTextArray=new SparseArray<>();
        //存放点击事件
        public SparseArray<View.OnClickListener> mClickArray=new SparseArray<>();
        public int mAnimations=0;//动画
        public int  mGravity= android.view.Gravity.CENTER;//位置
        public int mWidth= ViewGroup.LayoutParams.WRAP_CONTENT;//Dialog宽度
        public int mHeight= ViewGroup.LayoutParams.WRAP_CONTENT;//Dialog高度

        public AlertParams(Context context, int themeResId) {
            this.mContext = context;
            this.mThemeResId = themeResId;
        }



        /**
         * 绑定和设置参数
         * @param mAlert
         */
        public void apply(AlertController mAlert) {
            //1.设置布局
            DialogViewHelper viewHelper=null;
            if(mViewLayoutResId!=0){
                viewHelper=new DialogViewHelper(mContext,mViewLayoutResId);
            }
            if (mView!=null){
                viewHelper=new DialogViewHelper();
                viewHelper.setContentView(mView);
            }
            if (viewHelper==null){
                throw new IllegalArgumentException("请设置布局setContentView（）");
            }

            //给Dialog设置布局
            mAlert.getDialog().setContentView(viewHelper.getContentView());

            mAlert.setViewHelper(viewHelper);
            //2.设置文本
            int textArraysize=mTextArray.size();
            for (int i=0;i<textArraysize;i++){
                mAlert.setText(mTextArray.keyAt(i),mTextArray.valueAt(i));
            }
            //3.设置点击事件
            int clickArraysize=mClickArray.size();
            for (int i=0;i<textArraysize;i++){
                mAlert.setOnClickListener(mClickArray.keyAt(i),mClickArray.valueAt(i));
            }

            //4.配置自定义的效果 全屏 从底部弹出 默认动画
            Window window=mAlert.getWindow();
            //设置位置
            window.setGravity(mGravity);
            //设置动画
            if (mAnimations!=0){
                window.setWindowAnimations(mAnimations);
            }
            //设置宽高
            WindowManager.LayoutParams params = window.getAttributes();
            params.width=mWidth;
            params.height=mHeight;
            window.setAttributes(params);


        }
    }
}
