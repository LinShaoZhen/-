package com.butterknife;

import android.app.Activity;
import android.view.View;

/**
 * @author lsz
 * @time 2018/10/17 15:25
 * 作用：
 */
public class Utils {
    public static <T extends View> T findViewById(Activity activity,int viewId){
        return activity.findViewById(viewId);
    }
}
