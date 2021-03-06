package com.demon.springbootquartz.config;

import com.demon.springbootquartz.database.service.SystemJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: Demon
 * @date 2021/2/23 13:02
 * @description: 程序启动完成后立即执行
 */
@Slf4j
@Component
public class AfterApplicationStarted implements ApplicationRunner {

    @Resource
    private UserConfig userConfig;

    @Resource
    private SystemJobService systemJobService;

    @Override
    public void run(ApplicationArguments args) {
        try {
            if(userConfig.isQuartzInit()){
                systemJobService.initJob();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}