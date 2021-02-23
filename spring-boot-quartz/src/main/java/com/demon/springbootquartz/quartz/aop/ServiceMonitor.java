package com.demon.springbootquartz.quartz.aop;

import java.lang.annotation.*;

/**
 * @author: Demon
 * @date 2021/2/23 16:54
 * @description:  Service监控注解
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceMonitor {

    int jobName() ;
    int jobGroup() ;

}
