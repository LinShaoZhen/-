package com.butterknife;

import android.support.annotation.UiThread;

/**
 * @author lsz
 * @time 2018/10/17 10:00
 * 作用：
 */
public interface Unbinder {
    @UiThread
    void unbind();

    Unbinder EMPTY = new Unbinder() {
        @Override
        public void unbind() {

        }
    };

}
