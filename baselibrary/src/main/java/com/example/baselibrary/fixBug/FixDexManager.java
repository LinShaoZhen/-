package com.example.baselibrary.fixBug;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

/**
 * Created by nana on 2018/8/27.
 */

public class FixDexManager {
    private Context mContext;
    private File mDexDir;

    public FixDexManager(Context context) {
        this.mContext = context;
        //获取应用可以访问的dex目录
        this.mDexDir = context.getDir("odex", Context.MODE_PRIVATE);
    }

    /**
     * 修复dex包
     *
     * @param fixDexPath 本地dex包路径
     */
    public void fixDex(String fixDexPath) throws Exception {
        //2.获取下载好的补丁dexElement
        //2.1移动到系统能够访问的目录下
        File srcFile = new File(fixDexPath);
        if (!srcFile.exists()) {
            throw new FileNotFoundException(fixDexPath);
        }
        File destFile = new File(mDexDir, srcFile.getName());
        if (destFile.exists()) {
            Log.e("lsz", "tragetFile已经存在");
            return;
        }
        copyFile(srcFile, destFile);
        //2.2classLoader读取fixDex路径
        List<File> fixDexFiles = new ArrayList<>();
        fixDexFiles.add(destFile);
        fixDexFiles(fixDexFiles);
    }

    private void injectDexElement(ClassLoader classLoader, Object dexElements) throws Exception {
        //1.先获取pathList
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);

        //2.获取pathList里面的dexElements
        Field dexElementField = pathList.getClass().getDeclaredField("dexElements");
        dexElementField.setAccessible(true);

        dexElementField.set(pathList, dexElements);
    }

    /**
     * 从classLoader获取dexElement
     *
     * @param classLoader
     * @return
     */
    private Object getDexElementsByClassLoader(ClassLoader classLoader) throws Exception {
        //1.先获取pathList
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);

        //2.获取pathList里面的dexElements
        Field dexElementField = pathList.getClass().getDeclaredField("dexElements");
        dexElementField.setAccessible(true);
        return dexElementField.get(pathList);

    }

    public static void copyFile(File src, File dest) throws IOException {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            if (!dest.exists()) {
                dest.createNewFile();
            }
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(dest).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }

        }
    }

    private static Object combineArray(Object arrayLhs, Object arrayRhs) {
        Class<?> localClass = arrayLhs.getClass().getComponentType();
        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(localClass, j);
        for (int k = 0; k < j; k++) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }
        }
        return result;
    }

    /**
     * 加载全部的修复包
     */
    public void loadFixDex() throws Exception {
        File[] dexDiles = mDexDir.listFiles();
        List<File> fixDexFiles = new ArrayList<>();
        for (File dexDile : dexDiles) {
            if (dexDile.getName().endsWith(".dex")) {
                fixDexFiles.add(dexDile);
            }
        }
        fixDexFiles(fixDexFiles);
    }

    /**
     * 修复dex
     *
     * @param fixDexFiles
     */
    private void fixDexFiles(List<File> fixDexFiles) throws Exception {
        //1.先获取已经运行的程序DexElement
        PathClassLoader applicatonClassLoader = (PathClassLoader) mContext.getClassLoader();

        Object applicationDexElements = getDexElementsByClassLoader(applicatonClassLoader);


        File optimizedDirectory = new File(mDexDir, "odex");
        if (!optimizedDirectory.exists()) {
            optimizedDirectory.mkdirs();
        }
        for (File fixDexFile : fixDexFiles) {
            BaseDexClassLoader fixDexClassLoader = new BaseDexClassLoader(
                    fixDexFile.getAbsolutePath(),
                    optimizedDirectory,
                    null,
                    applicatonClassLoader
            );
            Object fixDexElements = getDexElementsByClassLoader(fixDexClassLoader);
            //3.把补丁的dexElement插到已经运行的dexElement的最前面
            applicationDexElements = combineArray(fixDexElements, applicationDexElements);
        }
        //注入到原来的applicationClassLoader中
        injectDexElement(applicatonClassLoader, applicationDexElements);

    }
}
