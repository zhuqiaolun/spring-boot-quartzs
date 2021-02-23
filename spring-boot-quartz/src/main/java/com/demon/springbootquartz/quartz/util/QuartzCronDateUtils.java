package com.demon.springbootquartz.quartz.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Demon
 * @date 2021/2/23 11:02
 * @description: cron工具类，将date 转换成cron格式
 */
public class QuartzCronDateUtils {

    /***
     *  日期转换cron表达式时间格式
     * @param date 时间
     * @param dateFormat 格式
     * @return 返回
     */
    private static String formatDateByPattern(Date date,String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }
    /***
     * convert Date to cron
     * @param date:时间
     * @return 返回
     */
    public static String getCron(Date date){
        String dateFormat="ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date,dateFormat);
    }


}
