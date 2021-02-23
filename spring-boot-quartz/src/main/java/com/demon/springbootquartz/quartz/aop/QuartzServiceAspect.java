package com.demon.springbootquartz.quartz.aop;

import com.demon.springbootquartz.quartz.util.SchedulerNullException;
import com.demon.springbootquartz.quartz.service.QuartzService;
import com.demon.springbootquartz.support.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: Demon
 * @date 2021/2/23 16:35
 * @description: 切面定义
 */
@Slf4j
@Aspect
@Component
public class QuartzServiceAspect {

    @Resource
    private QuartzService quartzService;

    @Pointcut("@annotation(com.demon.springbootquartz.quartz.aop.ServiceMonitor)")
    public void getJobExist(){}

    /**
     * 可以执行切面点之前的操作
     */
    @Before("getJobExist()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        //判断是否有注解
        ServiceMonitor serviceMonitor = methodSignature.getMethod().getAnnotation(ServiceMonitor.class);
        if(serviceMonitor != null){
            Object[] args = joinPoint.getArgs();
            String jobName = String.valueOf(args[serviceMonitor.jobName()]);
            String jobGroup = String.valueOf(args[serviceMonitor.jobGroup()]);
            if(!quartzService.getJobExist(jobName,jobGroup)){
                ResponseBean responseBean = new ResponseBean();
                responseBean.setResult(false);
                responseBean.setErrMsg("任务不存在");
                responseBean.setErrCode("40004");
                throw new SchedulerNullException(responseBean.getErrCode(),responseBean.getErrMsg());
            }
        }
    }

}
