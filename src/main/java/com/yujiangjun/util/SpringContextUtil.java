package com.yujiangjun.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        // TODO Auto-generated method stub
        context = applicationContext;
    }
    //获得applicationContext
    public static ApplicationContext getApplicationContext() {
        //assertContextInjected();
        return context;
    }
    public static void clearHolder(){
        context=null;
    }
    //获取Bean
    public static <T> T getBean(Class<T> requiredType){
        //assertContextInjected();
        return (T) getApplicationContext().getBean(requiredType);
    }
    public static <T> T getBean(Class<T> requiredType,String beanName){
        //assertContextInjected();
        return (T) getApplicationContext().getBean(beanName,requiredType);
    }
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name){
        assertContextInjected();
        return (T) getApplicationContext().getBean(name);
    }
    //判断application是否为空
    public static void assertContextInjected(){
        Assert.isTrue(context==null, "application未注入 ，请在springContext.xml中注入SpringHolder!");
    }
}
