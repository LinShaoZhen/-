package com.butterknife;

import android.app.Activity;

import java.lang.reflect.Constructor;

/**
 * @author lsz
 * @time 2018/10/10 22:20
 * 作用：
 */
public class ButterKnife {
    public static Unbinder bind(Activity activity) {
        try {
            Class<? extends Unbinder> bindClassName = (Class<? extends Unbinder>) Class.forName(activity.getClass().getName() + "_ViewBinding");
            Constructor<? extends Unbinder> bindConstructor=bindClassName.getDeclaredConstructor(activity.getClass());
            bindConstructor.setAccessible(true);
            Unbinder unbinder=bindConstructor.newInstance(activity);
            return unbinder;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Unbinder.EMPTY;
    }
}
