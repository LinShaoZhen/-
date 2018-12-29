package com.example.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2018/08/11.
 */
@Target(ElementType.METHOD)//只能使用在方法上
@Retention(RetentionPolicy.RUNTIME)//在运行时生效
public @interface CheckNet {
}
