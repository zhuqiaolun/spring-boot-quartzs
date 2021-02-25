package com.demon.springbootquartz.util;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Map;

/**
 * @author: Demon
 * @date 2021/2/25 17:08
 * @description: 分页列表数据
 */
public class ListUtil {

    public static JSONObject getData(Page<Map<String, Object>> listPage) {
        JSONObject responseBean = new JSONObject(true);
        responseBean.put("current", listPage.getCurrent());
        responseBean.put("size", listPage.getSize());
        responseBean.put("total", listPage.getTotal());
        responseBean.put("page", listPage.getPages());
        responseBean.put("data", listPage.getRecords());
        responseBean.put("hasPrevious", listPage.hasPrevious());
        responseBean.put("hasNext", listPage.hasNext());
        return responseBean;
    }
}
