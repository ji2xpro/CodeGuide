package com.xxx.yyy.springbootguide.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * key生成器
 *
 * @author: maoyan
 * @date: 2020/6/24 11:01:43
 * @description:
 */
public interface CacheKeyGenerator {
    /**
     * 获取AOP参数,生成指定缓存Key
     *
     * @param pjp PJP
     * @return 缓存KEY
     */
    String getLockKey(ProceedingJoinPoint pjp);
}
