package com.example.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2018/08/11.
 * Veiw事件注解的Annotation
 */

//@Target(ElementType.FIELD)代表Annotation的位置，FIELD只能放在属性上 METHOD只能放在方法上 TYPE只能放在类上 CONSTRUCTOR只能放在构造函数上
@Target(ElementType.METHOD)
//@Retention(RetentionPolicy.CLASS)是指什么时候生效 CLASS编译时 RUNTIME运行时生效 SOURCE源码资源
@Retention(RetentionPolicy.RUNTIME)
public @interface OnClick {
    int[] value();
}
