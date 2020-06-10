package com.xxx.yyy.springbootguide.model;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author: maoyan
 * @date: 2020/2/4 14:04:08
 * @description:
 */
@Data
@Component
@PropertySource("classpath:my2.properties")
@ConfigurationProperties(prefix = "my2")
public class MyProperties2 {
    private int age;
    private String name;
    private String email;
}
