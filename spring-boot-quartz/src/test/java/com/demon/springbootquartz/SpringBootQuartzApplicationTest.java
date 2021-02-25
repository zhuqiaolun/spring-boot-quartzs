package com.demon.springbootquartz;

import com.demon.springbootquartz.database.entity.SystemJob;
import com.demon.springbootquartz.database.service.SystemJobService;
import com.demon.springbootquartz.util.MD5Util;
import com.demon.springbootquartz.util.RandomCodeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author: Demon
 * @date 2021/2/24 16:55
 * @description: 测试类
 */
@SpringBootTest
class SpringBootQuartzApplicationTest {

    @Resource
    private SystemJobService systemJobService;

    @Test
    void contextLoads() {

    }

    @Test
    void 新增定时任务() {
        Date date = new Date();
        SystemJob systemJob = new SystemJob().setJobIndex(MD5Util.encode(RandomCodeUtil.code20()));
        systemJob.setJobName("任务名称1").setJobGroup("任务组名1").setJobStatus(false);
        systemJob.setJobCron("0/10 * * * * ?").setJobDescription("任务描述1").setJobData(null);
        systemJob.setCreateTime(date).setUpdateTime(date);
        try {
            systemJobService.addJob(systemJob);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void 删除定时任务() {
        try {
            String jobIndex = "562446d82079d2ffe71c36f508e26cd7";
            systemJobService.deleteJob(jobIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
