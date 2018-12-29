package com.example.framelibrary.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Created by nana on 2018/9/3.
 * 皮肤的资源管理
 */

public class SkinResource {
    //资源通过这个对象获取
    private Resources mSkinResources;
    private String mPackageName;
    private String TAG="SkinResource";

    public SkinResource(Context context,String skinPath) {
        try {
            Resources superRes = context.getResources();
            AssetManager asset = AssetManager.class.newInstance();
            //添加本地下载好的资源皮肤
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            //反射执行方法
            method.invoke(asset, skinPath);
            mSkinResources = new Resources(asset, superRes.getDisplayMetrics(), superRes.getConfiguration());
            //获取skinPath包名
            mPackageName=context.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 通过名字获取Drawable
     * @param resName
     * @return
     */
    public Drawable getDrawableByName(String resName) {
        //获取图片的id
        try {
            int resId = mSkinResources.getIdentifier(resName, "drawable", mPackageName);
            //通过id获取图片资源
            Drawable drawable = mSkinResources.getDrawable(resId);
            return drawable;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过名字获取颜色
     * @param resName
     * @return
     */
    public ColorStateList getColorByName(String resName) {
        //获取图片的id
        try {
            int resId = mSkinResources.getIdentifier(resName, "color", mPackageName);
            //通过id获取图片资源
            Log.e(TAG,"resName-->"+resName+" ; mPackageName-->"+mPackageName+" ; resId--》"+resId);
            ColorStateList color = mSkinResources.getColorStateList(resId);
            return color;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
