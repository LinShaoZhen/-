package com.example.administrator.essayjoke;

import android.app.Application;

import com.example.baselibrary.fixBug.FixDexManager;
import com.example.baselibrary.http.HttpUtils;
import com.example.framelibrary.ExceptionCrashHandler;
import com.example.framelibrary.http.OkHttpEngine;
import com.example.framelibrary.skin.SkinManager;

/**
 * Created by nana on 2018/8/25.
 */

public class BaseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        HttpUtils.init(new OkHttpEngine());

        SkinManager.getInstance().init(this);
        //设置全局异常捕捉类
        ExceptionCrashHandler.getInstance().init(this);
        //加载之前的apatch包
        try {
            FixDexManager fixDexManager=new FixDexManager(this);
            fixDexManager.loadFixDex();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
