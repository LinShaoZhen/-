package com.example.baselibrary.ioc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018/08/08.
 */

public class ViewUtils {
    //针对Activity
    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    //针对自定义view
    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    //针对Fragment
    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }

    //兼容上面三个方法      Object -->反射需要执行的类
    public static void inject(ViewFinder finder, Object object) {
        //注入属性
        injectFiled(finder, object);
        //注入事件
        injectEvent(finder, object);
    }

    /**
     * 属性注入
     *
     * @param finder ViewFinder对象
     * @param object 需要注入属性的对象
     */
    private static void injectFiled(ViewFinder finder, Object object) {
        //1.获取所有属性
        Class<?> clazz = object.getClass();
        //获取所有属性包括私有和公有的
        Field[] fields = clazz.getDeclaredFields();
        //2.获取ViewById里面的Value值
        for (Field field : fields) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                //获取注解里面的id值  ——>R.id.test_tv
                int viewId = viewById.value();
                //3.findViewById找到View
                View view = finder.findVeiwById(viewId);
                if (view != null) {
                    //能注入所有修饰符 包括private,public
                    field.setAccessible(true);
                    //4.动态的注入找到的View
                    try {
                        field.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 注入事件
     *
     * @param finder VeiwFinder对象
     * @param object 需要注入事件的对象
     */
    private static void injectEvent(ViewFinder finder, Object object) {
        //1.获取类中的方法
        //1.1通过反射获取到该类
        Class<?> clazz = object.getClass();
        //2.获取类中所有的方法
        Method[] methods = clazz.getDeclaredMethods();
        //2.获取OnClick里面的Value值
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                int[] viewIds = onClick.value();
                for (int viewId : viewIds) {
                    //3.findViewById找到View
                    View view = finder.findVeiwById(viewId);

                    //拓展功能 检测网络
                    boolean isCheckNet=method.getAnnotation(CheckNet.class)!=null;
                    if (view != null) {
                        //4，View.setOnClickListener
                        view.setOnClickListener(new DeclaredOnClickListener(method, object,isCheckNet));
                    }
                }
            }
        }
    }

    private static class DeclaredOnClickListener implements View.OnClickListener {
        private Object mObject;
        private Method mMethod;
        private boolean mIsCheckNet;

        public DeclaredOnClickListener(Method method, Object object,boolean isCheckNet) {
            this.mObject = object;
            this.mMethod = method;
            this.mIsCheckNet=isCheckNet;

        }

        @Override
        public void onClick(View view) {
            //需不需要检测网络
            if (mIsCheckNet){
                if (!networkAvailable(view.getContext())){
                    Toast.makeText(view.getContext(), "亲，您的网络不太给力", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            try {
                //所有方法都可以，包括私有和公有方法
                mMethod.setAccessible(true);
                //5.反射执行方法
                mMethod.invoke(mObject, view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean networkAvailable(Context context){
        //得到连接对象
        try {


        ConnectivityManager connectivityManager= (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo=connectivityManager.
                getActiveNetworkInfo();
        if (activeNetworkInfo!=null&&activeNetworkInfo.isConnected()){
            return true;
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
