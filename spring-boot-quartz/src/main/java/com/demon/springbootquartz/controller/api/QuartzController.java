package com.demon.springbootquartz.controller.api;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demon.springbootquartz.database.entity.SystemJob;
import com.demon.springbootquartz.database.service.SystemJobService;
import com.demon.springbootquartz.quartz.util.SchedulerNullException;
import com.demon.springbootquartz.support.BaseController;
import com.demon.springbootquartz.support.ResponseBean;
import com.demon.springbootquartz.util.ListUtil;
import com.demon.springbootquartz.util.MD5Util;
import com.demon.springbootquartz.util.RandomCodeUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 定时任务 前端控制器
 * </p>
 *
 * @author demon
 * @since 2020-11-05
 */
@RestController
@RequestMapping(value = "api/quartz")
public class QuartzController extends BaseController {

    @Resource
    private SystemJobService systemJobService;

    @PostMapping(value = "list")
    public @ResponseBody void list(HttpServletResponse response) {
        ResponseBean responseBean = new ResponseBean();
        try {
            Map<String, Object> map = new LinkedHashMap<>();
            List<Map<String, Object>> systemJobList = systemJobService.getJobList(map);
            responseBean.setData(systemJobList);
            responseBean.setMsg("定时任务列表成功");
            responseBean.setResult(true);
        }catch (Exception e){
            e.printStackTrace();
            responseBean.setResult(false);
            responseBean.setErrCode("00001");
            responseBean.setErrMsg("定时任务列表异常");
        }
        this.out(response, responseBean);
    }

    @PostMapping(value = "list/page")
    public @ResponseBody void listPage(@RequestBody JSONObject jsonParam, HttpServletResponse response) {
        ResponseBean responseBean = new ResponseBean();
        try {
            String[] params = {"current","size"};
            if(this.getParams(responseBean,jsonParam,params)){
                long current = Long.parseLong(jsonParam.getString("current"));
                long size = Long.parseLong(jsonParam.getString("size"));
                Map<String, Object> map = new LinkedHashMap<>();
                Page<Map<String, Object>> serviceJobListPage = systemJobService.getJobListPage(current,size,map);
                JSONObject jsonObject = ListUtil.getData(serviceJobListPage);
                responseBean.setData(jsonObject);
                responseBean.setMsg("定时任务分页列表成功");
                responseBean.setResult(true);
            }
        }catch (Exception e){
            e.printStackTrace();
            responseBean.setResult(false);
            responseBean.setErrCode("00002");
            responseBean.setErrMsg("定时任务分页列表异常");
        }
        this.out(response, responseBean);
    }

    @PostMapping(value = "addJob")
    public @ResponseBody void addJob(@RequestBody JSONObject jsonParam, HttpServletResponse response) {
        ResponseBean responseBean = new ResponseBean();
        try {
            Date date = new Date();
            String[] params = {"jobName","jobGroup","jobDesc","jobCron","jobStatus","jobData"};
            if(this.getParams(responseBean,jsonParam,params)){
                JSONObject jobData = jsonParam.getJSONObject("jobData");
                SystemJob systemJob = new SystemJob().setJobIndex(MD5Util.encode(RandomCodeUtil.code20()));
                systemJob.setJobName(jsonParam.getString("jobName")).setJobGroup(jsonParam.getString("jobGroup"));
                systemJob.setJobStatus(jsonParam.getBoolean("jobStatus")).setJobCron(jsonParam.getString("jobCron"));
                systemJob.setJobDescription(jsonParam.getString("jobDesc")).setJobData(jobData.toJSONString());
                systemJob.setCreateTime(date).setUpdateTime(date);
                if(systemJobService.addJob(systemJob)){
                    responseBean.setData(systemJob.getJobIndex());
                    responseBean.setMsg("新增定时任务成功");
                    responseBean.setResult(true);
                }else{
                    responseBean.setErrCode("40005");
                    responseBean.setErrMsg("定时任务已存在");
                    responseBean.setResult(false);
                }
            }
        }catch (SchedulerNullException e){
            responseBean.setResult(false);
            responseBean.setErrCode(e.getCode());
            responseBean.setErrMsg(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            responseBean.setResult(false);
            responseBean.setErrCode("00003");
            responseBean.setErrMsg("新增定时任务异常");
        }
        this.out(response, responseBean);
    }

    @PostMapping(value = "deleteJob")
    public @ResponseBody void deleteJob(@RequestBody JSONObject jsonParam, HttpServletResponse response) {
        ResponseBean responseBean = new ResponseBean();
        try {
            String[] params = {"jobIndex"};
            if(this.getParams(responseBean,jsonParam,params)){
                String jobIndex = jsonParam.getString("jobIndex");
                systemJobService.deleteJob(jobIndex);
                responseBean.setMsg("删除定时任务成功");
                responseBean.setResult(true);
            }
        }catch (SchedulerNullException e){
            responseBean.setResult(false);
            responseBean.setErrCode(e.getCode());
            responseBean.setErrMsg(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            responseBean.setResult(false);
            responseBean.setErrCode("00004");
            responseBean.setErrMsg("删除定时任务异常");
        }
        this.out(response, responseBean);
    }

    @PostMapping(value = "resumeJob")
    public @ResponseBody void resumeJob(@RequestBody JSONObject jsonParam, HttpServletResponse response) {
        ResponseBean responseBean = new ResponseBean();
        try {
            String[] params = {"jobIndex"};
            if(this.getParams(responseBean,jsonParam,params)){
                String jobIndex = jsonParam.getString("jobIndex");
                systemJobService.resumeJob(jobIndex);
                responseBean.setMsg("运行定时任务成功");
                responseBean.setResult(true);
            }
        }catch (SchedulerNullException e){
            responseBean.setResult(false);
            responseBean.setErrCode(e.getCode());
            responseBean.setErrMsg(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            responseBean.setResult(false);
            responseBean.setErrCode("00005");
            responseBean.setErrMsg("运行定时任务异常");
        }
        this.out(response, responseBean);
    }

    @PostMapping(value = "pauseJob")
    public @ResponseBody void pauseJob(@RequestBody JSONObject jsonParam, HttpServletResponse response) {
        ResponseBean responseBean = new ResponseBean();
        try {
            String[] params = {"jobIndex"};
            if(this.getParams(responseBean,jsonParam,params)){
                String jobIndex = jsonParam.getString("jobIndex");
                systemJobService.pauseJob(jobIndex);
                responseBean.setMsg("暂停定时任务成功");
                responseBean.setResult(true);
            }
        }catch (SchedulerNullException e){
            responseBean.setResult(false);
            responseBean.setErrCode(e.getCode());
            responseBean.setErrMsg(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            responseBean.setResult(false);
            responseBean.setErrCode("00006");
            responseBean.setErrMsg("暂停定时任务异常");
        }
        this.out(response, responseBean);
    }

    @PostMapping(value = "runOneJob")
    public @ResponseBody void runOneJob(@RequestBody JSONObject jsonParam, HttpServletResponse response) {
        ResponseBean responseBean = new ResponseBean();
        try {
            String[] params = {"jobIndex"};
            if(this.getParams(responseBean,jsonParam,params)){
                String jobIndex = jsonParam.getString("jobIndex");
                systemJobService.runOneJob(jobIndex);
                responseBean.setMsg("运行一次定时任务成功");
                responseBean.setResult(true);
            }
        }catch (SchedulerNullException e){
            responseBean.setResult(false);
            responseBean.setErrCode(e.getCode());
            responseBean.setErrMsg(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            responseBean.setResult(false);
            responseBean.setErrCode("00007");
            responseBean.setErrMsg("运行一次定时任务异常");
        }
        this.out(response, responseBean);
    }

    @PostMapping(value = "clearJob")
    public @ResponseBody void clearJob(HttpServletResponse response) {
        ResponseBean responseBean = new ResponseBean();
        try {
            systemJobService.clearJob();
            responseBean.setMsg("清空所有定时任务成功");
            responseBean.setResult(true);
        }catch (SchedulerNullException e){
            responseBean.setResult(false);
            responseBean.setErrCode(e.getCode());
            responseBean.setErrMsg(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            responseBean.setResult(false);
            responseBean.setErrCode("00008");
            responseBean.setErrMsg("清空所有定时任务异常");
        }
        this.out(response, responseBean);
    }

}

