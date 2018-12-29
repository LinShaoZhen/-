package com.example.framelibrary.skin.attr;

import android.view.View;

import java.util.List;

/**
 * Created by nana on 2018/9/4.
 */

public class SkinView {
    private View mView;
    private List<SkinAttr> mSkinAttrs;

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.mView=view;
        this.mSkinAttrs=skinAttrs;
    }

    public void skin() {
        for (SkinAttr attr : mSkinAttrs) {
            attr.sking(mView);
        }
    }

}
