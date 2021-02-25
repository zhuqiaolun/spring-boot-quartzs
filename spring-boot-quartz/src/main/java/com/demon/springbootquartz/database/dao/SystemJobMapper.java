package com.demon.springbootquartz.database.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demon.springbootquartz.database.entity.SystemJob;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 定时任务 Mapper 接口
 * </p>
 *
 * @author demon
 * @since 2021-02-24
 */
@Mapper
public interface SystemJobMapper extends BaseMapper<SystemJob> {

    /**
     * 查询列表信息(分页)
     * @param page   分页标志
     * @param params 参数
     * @return 返回
     */
    Page<Map<String, Object>> getJobList(IPage<Map<String, Object>> page, @Param("params") Map<String, Object> params);

    /**
     * 查询列表信息
     * @param params 参数
     * @return 返回
     */
    List<Map<String,Object>> getJobList(@Param("params") Map<String,Object> params);

    /**
     * 通过任务索引获取Job内容
     *
     * @param jobIndex 索引
     * @return 返回
     */
    SystemJob getJobToIndex(@Param("jobIndex") String jobIndex);

    /**
     * 删除job
     * @param systemJob 参数
     * @return 返回
     */
    int deleteJob(SystemJob systemJob);

    /**
     * 删除job的运行状态
     * @param systemJob 参数
     * @return 返回
     */
    int updateJobStatus(SystemJob systemJob);

    /**
     * 删除job的初始状态
     * @param list 参数
     */
    void updateJobListToInit(@Param("list") List<SystemJob> list);

}
