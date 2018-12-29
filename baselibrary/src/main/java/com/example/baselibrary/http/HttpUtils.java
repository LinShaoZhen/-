package com.example.baselibrary.http;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by nana on 2018/8/30.
 * 自己的一套实现
 */

public class HttpUtils {
    //直接带参数，链式调用
    private String mUrl;
    //请求方式
    private int mType = GET_TYPE;
    private static final int POST_TYPE = 0x0011;
    private static final int GET_TYPE = 0x0010;

    private Map<String, Object> mParams;

    private Context mContext;
    //是否读取缓存
    private boolean mCache=false;

    private HttpUtils(Context context) {
        this.mContext = context;
        mParams = new ArrayMap<>();
    }

    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    //添加请求路径
    public HttpUtils url(String url) {
        mUrl = url;
        return this;
    }

    //post请求方式
    public HttpUtils post() {
        mType = POST_TYPE;
        return this;
    }

    //get请求方式
    public HttpUtils get() {
        mType = GET_TYPE;
        return this;
    }
    //是否配置缓存
    public HttpUtils cache(boolean isCache) {
        mCache = isCache;
        return this;
    }

    //添加参数
    public HttpUtils addParam(String key, Object value) {
        mParams.put(key, value);
        return this;
    }

    public HttpUtils addParams(Map<String, Object> params) {
        mParams.putAll(params);
        return this;
    }

    //添加回调
    public void execeute(EngineCallBack callBack) {
        if (callBack == null) {
            callBack = EngineCallBack.DEFUALT_CALL_BACK;
        }
        callBack.onPreExecute(mContext, mParams);

        //判断执行方法
        if (mType == POST_TYPE) {
            post(mContext, mUrl, mParams, callBack);
        }
        if (mType == GET_TYPE) {
            get(mContext, mUrl, mParams, callBack);
        }
    }

    public void execeute() {
        execeute(null);
    }

    //设置默认的引擎
    private static IHttpEngine mHttpEngine = null;

    //在application中初始化引擎
    public static void init(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    /**
     * 可以自带引擎
     *
     * @param httpEngine 引擎
     */
    public HttpUtils exchangeEngine(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
        return this;
    }

    private void get(Context context, String url, Map<String, Object> parmas, EngineCallBack callBack) {
        mHttpEngine.get(mCache,mContext, url, parmas, callBack);
    }

    private void post(Context context, String url, Map<String, Object> parmas, EngineCallBack callBack) {
        mHttpEngine.post(mCache,mContext, url, parmas, callBack);
    }

    public static String jointParams(String url, Map<String, Object> params) {
        if (params == null || params.size() <= 0) {
            return url;
        }
        StringBuffer stringBuffer = new StringBuffer(url);
        if (!url.contains("?")) {
            stringBuffer.append("?");
        } else {
            if (!url.endsWith("?")) {
                stringBuffer.append("&");
            }
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey() + "=" + entry.getValue() + "&");
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        return stringBuffer.toString();
    }

    /**
     * 解析一个类上面的class信息
     */
    public static Class<?> analysisClazzInfo(Object object) {
        Type gntType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) gntType).getActualTypeArguments();
        return (Class<?>) params[0];
    }


}
