package com.example.framelibrary.skin.attr;

import android.view.View;

/**
 * Created by nana on 2018/9/4.
 */

public class SkinAttr {
    private String mResName;
    private SkinType mSkinType;

    public SkinAttr(String resName, SkinType skinType) {
        this.mResName = resName;
        this.mSkinType = skinType;
    }

    public void sking(View view) {
        mSkinType.skin(view, mResName);
    }
}
