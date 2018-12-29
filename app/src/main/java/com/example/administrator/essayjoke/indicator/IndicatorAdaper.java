package com.example.administrator.essayjoke.indicator;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by nana on 2018/9/12.
 * 指示器的adapter
 */

public abstract class IndicatorAdaper<T extends View>{

    //获取总共显示的条数
    public abstract int getCount();
        //根据当前位置获取一个Vview
    public abstract T getView(int position, ViewGroup parent);
    //高亮当前位置
    public void highLightIndicator(T view){

    }
    //重置当前位置
    public void restoreIndicator(T view){

    }
    //添加底部指示器
    public View getBottomTackView(){
        return null;
    }


}
