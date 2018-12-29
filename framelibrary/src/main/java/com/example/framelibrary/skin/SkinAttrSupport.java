package com.example.framelibrary.skin;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.example.framelibrary.skin.attr.SkinAttr;
import com.example.framelibrary.skin.attr.SkinType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nana on 2018/9/3.
 * 皮肤属性解析的支持类
 */

public class SkinAttrSupport {
    ;
    private static final String TAG = "SkinAttrSupport";

    /**
     * 获取SkinAttr的属性
     *
     * @param context
     * @param attrs
     * @return
     */
    public static List<SkinAttr> getskinAttrs(Context context, AttributeSet attrs) {

        //解析background textColor src
        List<SkinAttr> skinAttrs = new ArrayList<>();
        int attrLength = attrs.getAttributeCount();//获取属性的长度
        for (int index = 0; index < attrLength; index++) {
            //获取属性名称
            String attrName = attrs.getAttributeName(index);
            //获取属性的值
            String attrValue = attrs.getAttributeValue(index);
            //   Log.e(TAG, "attrName-->" + attrName+" ; attrValue-->"+attrValue);
            //只获取重要的属性
            SkinType skinType = getSkinType(attrName);
            if (skinType != null) {
                //目前资源名称只是一个attrValue是一个@int类型
                String resName = getResNmae(context, attrValue);
                if (TextUtils.isEmpty(resName)){
                    continue;
                }
                SkinAttr skinAttr = new SkinAttr(resName, skinType);
                skinAttrs.add(skinAttr);
            }
        }

        return skinAttrs;
    }

    /**
     * 获取资源的名称
     * @param context
     * @param attrValue
     * @return
     */
    private static String getResNmae(Context context, String attrValue) {
        //@2131492979只有这种类型的才需要获取
        if (attrValue.startsWith("@")){
            attrValue=attrValue.substring(1);
            int resId=Integer.parseInt(attrValue);
           return context.getResources().getResourceEntryName(resId);
        }
        return null;
    }

    /**
     * 通过名称获取SkinType
     *
     * @param attrName
     * @return
     */
    private static SkinType getSkinType(String attrName) {
        SkinType[] skinTypes = SkinType.values();
        for (SkinType skinType : skinTypes) {
            if (skinType.getResName().equals(attrName)) {
                return skinType;
            }
        }
        return null;
    }
}
