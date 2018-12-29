package com.example.framelibrary.http;

import android.util.Log;

import com.example.framelibrary.db.DaoSupportFactory;
import com.example.framelibrary.db.IDaoSupport;

import java.util.List;

/**
 * Created by nana on 2018/9/2.
 */

public class CacheDataUtil {
    private static final String TAG = "CacheDataUtil";

    public static String getCacheResultJson(String finalUrl) {
        final IDaoSupport<CacheData> dataDaoSupport = DaoSupportFactory.getFactory().getDao(CacheData.class);
        List<CacheData> cacheDatas = dataDaoSupport.querySupport().selection("mUrlKey=?").selectionArgs(finalUrl).query();
        if (cacheDatas.size() != 0) {
            //代表有数据
            CacheData cacheData = cacheDatas.get(0);
            String resultJson = cacheData.getResultJson();
            return resultJson;
        }
        return null;
    }
//缓存数据
    public static long cacheData(String finalUrl, String resultJson) {
        final IDaoSupport<CacheData> dataDaoSupport = DaoSupportFactory.getFactory().getDao(CacheData.class);
        //缓存数据
        dataDaoSupport.delete("mUrlKey=?", finalUrl);
        long number = dataDaoSupport.insert(new CacheData(finalUrl, resultJson));
        Log.e(TAG, "缓存成功");
        return number;
    }
}
