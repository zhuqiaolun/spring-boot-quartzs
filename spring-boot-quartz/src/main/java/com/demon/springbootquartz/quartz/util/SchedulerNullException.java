package com.demon.springbootquartz.quartz.util;

/**
 * @author: Demon
 * @date 2021/2/23 17:37
 * @description: 定时任务异常
 */
public class SchedulerNullException extends RuntimeException  {

    private String code;

    public SchedulerNullException(String code,String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
