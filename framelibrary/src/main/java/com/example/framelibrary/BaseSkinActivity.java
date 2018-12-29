package com.example.framelibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;


import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;

import com.example.baselibrary.base.BaseActivity;
import com.example.framelibrary.skin.SkinAttrSupport;
import com.example.framelibrary.skin.SkinManager;
import com.example.framelibrary.skin.SkinResource;
import com.example.framelibrary.skin.attr.SkinAttr;
import com.example.framelibrary.skin.attr.SkinView;
import com.example.framelibrary.skin.callback.ISkinChangeListener;
import com.example.framelibrary.skin.support.SkinAppcompatViewInflater;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nana on 2018/8/22.
 */

public abstract class BaseSkinActivity extends BaseActivity implements LayoutInflaterFactory,ISkinChangeListener {
    //后面会写插件换肤
    private SkinAppcompatViewInflater mAppCompatViewInflater;

    private String TAG = "BaseSkinActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory(layoutInflater, this);

/*        LayoutInflater layoutInflater = LayoutInflater.from(this);
        LayoutInflaterCompat.setFactory(layoutInflater, new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

                return null;
            }
        });*/


        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        //拦截到view
        //1.创建view
        View view = createView(parent, name, context, attrs);
        Log.e(TAG, view + "");

        //2.解析属性  包括src textColor backgroun
        //2.1一个Activity对应着多个SkinView
        if (view != null) {
            List<SkinAttr> skinAttrs = SkinAttrSupport.getskinAttrs(context, attrs);
            SkinView skinView = new SkinView(view, skinAttrs);
            //3.统一交给SkinManager管理
            managerSkinView(skinView);
            //检测要不要换肤
           SkinManager.getInstance().checkChangeSkin(skinView);
        }


        return view;
    }

    /**
     * 统一管理SkinView
     * @param skinView
     */
    private void managerSkinView(SkinView skinView) {
      List<SkinView> skinViews=  SkinManager.getInstance().getSkinViews(this);
      if (skinViews==null){
          skinViews=new ArrayList<>();
          SkinManager.getInstance().register(this,skinViews);
      }
      skinViews.add(skinView);
    }

    public View createView(View parent, final String name, @NonNull Context context,
                           @NonNull AttributeSet attrs) {
        final boolean isPre21 = Build.VERSION.SDK_INT < 21;

        if (mAppCompatViewInflater == null) {
            mAppCompatViewInflater = new SkinAppcompatViewInflater();
        }

        // We only want the View to inherit it's context if we're running pre-v21
        final boolean inheritContext = isPre21 && true
                && shouldInheritContext((ViewParent) parent);

        return mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                isPre21, /* Only read android:theme pre-L (L+ handles this anyway) */
                true /* Read read app:theme as a fallback at all times for legacy reasons */
        );
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            // The initial parent is null so just return false
            return false;
        }
        while (true) {
            if (parent == null) {
                // Bingo. We've hit a view which has a null parent before being terminated from
                // the loop. This is (most probably) because it's the root view in an inflation
                // call, therefore we should inherit. This works as the inflated layout is only
                // added to the hierarchy at the end of the inflate() call.
                return true;
            } else if (parent == getWindow().getDecorView() || !(parent instanceof View)
                    || ViewCompat.isAttachedToWindow((View) parent)) {
                // We have either hit the window's decor view, a parent which isn't a View
                // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                // is currently added to the view hierarchy. This means that it has not be
                // inflated in the current inflate() call and we should not inherit the context.
                return false;
            }
            parent = parent.getParent();
        }
    }

    @Override
    public void changeSkin(SkinResource skinResource) {

    }
}
