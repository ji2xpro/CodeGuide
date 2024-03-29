package com.xxx.yyy.springbootguide.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Druid连接池配置类
 * @author: maoyan
 * @date: 2020/2/24 16:20:39
 * @description:
 */
@Configuration
public class DruidConfig {
    /**
     * 配置一个管理后台的Servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        //创建servlet注册实体
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        //设置ip白名单
        servletRegistrationBean.addInitParameter("allow","");
        //设置ip黑名单，黑白名单共同存在共同时，deny优先于allow)
        servletRegistrationBean.addInitParameter("deny","192.168.0.2");
        //设置控制台管理用户：登录用户名和密码
        servletRegistrationBean.addInitParameter("loginUsername","admin");
        servletRegistrationBean.addInitParameter("loginPassword","admin");
        //是否可以重置数据
        servletRegistrationBean.addInitParameter("resetEnable","false");
        return servletRegistrationBean;
    }

    /**
     * 配置一个web监控的filter
     * @return
     */
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        //创建过滤器
        filterRegistrationBean.setFilter(new WebStatFilter());
        //设置过滤器过滤路径
        filterRegistrationBean.addUrlPatterns("/*");
        //忽略过滤的形式
        filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}

