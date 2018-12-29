package com.example.framelibrary.http;

import android.content.Context;

import com.example.baselibrary.http.EngineCallBack;
import com.example.baselibrary.http.HttpUtils;
import com.google.gson.Gson;

import java.util.Map;

/**
 * Created by nana on 2018/8/31.
 */

public abstract class HttpCallBack<T> implements EngineCallBack {
    //添加公用参数
    @Override
    public void onPreExecute(Context context, Map<String, Object> params) {
        onPreExecute();
    }

    //开始执行了
    public void onPreExecute() {

    }

    @Override
    public void onSuccess(String result) {
        Gson gson = new Gson();
        T objResult = (T) gson.fromJson(result, HttpUtils.analysisClazzInfo(this));
        onSuccess(objResult);
    }

    //返回可以直接操作的对象
    public abstract void onSuccess(T result);
}
