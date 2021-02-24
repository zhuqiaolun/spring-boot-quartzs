package com.demon.springbootquartz.quartz;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

/**
 * @author: Demon
 * @date 2021/2/23 11:12
 * @description: QuartzJob触发时间，并发执行
 *
 * 添加：@DisallowConcurrentExecution：告诉Quartz不要并发地执行同一个JobDetail实例。
 * 如果你使用了@PersistJobDataAfterExecution注解，则强烈建议你同时使用@DisallowConcurrentExecution注解，
 * 因为当同一个job（JobDetail）的两个实例被并发执行时，由于竞争，JobDataMap中存储的数据很可能是不确定的。
 */
@Slf4j
public class MyQuartzJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try{
            Date date = new Date();
            System.out.println("定时任务执行时间："+ DateFormatUtils.format(date,"yyyy-MM-dd HH:mm:ss"));
            // 获取参数
            JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
            //反射 - 执行类
            Class<?> cls = Class.forName(jobDataMap.getString("classPath"));
            Method execute = cls.getMethod("execute", Map.class);
            execute.invoke(cls.newInstance(), jobDataMap.getWrappedMap());
        }catch (Exception e){
            //捕获异常后的处理，本示例为抛出异常
            throw new JobExecutionException(e);
        }
    }

}
