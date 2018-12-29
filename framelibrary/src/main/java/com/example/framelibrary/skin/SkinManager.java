package com.example.framelibrary.skin;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import com.example.framelibrary.BaseSkinActivity;
import com.example.framelibrary.skin.attr.SkinView;
import com.example.framelibrary.skin.callback.ISkinChangeListener;
import com.example.framelibrary.skin.config.SkinConfig;
import com.example.framelibrary.skin.config.SkinPreUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by nana on 2018/9/3.
 * 皮肤的管理类
 */

public class SkinManager {
    private static SkinManager mInstance;
    private Context mContext;
    private Map<ISkinChangeListener, List<SkinView>> mSkinViews = new HashMap<>();
    private SkinResource mSkinResource;

    static {
        mInstance = new SkinManager();
    }

    public static SkinManager getInstance() {
        return mInstance;
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
        //每次打开应用都会到这里来，防止皮肤被任意删除，做一些措施
        String currentSkinPath = SkinPreUtils.getInstance(context).getSkinPath();
        File file = new File(currentSkinPath);
        if (!file.exists()) {
            //不存在，清空皮肤
            SkinPreUtils.getInstance(context).clearSkinInfo();
            return;
        }
        //判断能不能获取包名
        String packageName = context.getPackageManager().getPackageArchiveInfo(currentSkinPath, PackageManager.GET_ACTIVITIES).packageName;
        if (TextUtils.isEmpty(packageName)) {
            SkinPreUtils.getInstance(context).clearSkinInfo();
            return;
        }
        //最好校验签名 增量更新说
        //做一些初始化的工作
        mSkinResource = new SkinResource(mContext, currentSkinPath);
    }

    /**
     * 加载皮肤
     *
     * @param skinPath
     * @return
     */
    public int loadSkin(String skinPath) {
        //判断文件存不存在
        File file = new File(skinPath);
        if (!file.exists()) {
            //不存在，返回不存在信息
            return SkinConfig.SKIN_FILE_NOEXSIST;
        }
        //判断能不能获取包名
        String packageName = mContext.getPackageManager().getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES).packageName;
        if (TextUtils.isEmpty(packageName)) {
            return SkinConfig.SKIN_FILE_ERROR;
        }
        //当前皮肤如果一样不要换
        String currentSKinPath = SkinPreUtils.getInstance(mContext).getSkinPath();
        if (skinPath.equals(currentSKinPath)) {
            return SkinConfig.SKIN_CHANGE_NOTHING;
        }
        //初始化资源管理
        mSkinResource = new SkinResource(mContext, skinPath);
        //改变皮肤
        changeSkin();
        //保存皮肤状态
        saveSkinStatus(skinPath);
        return SkinConfig.SKIN_CHANGE_SUCCESS;
    }

    /**
     * 改变皮肤
     */
    private void changeSkin() {

        Set<ISkinChangeListener> keys = mSkinViews.keySet();
        for (ISkinChangeListener key : keys) {
            List<SkinView> skinViews = mSkinViews.get(key);
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }
            //通知Activity
            key.changeSkin(mSkinResource);
        }
    }

    private void saveSkinStatus(String skinPath) {
        SkinPreUtils.getInstance(mContext).saveSkinPath(skinPath);
    }

    /**
     * 恢复默认
     *
     * @return
     */
    public int restoreDefault() {
        //判断有没有皮肤，如果没有皮肤就不要执行任何方法
        String currentSkinPath = SkinPreUtils.getInstance(mContext).getSkinPath();
        if (TextUtils.isEmpty(currentSkinPath)) {
            return SkinConfig.SKIN_CHANGE_NOTHING;
        }
        //当前手机运行app的apk的路径
        String skinPath = mContext.getPackageResourcePath();
        mSkinResource = new SkinResource(mContext, skinPath);
        //改变皮肤
        changeSkin();
        //把皮肤信息清空
        SkinPreUtils.getInstance(mContext).clearSkinInfo();
        return SkinConfig.SKIN_CHANGE_SUCCESS;
    }

    /**
     * 通过activity获取skinview
     *
     * @param activity
     * @return
     */
    public List<SkinView> getSkinViews(Activity activity) {
        return mSkinViews.get(activity);
    }

    /**
     * 注册
     *
     * @param skinChangeListener
     * @param skinViews
     */
    public void register(ISkinChangeListener skinChangeListener, List<SkinView> skinViews) {
        mSkinViews.put(skinChangeListener, skinViews);
    }

    /**
     * 获取当前皮肤资源管理
     *
     * @return
     */
    public SkinResource getSkinResource() {
        return mSkinResource;
    }

    /**
     * 检测要不要换肤
     *
     * @param skinView
     */
    public void checkChangeSkin(SkinView skinView) {
        //如果当前有皮肤，也就是保存了皮肤路径，就换一下皮肤
        String currentSkinPath = SkinPreUtils.getInstance(mContext).getSkinPath();
        Log.e("lsz1", currentSkinPath);
        if (!TextUtils.isEmpty(currentSkinPath)) {
            //切换一下皮肤
            skinView.skin();
        }
    }

    /**防止内存泄漏
     * @param skinChangeListener
     */
    public void unregister(ISkinChangeListener skinChangeListener) {
        mSkinViews.remove(skinChangeListener);
    }
}
