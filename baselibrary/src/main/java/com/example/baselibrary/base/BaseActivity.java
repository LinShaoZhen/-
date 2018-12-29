package com.example.baselibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.baselibrary.ioc.ViewUtils;

/**
 * Created by nana on 2018/8/22.
 * 整个应用的BaseActivity
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局layout
        setContentView();
        //一些特定的算法，子类基本都会使用的
        ViewUtils.inject(this);
        //初始化头部
        initTitle();
        //初始化界面
        initView();
        //初始化数据
        initData();
    }


    //设置布局layout
    protected abstract void setContentView();

    //初始化头部
    protected abstract void initTitle();

    //初始化界面
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    /**
     * 启动Activity
     * @param clazz 需要启动的Activity
     */
    protected void startActivity(Class<?> clazz){
        Intent intent=new Intent(this,clazz);
        startActivity(intent);
    }

    /**
     * fandViewById
     * @return View
     */
    protected <T extends View> T viewById(int viewId){
     return   (T)findViewById(viewId);
    }

}
