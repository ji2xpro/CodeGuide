package com.xxx.yyy.springbootguide.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author: maoyan
 * @date: 2020/6/24 10:47:46
 * @description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheLock {
    /**
     * redis 锁key的前缀
     *
     * @return redis 锁key的前缀
     */
    String prefix() default "";

    /**
     * 过期秒数,默认为5秒
     *
     * @return 轮询锁的时间
     */
    int expire() default 5;

    /**
     * 超时时间单位
     *
     * @return 秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * <p>Key的分隔符（默认 :）</p>
     * <p>生成的Key：N:SO1008:500</p>
     *
     * @return String
     */
    String delimiter() default ":";
}
