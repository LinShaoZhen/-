package com.example.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

/**
 * Created by nana on 2018/9/1.
 */

public class DaoSupportFactory {
    private static DaoSupportFactory mFactory;

    //持有外部数据的引用
    private SQLiteDatabase mSQLiteDatabase;

    private DaoSupportFactory() {
        //把数据库放到内存卡里面  判断是否有内存卡，6。0要动态申请权限
        File dbRoot = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),
                File.separator + "nhdz" + File.separator + "database");
        if (!dbRoot.exists()) {
            dbRoot.mkdirs();
        }
        File dbFile = new File(dbRoot, "nhdz.db");
        //打开或者创建一个数据库
        mSQLiteDatabase= SQLiteDatabase.openOrCreateDatabase(dbFile, null);
    }

    //使用单例设计模式获取数据库工厂类
    public static DaoSupportFactory getFactory() {
        if (mFactory == null) {
            synchronized (DaoSupportFactory.class) {
                if (mFactory == null) {
                    mFactory = new DaoSupportFactory();
                }
            }
        }
        return mFactory;
    }

    public <T>IDaoSupport<T> getDao(Class<T> clazz){
        IDaoSupport<T> daoSoupport=new DaoSupport<T>();
        daoSoupport.init(mSQLiteDatabase,clazz);
        return daoSoupport;
        }


}
