package com.xxx.yyy.springbootguide.aspect;

import com.alibaba.fastjson.JSON;
import com.xxx.yyy.springbootguide.config.datasource.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author: maoyan
 * @date: 2020/2/18 11:16:38
 * @description:
 */
@Aspect
@Slf4j
@Component
public class ServiceLog {

    private long startTime;

    @Pointcut("execution(public * com.xxx.yyy.springbootguide.service.*.*(..))")
    public void service() {
    }

    @Before("service()")
    public void before(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String method = signature.getDeclaringTypeName() + "." + signature.getName();
        this.startTime = System.currentTimeMillis();
        log.info("-----------------------------------------------------");
        log.info("当前执行的service方法：" + method);
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.info("参数：" + arg);
        }
    }

    @AfterReturning(pointcut = "service()", returning = "ret")
    public void after(Object ret) {
        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        long endTime = System.currentTimeMillis();
        log.info(request.getMethod()+"方法执行了" +(endTime - this.startTime) + "ms");
        log.info("service返回参数:" + JSON.toJSONString(ret));
        log.info("-----------------------------------------------------");
    }

    @Around("service()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long begin = System.currentTimeMillis();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        Method method = methodSignature.getMethod();

        DataSource dataSource = method.getAnnotation(DataSource.class);

        Object result =  point.proceed();

        log.info("==============================请求开始==============================");

        log.info("请求链接：{}",request.getRequestURL().toString());

        log.info("请求类型：{}",request.getMethod());

        log.info("请求方法：{}.{}",signature.getDeclaringTypeName(),signature.getName());

        log.info("请求IP：{}",request.getRemoteAddr());

        log.info("请求入参：{}",JSON.toJSONString(point.getArgs()));

        long end = System.currentTimeMillis();

        log.info("请求耗时：{} ms",end-begin);

        log.info("请求返回：{}",JSON.toJSONString(result));

        log.info("==============================请求结束==============================");

        return result;
    }
}