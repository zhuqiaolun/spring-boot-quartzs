package com.demon.springbootquartz.quartz.service.impl;

import com.demon.springbootquartz.quartz.aop.ServiceMonitor;
import com.demon.springbootquartz.quartz.service.QuartzService;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author: Demon
 * @date 2021/2/23 11:03
 * @description: 定时任务实现类
 */
@Service
public class QuartzServiceImpl implements QuartzService {

    @Resource
    private Scheduler scheduler;

    @Override
    public boolean getJobExist(String jobName, String jobGroup) throws Exception {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            return scheduler.getTriggersOfJob(jobKey).size() > 0;
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public boolean addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroup, String description, String jobTime, boolean jobStatus, Map<String, Object> jobData) throws Exception {
        if(this.getJobExist(jobName,jobGroup)){
            return false;
        }else {
            try {
                // 创建jobDetail实例，绑定Job实现类
                // 指明job的名称，所在组的名称，以及绑定job类
                // 任务名称和组构成任务key
                JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).withDescription(description).build();
                // 设置job参数
                if (jobData != null && jobData.size() > 0) {
                    jobDetail.getJobDataMap().putAll(jobData);
                }
                // 定义调度触发规则
                TriggerBuilder<CronTrigger> cronTriggerTriggerBuilder = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup)
                        .withSchedule(CronScheduleBuilder.cronSchedule(jobTime))
                        .withDescription(description).usingJobData(jobDetail.getJobDataMap());
                Trigger trigger = cronTriggerTriggerBuilder.build();

                // 把作业和触发器注册到任务调度中
                scheduler.scheduleJob(jobDetail, trigger);
                if (jobStatus) {
                    resumeJob(jobName, jobGroup);
                } else {
                    pauseJob(jobName, jobGroup);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e);
            }
            return true;
        }

    }

    /**
     * 修改 一个job的 时间表达式
     *
     * @param jobName  jobName
     * @param jobGroup jobGroup
     * @param jobTime  jobTime
     */
    @Override
    @ServiceMonitor(jobName = 0,jobGroup = 1)
    public void updateJob(String jobName, String jobGroup, String jobTime) throws Exception {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            if(scheduler.getTriggersOfJob(jobKey).size() > 0){
                TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
                CronTrigger trigger = ((CronTrigger) scheduler.getTrigger(triggerKey))
                        .getTriggerBuilder().withIdentity(triggerKey)
                        .withSchedule(CronScheduleBuilder.cronSchedule(jobTime)).build();
                // 重启触发器
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 删除任务一个job
     *
     * @param jobName  任务名称
     * @param jobGroup 任务组名
     */
    @Override
    @ServiceMonitor(jobName = 0,jobGroup = 1)
    public void deleteJob(String jobName, String jobGroup) throws Exception {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            if(scheduler.getTriggersOfJob(jobKey).size() > 0){
                TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
                // 停止触发器
                scheduler.pauseTrigger(triggerKey);
                // 移除触发器
                scheduler.unscheduleJob(triggerKey);
                // 删除任务
                scheduler.deleteJob(jobKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 暂停一个job
     *
     * @param jobName  jobName
     * @param jobGroup jobGroup
     */
    @Override
    @ServiceMonitor(jobName = 0,jobGroup = 1)
    public void pauseJob(String jobName, String jobGroup) throws Exception {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            if(scheduler.getTriggersOfJob(jobKey).size() > 0){
                scheduler.pauseJob(jobKey);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 恢复一个job
     *
     * @param jobName  jobName
     * @param jobGroup jobGroup
     */
    @Override
    @ServiceMonitor(jobName = 0,jobGroup = 1)
    public void resumeJob(String jobName, String jobGroup) throws Exception {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            if(scheduler.getTriggersOfJob(jobKey).size() > 0){
                scheduler.resumeJob(jobKey);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 立即执行一个job
     *
     * @param jobName  jobName
     * @param jobGroup jobGroup
     */
    @Override
    @ServiceMonitor(jobName = 0,jobGroup = 1)
    public void runJobOne(String jobName, String jobGroup) throws Exception {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
            if(scheduler.getTriggersOfJob(jobKey).size() > 0){
                scheduler.triggerJob(jobKey);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * 获取所有计划中的任务列表
     */
    @Override
    public List<Map<String, Object>> queryAllJob() {
        List<Map<String, Object>> jobList = new ArrayList<>();
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Map<String, Object> map = new LinkedHashMap<>();
                    getMap(map, jobKey, trigger);
                    jobList.add(map);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }

    /**
     * 获取所有计划中的任务列表状态
     */
    @Override
    public Map<String, Object> queryAllJobStatus() {
        Map<String, Object> map = new LinkedHashMap<>();
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    map.put(jobKey.getName() + "#" + jobKey.getGroup(), scheduler.getTriggerState(trigger.getKey()).name());
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取所有正在运行的job
     */
    @Override
    public List<Map<String, Object>> queryRunJob() {
        List<Map<String, Object>> jobList = new ArrayList<>();
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            for (JobExecutionContext executingJob : executingJobs) {
                Map<String, Object> map = new LinkedHashMap<>();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                getMap(map, jobKey, trigger);
                jobList.add(map);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }

    /**
     * 构建返回参数
     *
     * @param map     map
     * @param jobKey  jobKey
     * @param trigger trigger
     * @throws SchedulerException 异常
     */
    private void getMap(Map<String, Object> map, JobKey jobKey, Trigger trigger) throws SchedulerException {
        map.put("jobName", jobKey.getName());
        map.put("jobGroup", jobKey.getGroup());
        map.put("jobDesc", trigger.getDescription());
        map.put("nextFireTime", trigger.getNextFireTime());
        map.put("previousFireTime", trigger.getPreviousFireTime());
        map.put("jobStatus", scheduler.getTriggerState(trigger.getKey()).name());
        if (trigger instanceof CronTrigger) {
            CronTrigger cronTrigger = (CronTrigger) trigger;
            String cronExpression = cronTrigger.getCronExpression();
            map.put("jobTime", cronExpression);
        }
    }

}
