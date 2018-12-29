package com.example.framelibrary.skin.config;

import android.content.Context;

/**
 * Created by nana on 2018/9/5.
 */

public class SkinPreUtils {
    private static SkinPreUtils mSkinPreUtils;
    private Context mContext;

    private SkinPreUtils(Context context) {
        this.mContext = context.getApplicationContext();
    }

    //单例设计模式
    public static SkinPreUtils getInstance(Context context) {
        if (mSkinPreUtils == null) {
            synchronized (SkinPreUtils.class) {
                if (mSkinPreUtils == null) {
                    mSkinPreUtils = new SkinPreUtils(context);
                }
            }
        }
        return mSkinPreUtils;
    }

    /**
     * 保存当前皮肤路径
     * @param skinPath
     */
    public void saveSkinPath(String skinPath) {
        mContext.getSharedPreferences(SkinConfig.SKIN_INFO_NAME, Context.MODE_PRIVATE)
                .edit().putString(SkinConfig.SKIN_PATH_NAME, skinPath).commit();
    }

    /**
     * 获取皮肤的路径
     * @return 当前皮肤路径
     */
    public String getSkinPath(){
        String skinPath=mContext.getSharedPreferences(SkinConfig.SKIN_INFO_NAME,Context.MODE_PRIVATE)
                .getString(SkinConfig.SKIN_PATH_NAME,"");
        return skinPath;
    }

    /**
     * 清空皮肤路径
     */
    public void clearSkinInfo() {
        saveSkinPath("");
    }
}
