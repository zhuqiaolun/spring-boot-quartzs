package com.demon.springbootquartz.database.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demon.springbootquartz.database.dao.SystemJobMapper;
import com.demon.springbootquartz.database.entity.SystemJob;
import com.demon.springbootquartz.database.service.SystemJobService;
import com.demon.springbootquartz.quartz.MyQuartzJob;
import com.demon.springbootquartz.quartz.service.QuartzService;
import com.demon.springbootquartz.quartz.util.SchedulerNullException;
import com.demon.springbootquartz.support.ResponseBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 定时任务 服务实现类
 * </p>
 *
 * @author demon
 * @since 2021-02-24
 */
@Service
public class SystemJobServiceImpl extends ServiceImpl<SystemJobMapper, SystemJob> implements SystemJobService {

    @Resource
    private QuartzService quartzService;


    @Override
    public Page<Map<String, Object>> getJobListPage(long current, long size, Map<String, Object> map) {
        IPage<Map<String, Object>> page = new Page<>(current, size);
        return this.baseMapper.getJobList(page, map);
    }

    @Override
    public List<Map<String,Object>>  getJobList(Map<String,Object> map) {
        return this.baseMapper.getJobList(map);
    }

    @Override
    public void initJob() throws Exception {
        List<SystemJob> systemJobList = this.list();
        System.out.println("初始化定时任务总数：" + systemJobList.size());
        quartzService.initStart();
        if (systemJobList.size() > 0) {
            List<SystemJob> jobList = new ArrayList<>();
            for (SystemJob systemJob : systemJobList) {
                systemJob.setJobInitStatus(addInitJob(systemJob));
                jobList.add(systemJob);
            }
            this.baseMapper.updateJobListToInit(jobList);
        }
    }

    @Override
    public SystemJob getJobToIndex(String jobIndex) {
        return this.baseMapper.getJobToIndex(jobIndex);
    }

    @Override
    public boolean addJob(SystemJob systemJob) throws Exception {
        if(addInitJob(systemJob)){
            try{
                this.baseMapper.insert(systemJob);
                return true;
            }catch (Exception e){
                quartzService.deleteJob(systemJob.getJobIndex(), systemJob.getJobIndex());
            }
        }
        return false;
    }



    @Override
    public void deleteJob(String jobIndex) throws Exception {
        executeJob("deleteJob", jobIndex);
    }

    @Override
    public void resumeJob(String jobIndex) throws Exception {
        executeJob("resumeJob", jobIndex);
    }

    @Override
    public void pauseJob(String jobIndex) throws Exception {
        executeJob("pauseJob", jobIndex);
    }

    @Override
    public void runOneJob(String jobIndex) throws Exception {
        executeJob("runOneJob", jobIndex);
    }

    @Override
    public void clearJob() throws Exception {
        quartzService.clear();
        this.baseMapper.delete(null);
    }

    private void executeJob(String type, String jobIndex) throws Exception {
        Date date = new Date();
        SystemJob systemJob = this.getJobToIndex(jobIndex);
        if (systemJob != null) {
            switch (type) {
                case "deleteJob":
                    this.baseMapper.deleteJob(systemJob);
                    quartzService.deleteJob(jobIndex, jobIndex);
                    break;
                case "resumeJob":
                    systemJob.setJobStatus(true).setUpdateTime(date);
                    this.baseMapper.updateJobStatus(systemJob);
                    quartzService.resumeJob(jobIndex, jobIndex);
                    break;
                case "pauseJob":
                    systemJob.setJobStatus(false).setUpdateTime(date);
                    this.baseMapper.updateJobStatus(systemJob);
                    quartzService.pauseJob(jobIndex, jobIndex);
                    break;
                case "runOneJob":
                    quartzService.runOneJob(jobIndex, jobIndex);
                    break;
                default:
                    System.out.println("未知执行");
                    break;
            }
        } else {
            ResponseBean responseBean = new ResponseBean().setResult(false);
            responseBean.setErrCode("40004");
            responseBean.setErrMsg("任务[" + jobIndex + "]不存在");
            throw new SchedulerNullException(responseBean);
        }
    }


    private boolean addInitJob(SystemJob systemJob) throws Exception {
        String jobName = systemJob.getJobIndex();
        String jobGroup = systemJob.getJobIndex();
        String description = systemJob.getJobDescription();
        String jobTime = systemJob.getJobCron();
        boolean jobStatus = systemJob.isJobStatus();
        Map<String, Object> jobData = JSON.parseObject(systemJob.getJobData());
        boolean addJobStatus = quartzService.addJob(MyQuartzJob.class, jobName, jobGroup, description, jobTime, jobStatus, jobData);
        System.out.println("定时任务[" + jobName + "]：" + addJobStatus);
        return addJobStatus;
    }

}
