package com.demon.springbootquartz.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author: Demon
 * @date 2021/2/23 11:17
 * @description: 获取注册的bean 获取 ApplicationContext
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext appCtx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("初始化applicationContext："+applicationContext.getBeanDefinitionNames().length);
        appCtx = applicationContext;
    }

    /**
     * 通过name获取 Bean.
     * @param name bean名
     * @return 返回
     */
    public static Object getBean(String name) {
        return appCtx.getBean(name);
    }

    /**
     * 通过class获取Bean.
     * @param clazz bean类
     * @return 返回
     */
    public static <T> T getBean(Class<T> clazz) {
        return appCtx.getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     * @param name bean名
     * @param clazz bean类
     * @return 返回
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return appCtx.getBean(name, clazz);
    }

}
