package com.example.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by nana on 2018/8/30.
 * 引擎的规范
 */

public interface IHttpEngine {
    //get请求
    void get(boolean cache,Context context, String url, Map<String, Object> parmas, EngineCallBack callBack);

    //post请求
    void post(boolean cache,Context context, String url, Map<String, Object> parmas, EngineCallBack callBack);

    //下载文件

    //上传文件

    //https添加证书

}
