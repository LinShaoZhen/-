package com.example.framelibrary.http;

/**
 * Created by nana on 2018/9/2.
 * 缓存的实体类
 */

public class CacheData {
    //请求的链接
    private String mUrlKey;
    // 后台返回的Json
    private String mResultJson;

    public CacheData() {
    }

    public CacheData(String urlKey, String resultJson) {
        this.mUrlKey = urlKey;
        this.mResultJson = resultJson;
    }

    public String getResultJson() {
        return mResultJson;
    }
}
