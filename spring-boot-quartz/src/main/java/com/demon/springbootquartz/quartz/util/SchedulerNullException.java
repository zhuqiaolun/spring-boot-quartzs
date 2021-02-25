package com.demon.springbootquartz.quartz.util;

import com.demon.springbootquartz.support.ResponseBean;

/**
 * @author: Demon
 * @date 2021/2/23 17:37
 * @description: 定时任务异常
 */
public class SchedulerNullException extends RuntimeException  {

    private String code;

    public SchedulerNullException(ResponseBean responseBean) {
        super(responseBean.getErrMsg());
        this.code = responseBean.getErrCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
