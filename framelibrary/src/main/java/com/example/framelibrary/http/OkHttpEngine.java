package com.example.framelibrary.http;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.example.baselibrary.http.EngineCallBack;
import com.example.baselibrary.http.HttpUtils;
import com.example.baselibrary.http.IHttpEngine;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by nana on 2018/8/30.
 * OkHttp默认的引擎
 */

public class OkHttpEngine implements IHttpEngine {
    private static final String TAG = "OkHttpEngine";
    private static OkHttpClient mOkHttpClient = new OkHttpClient();
    private static Handler mHandler = new Handler();

    @Override
    public void post(boolean cache, Context context, String url, Map<String, Object> parmas, final EngineCallBack callBack) {

        final String jointUrl = HttpUtils.jointParams(url, parmas);
        Log.e("Post请求路径：", jointUrl);


        final RequestBody requestBody = appendBody(parmas);
        final Request request = new Request.Builder()
                .url(url)
                .tag(context)
                .post(requestBody)
                .build();

        mOkHttpClient.newCall(request).enqueue(
                new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        callBack.onError(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        callBack.onSuccess(result);
                        Log.e("post返回结果", result);
                    }
                }
        );


    }

    //组装post请求参数body
    protected RequestBody appendBody(Map<String, Object> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        addParams(builder, params);
        return builder.build();
    }

    //添加参数
    private void addParams(MultipartBody.Builder builder, Map<String, Object> params) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.addFormDataPart(key, params.get(key) + "");
                Object value = params.get(key);
                if (value instanceof File) {
                    //处理文件
                    File file = (File) value;
                    builder.addFormDataPart(key, file.getName(), RequestBody.create(MediaType.parse(guessMimeType(file.getAbsolutePath())), file));
                } else if (value instanceof List) {
                    //代表提交的是List集合
                    try {
                        List<File> listFiles = (List<File>) value;
                        for (int i = 0; i < listFiles.size(); i++) {
                            //获取文件
                            File file = listFiles.get(i);
                            builder.addFormDataPart(key + i, file.getName(), RequestBody.create(MediaType.parse(guessMimeType(file.getAbsolutePath())), file));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    builder.addFormDataPart(key, value + "");
                }
            }
        }
    }

    /**
     * 猜测文件的类型
     */
    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    @Override
    public void get(final boolean cache, Context context, String url, Map<String, Object> parmas, final EngineCallBack callBack) {
        final String finalUrl = HttpUtils.jointParams(url, parmas);

        //判断需不需要缓存，判断有没有缓存
        if (cache) {
            String resultJson = CacheDataUtil.getCacheResultJson(finalUrl);
            if (!TextUtils.isEmpty(resultJson)) {
                Log.e(TAG, "有缓存，已读到缓存");
                //需要缓存，而且数据库有缓存
                callBack.onSuccess(resultJson);
            }
        }


        Log.e("get请求路径：", url);
        Request.Builder requestBuilder = new Request.Builder().url(finalUrl).tag(context);
        requestBuilder.method("GET", null);
        final Request request = requestBuilder.build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resultJson = response.body().string();
                //获取到数据之后先会执行成功方法
                if (cache) {
                    String cacheResultJson = CacheDataUtil.getCacheResultJson(finalUrl);
                    if (!TextUtils.isEmpty(resultJson)) {
                        //比对内容
                        if (resultJson.equals(cacheResultJson)) {
                            //内容一样不需要执行成功方法刷新界面
                            Log.e(TAG, "数据和缓存一致");
                            return;
                        }

                    }
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(resultJson);
                    }
                });
                Log.e("get返回结果", resultJson);
                if (cache) {
                    CacheDataUtil.cacheData(finalUrl, resultJson);
                }

            }
        });


    }
}
