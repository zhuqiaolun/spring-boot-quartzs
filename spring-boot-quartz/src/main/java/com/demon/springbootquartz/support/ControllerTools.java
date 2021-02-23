package com.demon.springbootquartz.support;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author: Demon
 * @date 2021/2/23 11:17
 * @description:  控制层工具
 */
@Slf4j
public class ControllerTools {

    /**
     * 判断是否 ajax 请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    /**
     * ajax 输出
     * 使用response输出JSON
     * @param response response
     * @param str 响应对应
     */
    public static void out(HttpServletResponse response, Object str) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.print(str);
        } catch (Exception e) {
            log.error("输出JSON出错",e.getMessage());
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     *  获取 requestBody 参数
     */
    public static JSONObject getJsonParam(HttpServletRequest request) throws JSONException {
        try {
            // 获取输入流
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
            // 写入数据到StringBuilder
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = streamReader.readLine()) != null) {
                sb.append(line);
            }
            return JSONObject.parseObject(sb.toString());
        } catch (Exception e) {
            throw new JSONException("错误参数",e);
        }
    }


}
