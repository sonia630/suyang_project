package com.o2o.massage.core.component;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * User: zhongli
 * Date: 2017/9/1 17:15
 * Description:
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext=null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    public static  <T> T getBean(Class<T> tClass){
        return applicationContext.getBean(tClass);
    }

    public static Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
}
