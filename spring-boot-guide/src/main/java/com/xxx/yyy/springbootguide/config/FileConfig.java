package com.xxx.yyy.springbootguide.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author: maoyan
 * @date: 2020/6/23 10:44:13
 * @description:
 */
@Log4j2
@Configuration
public class FileConfig implements WebMvcConfigurer {

    @Value("${file.path}")
    private String path;

    @Value("${file.address}")
    private String address;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //取得在服务器中的绝对路径
        String filePath = new File(path).getAbsolutePath() + File.separator;

        log.info("Mapping " + address +" from " + filePath);

        /**
         * 资源映射路径
         * addResourceHandler：访问映射路径
         * addResourceLocations：资源绝对路径
         */
        registry.addResourceHandler(address)
            // Spring Boot需要增加file协议前缀
            .addResourceLocations("file:" + filePath)
            // 设置浏览器缓存30分钟
            .setCacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES));
    }
}
