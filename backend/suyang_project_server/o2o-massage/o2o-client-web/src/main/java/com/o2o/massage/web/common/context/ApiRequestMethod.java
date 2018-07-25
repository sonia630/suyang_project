package com.o2o.massage.web.common.context;

import com.o2o.massage.core.common.EnumApiRequestSide;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)//注解会在class中存在，运行时可通过反射获取
@Target(ElementType.METHOD)//目标是方法
@Documented
public @interface ApiRequestMethod {
    /**
     *
     * @return
     */
    EnumApiRequestSide requestSide() default EnumApiRequestSide.CLIENT;
}
