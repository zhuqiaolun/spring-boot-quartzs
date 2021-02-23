package com.demon.springbootquartz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Demon
 * @date 2021/2/23 10:45
 * @description: WebMvc配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置模板资源路径
        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/templates/");
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/static/");
        registry.addResourceHandler("/public/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/public/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //swagger
        registry.addRedirectViewController("/swagger-ui.html", "/swagger-ui/index.html");
    }

    /**
     * 解决IE浏览器Get  @ResponseBody返回json的时候提示下载问题
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        MediaType media = new MediaType(MediaType.TEXT_HTML, Charset.forName("UTF-8"));
        supportedMediaTypes.add(media);
        jsonConverter.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(jsonConverter);
    }

}
