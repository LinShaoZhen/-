package com.example.administrator.essayjoke.RecycleView.CommonAdapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.essayjoke.R;

/**
 * Created by nana on 2018/9/20.
 */

public class ImageLoader extends ViewHolder.HolderImageLoader {
    public ImageLoader(Object path) {
        super(path);
    }

    @Override
    public void loadImage(ImageView imageView, Object path) {
        Glide.with(imageView.getContext()).load(path).placeholder(R.drawable.icon).into(imageView);
    }
}
