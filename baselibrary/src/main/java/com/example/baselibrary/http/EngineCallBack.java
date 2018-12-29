package com.example.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by nana on 2018/8/30.
 */

public interface EngineCallBack {
    //在执行前会回调的方法
    public void onPreExecute(Context context, Map<String,Object> params);
    //失败
    public void onError(Exception e);
    //成功
    public void onSuccess(String result);

    //默认的回调
    public final EngineCallBack DEFUALT_CALL_BACK=new EngineCallBack() {
        @Override
        public void onPreExecute(Context context, Map<String, Object> params) {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };
}
