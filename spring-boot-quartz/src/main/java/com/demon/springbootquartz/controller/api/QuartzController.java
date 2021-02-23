package com.demon.springbootquartz.controller.api;


import com.alibaba.fastjson.JSONObject;
import com.demon.springbootquartz.quartz.util.SchedulerNullException;
import com.demon.springbootquartz.quartz.MyQuartzJob;
import com.demon.springbootquartz.quartz.service.QuartzService;
import com.demon.springbootquartz.support.BaseController;
import com.demon.springbootquartz.support.ResponseBean;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
    private QuartzService quartzService;

    @PostMapping(value = "list")
    public @ResponseBody void list(HttpServletResponse response) {
        ResponseBean responseBean = new ResponseBean();
        try {
            List<Map<String, Object>> queryAllJob = quartzService.queryAllJob();
            responseBean.setData(queryAllJob);
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

    @PostMapping(value = "addJob")
    public @ResponseBody void addJob(@RequestBody JSONObject jsonParam, HttpServletResponse response) {
        ResponseBean responseBean = new ResponseBean();
        try {
            String[] params = {"jobName","jobGroup","jobDesc","jobCron","jobStatus","jobData"};
            if(this.getParams(responseBean,jsonParam,params)){
                String jobName = jsonParam.getString("jobName");
                String jobGroup = jsonParam.getString("jobGroup");
                String jobDesc = jsonParam.getString("jobDesc");
                String jobCron = jsonParam.getString("jobCron");
                Boolean jobStatus = jsonParam.getBoolean("jobStatus");
                Map<String, Object> jobData = jsonParam.getJSONObject("jobData");
                if(quartzService.addJob(MyQuartzJob.class,jobName,jobGroup,jobDesc,jobCron, jobStatus,jobData)){
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
            String[] params = {"jobName","jobGroup"};
            if(this.getParams(responseBean,jsonParam,params)){
                String jobName = jsonParam.getString("jobName");
                String jobGroup = jsonParam.getString("jobGroup");
                quartzService.deleteJob(jobName,jobGroup);
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
            String[] params = {"jobName","jobGroup"};
            if(this.getParams(responseBean,jsonParam,params)){
                String jobName = jsonParam.getString("jobName");
                String jobGroup = jsonParam.getString("jobGroup");
                quartzService.resumeJob(jobName,jobGroup);
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
            String[] params = {"jobName","jobGroup"};
            if(this.getParams(responseBean,jsonParam,params)){
                String jobName = jsonParam.getString("jobName");
                String jobGroup = jsonParam.getString("jobGroup");
                quartzService.pauseJob(jobName,jobGroup);
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

    @PostMapping(value = "runJobOne")
    public @ResponseBody void runJobOne(@RequestBody JSONObject jsonParam, HttpServletResponse response) {
        ResponseBean responseBean = new ResponseBean();
        try {
            String[] params = {"jobName","jobGroup"};
            if(this.getParams(responseBean,jsonParam,params)){
                String jobName = jsonParam.getString("jobName");
                String jobGroup = jsonParam.getString("jobGroup");
                quartzService.runJobOne(jobName,jobGroup);
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

}

