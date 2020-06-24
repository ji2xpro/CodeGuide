package com.xxx.yyy.springbootguide.config;

import com.xxx.yyy.springbootguide.interceptor.CacheKeyGenerator;
import com.xxx.yyy.springbootguide.interceptor.LockKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: maoyan
 * @date: 2020/6/24 14:13:38
 * @description:
 */
@Configuration
public class CacheKeyConfig {

    @Bean
    public CacheKeyGenerator cacheKeyGenerator() {
        return new LockKeyGenerator();
    }
}
