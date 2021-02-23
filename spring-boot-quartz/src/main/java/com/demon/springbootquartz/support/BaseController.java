package com.demon.springbootquartz.support;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: Demon
 * @date 2021/2/23 11:18
 * @description: 控制层 基本
 */
@Slf4j
public abstract class BaseController {

    /**
     * 使用response输出JSON
     *
     * @param response response
     * @param str      响应对应
     */
    protected void out(HttpServletResponse response, Object str) {
        ControllerTools.out(response, str);
    }

    /**
     * 判断参数 是否存在
     */
    protected boolean getParams(ResponseBean responseBean, JSONObject jsonParam, String[] params) {
        boolean flag = false;
        for (String param : params) {
            if (jsonParam.containsKey(param)) {
                if (StringUtils.isNotBlank(String.valueOf(jsonParam.get(param)))) {
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            } else {
                flag = false;
                break;
            }
        }
        if (!flag) {
            responseBean.setResult(false);
            responseBean.setErrMsg("缺少参数&参数不能为空");
            responseBean.setErrCode("40003");
        }
        return flag;
    }

}
