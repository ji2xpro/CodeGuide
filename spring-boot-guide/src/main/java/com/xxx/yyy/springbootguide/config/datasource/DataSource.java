package com.xxx.yyy.springbootguide.config.datasource;

import com.xxx.yyy.springbootguide.enums.DataSources;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在方法上使用，用于指定使用哪个数据源
 *
 * @author: maoyan
 * @date: 2020/2/25 11:12:59
 * @description:
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String value() default DataSources.DB1;
}
