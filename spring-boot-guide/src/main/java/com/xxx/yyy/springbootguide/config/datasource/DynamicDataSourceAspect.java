package com.xxx.yyy.springbootguide.config.datasource;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切换数据源Advice
 * <p>
 * 使用AOP拦截特定的注解去动态的切换数据源
 * 通过增加一个切面去拦截servcie层在调用mybatis生成的接口时，来切换数据源，从而省去每次调用mybatis生成的接口时都要手动注明数据源
 *
 * @author: maoyan
 * @date: 2020/2/25 11:41:23
 * @description:
 */
@Aspect
@Component
@Order(1) //多个aop配置时需要指定加载顺序（事务也是一个） -1为最先执行
@Slf4j
public class DynamicDataSourceAspect {
//    // @within在类上设置, @annotation在方法上进行设置
//    @Pointcut("@within(com.xxx.yyy.springbootguide.config.datasource.DataSource)||@annotation(com.xxx.yyy.springbootguide.config.datasource.DataSource)")
//    public void pointcut() {
//    }
//
//    @Before("pointcut()")
//    public void before(JoinPoint joinPoint) {
//        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
//        //获取方法上的注解
//        DataSource annotation = method.getAnnotation(DataSource.class);
//        if (annotation == null) {
//            //获取类上面的注解
//            annotation = joinPoint.getTarget().getClass().getAnnotation(DataSource.class);
//            if (annotation == null) {
//                return;
//            }
//        }
//        //获取注解上的数据源的值的信息
//        String dataSource = annotation.value();
//        if (dataSource != null) {
//            //给当前的执行SQL的操作设置特殊的数据源的信息
//            DynamicDataSourceContextHolder.setDataSource(dataSource);
//        }
////        System.out.println("AOP动态切换数据源，className"+joinPoint.getTarget().getClass().getName()+"methodName"+method.getName()+";dataSourceKey:"+dataSourceKey==""?"默认数据源":dataSourceKey);
//    }
//
//    @After("pointcut()")
//    public void after(JoinPoint joinPoint) {
//        //方法执行完毕之后，销毁当前数据源信息，进行垃圾回收
//        DynamicDataSourceContextHolder.removeDataSource();
//    }

    /**
     * @Before("@annotation(ds)")的意思是：@Before：在方法执行之前进行执行：
     * @annotation(targetDataSource)：会拦截注解targetDataSource的方法，否则不拦截;
     */
    @Before("@annotation(dataSource)")
    public void changeDataSource(JoinPoint point, DataSource dataSource) {
        //获取当前的指定的数据源;
        String dsValue = dataSource.value();

        //如果不在我们注入的所有的数据源范围之内，那么输出警告信息，系统自动使用默认的数据源。
        if (!DynamicDataSourceContextHolder.isExist(dsValue)) {
            log.error("数据源[{}]不存在，使用默认数据源 > {}", dataSource.value(), point.getSignature());
            return;
        } else {
            log.info("Use DataSource : {} > {}", dataSource.value(), point.getSignature());
            //找到的话，那么设置到动态数据源上下文中。
            DynamicDataSourceContextHolder.setDataSource(dataSource.value());
        }
    }

    @After("@annotation(dataSource)")
    public void restoreDataSource(JoinPoint point, DataSource dataSource) {
        log.info("Revert DataSource : {} > {}", dataSource.value(), point.getSignature());
        //方法执行完毕之后，销毁当前数据源信息，进行垃圾回收。
        DynamicDataSourceContextHolder.removeDataSource();
    }
}
