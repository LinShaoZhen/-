package com.example.framelibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nana on 2018/8/25.
 * 单例的设计模式的异常捕捉
 */

public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {
    private static ExceptionCrashHandler mInstance;
    private String TAG = "ExceptionCrashHandler";
    //获取系统默认的处理
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    public static ExceptionCrashHandler getInstance() {
        if (mInstance == null) {
            synchronized (ExceptionCrashHandler.class) {
                if (mInstance == null) {
                    mInstance = new ExceptionCrashHandler();
                }
            }
        }
        return mInstance;
    }

    private Context mContext;

    public void init(Context context) {
        this.mContext = context;
        //设置全局的异常类为本类
        Thread.currentThread().setUncaughtExceptionHandler(this);
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG, "报异常了");
        //崩溃的详细信息
        String crashFileName = saveInfoToSD(ex);
        Log.e(TAG, "fileName-->" + crashFileName);
        //缓存崩溃日志文件
        cacheCrashFile(crashFileName);
        //应用信息 包名 版本号

        //手机信息

        //让系统默认处理
        mDefaultExceptionHandler.uncaughtException(thread, ex);
    }

    /**
     * 缓存奔溃日志文件
     * @param fileName
     */
    private void cacheCrashFile(String fileName) {
        SharedPreferences sp=mContext.getSharedPreferences("crash",Context.MODE_PRIVATE);
        sp.edit().putString("CRASH_FILE_NAME",fileName).commit();
    }

    public File getCrashFile(){
        String crashFileName =mContext.getSharedPreferences("crash",Context.MODE_PRIVATE).getString("CRASH_FILE_NAME","");
        return new File(crashFileName);
    }

    /**
     * 保存获取的软件信息，设备信息和出错信息保存在SDcard中
     *
     * @param ex
     * @return
     */
    private String saveInfoToSD(Throwable ex) {
        String fileName = null;
        StringBuffer sb = new StringBuffer();
            //保存手机信息+应用信息--》obtainSimpleInfo
        for (Map.Entry<String, String> entry : obtainSimpleInfo(mContext).entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }
        //保存崩溃的详细信息
        sb.append(obtainExceptionInfo(ex));
        //保存文件 手机应用的目录
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File dir = new File(mContext.getFilesDir() + File.separator + "crash" + File.separator);
            //先删除之前的异常信息
            if (dir.exists()) {
                //删除该目录下的所有子文件
                deleteDir(dir);
            }
            //再重新创建文件夹
            if (!dir.exists()) {
                dir.mkdir();
            }
            try {
                fileName = dir.toString() + File.separator + getAssignTime("yyyy_MM_dd HH_mm") + ".txt";
                FileOutputStream fos = new FileOutputStream(fileName);
                fos.write(sb.toString().getBytes());
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    private String getAssignTime(String dateFormStr) {
        DateFormat dateFormat = new SimpleDateFormat(dateFormStr);
        long currentTime = System.currentTimeMillis();
        return dateFormat.format(currentTime);
    }

    private HashMap<String, String> obtainSimpleInfo(Context context) {
        HashMap<String, String> map = new HashMap<>();
        PackageManager mPackageManager = context.getPackageManager();
        PackageInfo mPackageInfo = null;
        try {
            mPackageInfo = mPackageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        map.put("versionName", mPackageInfo.versionName);
        map.put("versionCode", mPackageInfo.versionCode + "");
        map.put("MODEL", Build.MODEL + "");
        map.put("SDK_INT", Build.VERSION.SDK_INT + "");
        map.put("PRODUCT", Build.PRODUCT + "");
        map.put("MOBLE_INFO", getMobileInfo());
        return map;
    }

    /**
     * 获取手机的信息
     * @return
     */
    private static String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        try {
            //利用反射获取Build的所有属性
            Field[] files = Build.class.getDeclaredFields();
            for (Field field : files) {
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name + "=" + value);
                sb.append("\n");
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获取系统为捕捉到的错误信息
     */
    private String obtainExceptionInfo(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     */
    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            //递归删除目录中的字目录
           for (File child:children){
               child.delete();
           }
        }
        //目录此时为空可以删除
        return true;
    }


}
