package com.example.framelibrary.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framelibrary.skin.SkinManager;
import com.example.framelibrary.skin.SkinResource;

/**
 * Created by nana on 2018/9/4.
 */

public enum SkinType {
    TEXT_COLOR("textColor") {
        @Override
        public void skin(View view, String resName) {
            SkinResource skinResource = getSkinResource();
            ColorStateList color = skinResource.getColorByName(resName);
            if (color == null) {
                return;
            }
            TextView textView=(TextView)view;
            textView.setTextColor(color);
        }
    }, BACKGROUND("background") {
        @Override
        public void skin(View view, String resName) {
            Log.e("LSZ",resName+"");
            //背景可能是颜色可能是图片
            SkinResource skinResource = getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(resName);
            //背景为图片
            if (drawable != null) {
                Log.e("LSZ",drawable+"");
                ImageView imageView=(ImageView)view;
                imageView.setBackgroundDrawable(drawable);
                return;
            }
            //背景为颜色
            ColorStateList color = skinResource.getColorByName(resName);
            if (color!=null){
                Log.e("LSZ",color+"");
                view.setBackgroundColor(color.getDefaultColor());
            }

        }
    }, SRC("src") {
        @Override
        public void skin(View view, String resName) {
            SkinResource skinResource = getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(resName);
            //背景为图片
            if (drawable != null) {
                ImageView imageView=(ImageView)view;
                imageView.setImageDrawable(drawable);
                return;
            }
        }
    };

    private String mResName;

    SkinType(String resName) {
        this.mResName = resName;
    }

    public abstract void skin(View view, String resName);

    public String getResName() {
        return mResName;
    }

    public SkinResource getSkinResource() {
        return SkinManager.getInstance().getSkinResource();
    }
}
