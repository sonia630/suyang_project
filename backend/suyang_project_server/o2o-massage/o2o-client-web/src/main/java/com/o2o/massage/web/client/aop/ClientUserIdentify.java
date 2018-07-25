package com.o2o.massage.web.client.aop;

import java.lang.annotation.*;

/**
 * 用户身份认证：如果登陆则获取LoginInfo 如果未登录则根据 mustLogin 判断是否抛出异常

 */
@Retention(RetentionPolicy.RUNTIME) // 注解会在class中存在，运行时可通过反射获取
@Target(ElementType.METHOD) // 目标是方法
@Documented
@Inherited
public @interface ClientUserIdentify {
    /**
     * 是否必须登陆 默认不需要一定登陆
     * 
     * @return
     */
    boolean mustLogin() default false;

    /**
     *
     * @return
     */
    EnumRequiredUserType requiredClientUserType() default EnumRequiredUserType.EITHER;
}
