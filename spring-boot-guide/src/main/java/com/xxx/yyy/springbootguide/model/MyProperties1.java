package com.xxx.yyy.springbootguide.model;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: maoyan
 * @date: 2020/2/4 14:04:08
 * @description:
 */
@Data
@Component
@ConfigurationProperties(prefix = "my1")
public class MyProperties1 {
    private int age;
    private String name;
}
