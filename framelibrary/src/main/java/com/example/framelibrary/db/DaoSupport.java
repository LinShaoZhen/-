package com.example.framelibrary.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.framelibrary.db.curd.QuerySupport;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nana on 2018/9/1.
 */


public class DaoSupport<T> implements IDaoSupport<T> {
    private static final String TAG = "DaoSupport";
    //SQLiteDatabase对象
    private SQLiteDatabase mSQLiteDatabase;
    //泛型类（需要存储的数据的对象类）
    private Class<T> mClazz;
    private static final Object[] mPutMethodArgs = new Object[2];
    private static final Map<String, Method> mPutMethods = new HashMap<>();

    public void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz) {
        this.mSQLiteDatabase = sqLiteDatabase;
        this.mClazz = clazz;
        //创建表
        StringBuffer sb = new StringBuffer();
        sb.append("create table if not exists ").append(DaoUtil.getTableName(mClazz)).append("(id integer primary key autoincrement, ");

        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);//设置权限
            String name = field.getName();
            String type = field.getType().getSimpleName();//返回的是int、boolean、String
            //type需要进行转换int--》integer、String -->text、
            sb.append(name);
            sb.append(DaoUtil.getColumnType(type)).append(", ");
        }
        sb.replace(sb.length() - 2, sb.length() - 1, ")");
        String createTableSql = sb.toString();
        Log.e(TAG, "表语句：" + createTableSql);
        //创建表
        mSQLiteDatabase.execSQL(createTableSql);
    }

    @Override
    public void insert(List<T> datas) {
        //不使用事务总耗时：6005
        //第三方的大概耗时：471
        //使用了事务之后的耗时：263
        //将反射进行缓存之后总耗时：240

        mSQLiteDatabase.beginTransaction();
        for (T data : datas) {
            //调用单条插入
            insert(data);
        }
        mSQLiteDatabase.setTransactionSuccessful();
        mSQLiteDatabase.endTransaction();


    }
private QuerySupport<T> mQuerySupport;
    @Override
    public QuerySupport<T> querySupport() {
        if (mQuerySupport==null){
            mQuerySupport=new QuerySupport<T>(mSQLiteDatabase,mClazz);
        }
        return mQuerySupport;
    }


    //插入数据库，T是任意对象
    @Override
    public long insert(T obj) {
        //插入的方法还是使用原生的，只是进行的封装
        ContentValues values = contentValuesByObj(obj);

        return mSQLiteDatabase.insert(DaoUtil.getTableName(mClazz), null, values);
    }

    //将obj转成ContentValues
    private ContentValues contentValuesByObj(T obj) {
        ContentValues values = new ContentValues();
        //封装values
        Field[] fields = mClazz.getDeclaredFields();
        for (Field field : fields) {
            try {
                //设置权限
                field.setAccessible(true);
                String key = field.getName();
                //获取value
                Object value = field.get(obj);
                //put的第二个参数是类型如integer，varchar需要转换
                //反射在一定程度上会影响性能 源码里面 activity实例 view的创建都是使用了反射
                //第三方和源码对放反射的处理是最好的教材

                mPutMethodArgs[0] = key;
                mPutMethodArgs[1] = value;
                //还是使用反射获取方法
                String filedTtpeName = field.getType().getName();
                Method putMethod = mPutMethods.get(filedTtpeName);
                if (putMethod == null) {
                    putMethod = ContentValues.class.getDeclaredMethod("put", String.class, value.getClass());
                    mPutMethods.put(filedTtpeName, putMethod);
                }

                //通过反射执行
                putMethod.invoke(values, mPutMethodArgs);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mPutMethodArgs[0] = null;
                mPutMethodArgs[1] = null;

            }
        }
        return values;
    }

    //更新
    public int update(T obj, String whereClause, String... whereArgs) {
        ContentValues values =contentValuesByObj(obj);
        return mSQLiteDatabase.update(DaoUtil.getTableName(mClazz), values, whereClause, whereArgs);
    }

    //删除
    public int delete(String whereClause, String[] whereArgs) {
        return mSQLiteDatabase.delete(DaoUtil.getTableName(mClazz), whereClause, whereArgs);
    }
}
