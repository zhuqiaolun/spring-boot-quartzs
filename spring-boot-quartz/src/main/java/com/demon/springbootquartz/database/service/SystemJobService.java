package com.demon.springbootquartz.database.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demon.springbootquartz.database.entity.SystemJob;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 定时任务 服务类
 * </p>
 *
 * @author demon
 * @since 2021-02-24
 */
public interface SystemJobService extends IService<SystemJob> {

    /**
     * 查询列表信息(分页)
     * @param current 每页数
     * @param size 当前页
     * @param map 参数
     * @return 返回
     */
    Page<Map<String, Object>> getJobListPage(long current, long size, Map<String, Object> map);

    /**
     * 查询列表信息
     * @param map 参数
     * @return 返回
     */
    List<Map<String,Object>> getJobList(Map<String, Object> map);

    /**
     * 初始化 全部Job
     *
     * @throws Exception 异常
     */
    void initJob() throws Exception;

    /**
     * 通过任务索引获取Job内容
     *
     * @param jobIndex 索引
     * @return 返回
     * @throws Exception 异常
     */
    SystemJob getJobToIndex(String jobIndex) throws Exception;

    /**
     * 添加job
     *
     * @param systemJob 参数
     * @return 返回
     * @throws Exception 异常
     */
    boolean addJob(SystemJob systemJob) throws Exception;

    /**
     * 删除job
     *
     * @param jobIndex 索引
     * @throws Exception 异常
     */
    void deleteJob(String jobIndex) throws Exception;

    /**
     * 运行job
     *
     * @param jobIndex 索引
     * @throws Exception 异常
     */
    void resumeJob(String jobIndex) throws Exception;

    /**
     * 暂停job
     *
     * @param jobIndex 索引
     * @throws Exception 异常
     */
    void pauseJob(String jobIndex) throws Exception;

    /**
     * 运行一次job
     *
     * @param jobIndex 索引
     * @throws Exception 异常
     */
    void runOneJob(String jobIndex) throws Exception;

    /**
     * 清空所有JOB
     * @throws Exception 异常
     */
    void clearJob() throws Exception;

}
