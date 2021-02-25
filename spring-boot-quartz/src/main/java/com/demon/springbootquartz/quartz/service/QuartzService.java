package com.demon.springbootquartz.quartz.service;

import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;
import java.util.Map;

/**
 * @author: Demon
 * @date 2021/2/23 11:02
 * @description: 定时任务 接口
 */
public interface QuartzService {

    /**
     * 开启任务
     */
    void initStart();

    /**
     * 判断任务是否存在
     * @param jobName 参数
     * @param jobGroup 参数
     * @return 返回
     * @throws Exception 异常
     */
    boolean getJobExist(String jobName, String jobGroup) throws Exception;

    /**
     * 增加一个job
     *
     * @param jobClass
     *            任务实现类
     * @param jobName
     *            任务名称(建议唯一)
     * @param jobGroup
     *            任务组名
     * @param description
     *            任务描述
     * @param jobTime
     *            时间表达式 （如：0/5 * * * * ? ）
     * @param jobStatus
     *            任务 初始状态
     * @param jobData
     *            参数
     * @throws Exception
     *            异常
     * @return 返回
     */
    boolean addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroup, String description, String jobTime, boolean jobStatus, Map<String, Object> jobData) throws Exception;

    /**
     * 修改 一个job的 时间表达式
     *
     * @param jobName jobName
     * @param jobGroup jobGroup
     * @param jobTime jobTime
     * @throws Exception 异常
     */
    void updateJob(String jobName, String jobGroup, String jobTime) throws Exception;

    /**
     * 删除任务一个job
     *
     * @param jobName
     *            任务名称
     * @param jobGroup
     *            任务组名
     * @throws Exception 异常
     */
    void deleteJob(String jobName, String jobGroup) throws Exception;

    /**
     * 暂停一个job
     *
     * @param jobName jobName
     * @param jobGroup jobGroup
     * @throws Exception 异常
     */
    void pauseJob(String jobName, String jobGroup) throws Exception;

    /**
     * 恢复一个job
     *
     * @param jobName jobName
     * @param jobGroup jobGroup
     * @throws Exception 异常
     */
    void resumeJob(String jobName, String jobGroup) throws Exception;

    /**
     * 立即执行一个job(只一次)
     *
     * @param jobName  jobName
     * @param jobGroup jobGroup
     * @throws Exception 异常
     */
    void runOneJob(String jobName, String jobGroup) throws Exception;

    /**
     * 清空所有JOB
     * @throws Exception 异常
     */
    void clear() throws Exception;

    /**
     * 获取所有计划中的任务列表
     * @return 返回
     */
    List<Map<String, Object>> queryAllJob();

    /**
     * 获取所有计划中的任务列表状态
     */
    Map<String, Object> queryAllJobStatus();

    /**
     * 获取所有正在运行的job
     * @return 返回
     */
    List<Map<String, Object>> queryRunJob();


}
