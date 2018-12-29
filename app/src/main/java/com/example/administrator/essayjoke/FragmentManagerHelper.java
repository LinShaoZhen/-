package com.example.administrator.essayjoke;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * Created by nana on 2018/9/12.
 */

public class FragmentManagerHelper {
    private FragmentManager mFragmentManager;
    private int mContainerViewId;

    /**
     * 注解：@Nullable表示不能为空，@IdRes系统会确认此资源是否存在
     *
     * @param fragmentManager Fragment管理类
     * @param containerViewId 容器id
     */
    public FragmentManagerHelper(@Nullable FragmentManager fragmentManager, @IdRes int containerViewId) {
        this.mFragmentManager = fragmentManager;
        this.mContainerViewId = containerViewId;
    }

    /**
     * 添加Fragment的方法
     *
     * @param fragment
     */
    public void add(Fragment fragment) {
        //开启事务
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        //添加Fragment，第一个参数是容器id，第二个参数是需要添加的Fragment
        fragmentTransaction.add(mContainerViewId, fragment);
        //最后一定要提交
        fragmentTransaction.commit();
    }

    /**
     * 切换显示Fragment的方法
     *
     * @param fragment
     */
    public void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        //隐藏所有的Fragment
        List<Fragment> childFragments = mFragmentManager.getFragments();
        for (Fragment childFragment : childFragments) {
            fragmentTransaction.hide(childFragment);
        }
        //如果容器没有就添加，有的话直接显示
        if (!childFragments.contains(fragment)) {
            fragmentTransaction.add(mContainerViewId, fragment);
        } else {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commit();
    }
}
