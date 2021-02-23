package com.demon.task.hello;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * @author: Demon
 * @date 2021/2/23 18:34
 * @description: Hello执行类
 */
public class Hello {

    public void execute(Map<String, Object> dataMap) {
        System.out.println("Hello："+ JSON.toJSONString(dataMap));
    }

}
