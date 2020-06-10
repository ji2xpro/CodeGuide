package com.xxx.yyy.springbootguide.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;


/**
 * Druid连接池配置类
 * @author: maoyan
 * @date: 2020/2/24 16:20:39
 * @description:
 */
@Configuration
public class DruidConfig {

//    @Value("${spring.datasource.url}")
//    private String dbUrl;
//    @Value("${spring.datasource.username}")
//    private String username;
//    @Value("${spring.datasource.password}")
//    private String password;
//    @Value("${spring.datasource.driver-class-name}")
//    private String driverClassName;
//    @Value("${spring.datasource.initialSize}")
//    private int initialSize;
//    @Value("${spring.datasource.minIdle}")
//    private int minIdle;
//    @Value("${spring.datasource.maxActive}")
//    private int maxActive;
//    @Value("${spring.datasource.maxWait}")
//    private int maxWait;
//    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
//    private int timeBetweenEvictionRunsMillis;
//    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
//    private int minEvictableIdleTimeMillis;
//    @Value("${spring.datasource.validationQuery}")
//    private String validationQuery;
//    @Value("${spring.datasource.testWhileIdle}")
//    private boolean testWhileIdle;
//    @Value("${spring.datasource.testOnBorrow}")
//    private boolean testOnBorrow;
//    @Value("${spring.datasource.testOnReturn}")
//    private boolean testOnReturn;
//    @Value("${spring.datasource.poolPreparedStatements}")
//    private boolean poolPreparedStatements;
//    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
//    private int maxPoolPreparedStatementPerConnectionSize;
//    @Value("${spring.datasource.filters}")
//    private String filters;
//    @Value("{spring.datasource.connectionProperties}")
//    private String connectionProperties;
//
//    @Bean   //声明其为Bean实例
//    @Primary //在同样的DataSource中，首先使用被标注的DataSource
//    public DataSource dataSource(){
//        DruidDataSource datasource = new DruidDataSource();
//        datasource.setUrl(this.dbUrl);
//        datasource.setUsername(username);
//        datasource.setPassword(password);
//        datasource.setDriverClassName(driverClassName);
//
//        //configuration
//        datasource.setInitialSize(initialSize);
//        datasource.setMinIdle(minIdle);
//        datasource.setMaxActive(maxActive);
//        datasource.setMaxWait(maxWait);
//        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//        datasource.setValidationQuery(validationQuery);
//        datasource.setTestWhileIdle(testWhileIdle);
//        datasource.setTestOnBorrow(testOnBorrow);
//        datasource.setTestOnReturn(testOnReturn);
//        datasource.setPoolPreparedStatements(poolPreparedStatements);
//        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
//        try {
//            datasource.setFilters(filters);
//        } catch (SQLException e) {
//            System.err.println("druid configuration initialization filter: "+ e);
//        }
//        datasource.setConnectionProperties(connectionProperties);
//        return datasource;
//    }
//
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource druid(){
//        return new DruidDataSource();
//    }

    @Bean
    public ServletRegistrationBean druidServlet(){
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

    @Bean
    public FilterRegistrationBean statFilter(){
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

