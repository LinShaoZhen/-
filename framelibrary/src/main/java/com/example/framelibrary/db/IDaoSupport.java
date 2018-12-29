package com.example.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;

import com.example.framelibrary.db.curd.QuerySupport;

import java.util.List;

/**
 * Created by nana on 2018/9/1.
 */

public interface IDaoSupport<T> {
    //插入数据
    public long insert(T t);
    public void init(SQLiteDatabase sqLiteDatabase,Class<T> clazz);
    //批量插入检测性能
    public void insert(List<T> datas);
    //获取专门查询的支持类
    public QuerySupport<T> querySupport();

    int update(T obj, String whereClause, String... whereArgs);

    int delete(String whereClause, String... whereArgs);
}
