package com.demon.springbootquartz.quartz.aop;

import com.demon.springbootquartz.quartz.service.QuartzService;
import com.demon.springbootquartz.support.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
     * 环绕
     * 在切入点前后切入内容，并自己控制何时执行切入点自身的内容
     * @param proceedingJoinPoint 参数
     * @return 返回
     * @throws Throwable 异常
     */
    @Around("getJobExist()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature)proceedingJoinPoint.getSignature();
        //判断是否有注解
        ServiceMonitor serviceMonitor = methodSignature.getMethod().getAnnotation(ServiceMonitor.class);
        if(serviceMonitor != null){
            Object[] args = proceedingJoinPoint.getArgs();
            String jobName = String.valueOf(args[serviceMonitor.jobName()]);
            String jobGroup = String.valueOf(args[serviceMonitor.jobGroup()]);
            if(quartzService.getJobExist(jobName,jobGroup)){
                return proceedingJoinPoint.proceed();
            }else{
                ResponseBean responseBean = new ResponseBean();
                responseBean.setResult(false);
                responseBean.setErrMsg("["+jobName+"-"+jobGroup+"]任务不存在");
                responseBean.setErrCode("40004");
                System.out.println(responseBean.toString());
                return responseBean;
            }
        }else {
            return proceedingJoinPoint.proceed();
        }
    }
}
