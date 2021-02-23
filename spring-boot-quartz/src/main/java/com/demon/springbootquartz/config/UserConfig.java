package com.demon.springbootquartz.config;

import com.demon.springbootquartz.support.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author: Demon
 * @date 2021/2/23 11:25
 * @description: 用户配置
 */
@Data
@Component
@PropertySource(value = {"classpath:user.yml"}, factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "demon.user")
public class UserConfig {

    private boolean quartzInitStart;

}
