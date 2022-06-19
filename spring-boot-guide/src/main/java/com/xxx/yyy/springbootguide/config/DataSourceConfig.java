package com.xxx.yyy.springbootguide.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.xxx.yyy.springbootguide.config.datasource.DynamicDataSource;
import com.xxx.yyy.springbootguide.enums.DataSources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源配置类
 *
 * @author: maoyan
 * @date: 2020/2/25 10:55:18
 * @description:
 */
@Configuration
//@MapperScan(basePackages = "com.xxx.yyy.springbootguide.mapper", sqlSessionFactoryRef = "SqlSessionFactory")
public class DataSourceConfig {
    //数据源1
    @Bean(DataSources.DB1)
    // 表示这个数据源是默认数据源
    @Primary
    // 读取application.properties中的配置参数映射成为一个对象，prefix表示参数的前缀
    @ConfigurationProperties(prefix = "spring.datasource.druid.db1")
    public DataSource dataSource1() {
        return DruidDataSourceBuilder.create().build();
    }

    // 数据源2
    @Bean(DataSources.DB2)
    // application.properteis中对应属性的前缀
    @ConfigurationProperties(prefix = "spring.datasource.druid.db2")
    public DataSource dataSource2() {

        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 动态数据源: 通过AOP在不同数据源之间动态切换
     *
     * @return
     */
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        // 配置多数据源，这个地方是比较核心的targetDataSources集合是我们数据库和名字之间的映射
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSources.DB1, dataSource1());
        targetDataSources.put(DataSources.DB2, dataSource2());

//        DynamicDataSource dynamicDataSource = new DynamicDataSource();
//        dynamicDataSource.setTargetDataSources(targetDataSources);
//        dynamicDataSource.setDefaultTargetDataSource(dataSource1());

        DynamicDataSource dynamicDataSource = DynamicDataSource.build(dataSource1(), targetDataSources);

        return dynamicDataSource;
    }

    /**
     * 配置@Transactional注解事物
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }

    @Bean(name = "SqlSessionFactory")
//    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactory SqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource)
            throws Exception {
        final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        /**
         * 由于使用了druid连接池，mybatis的配置需要在Datasource中设置（在application.properties中就不需要指定），配置如下：
         *
         * # 配置mapper xml文件所在路径
         * mybatis.mapper-locations=classpath:mapper/*.xml
         * # 配置映射类所在包名
         * mybatis.type-aliases-package=com.xxx.yyy.springbootguide.model
         *
         */
        // 设置映射类所在包名
        bean.setTypeAliasesPackage("com.xxx.yyy.springbootguide.model");
        // 设置我们的xml文件路径
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));


//            final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//            sessionFactory.setDataSource(mysql);
//            sessionFactory.setMapperLocations(
//                    new PathMatchingResourcePatternResolver()
//                            .getResources(MAPPER_LOCATION));
//
//            //分页插件
//            Properties properties = new Properties();
//            properties.setProperty("helperDialect", "mysql");
//            properties.setProperty("offsetAsPageNum", "true");
//            properties.setProperty("rowBoundsWithCount", "true");
//            properties.setProperty("reasonable", "true");
//            Interceptor interceptor = new PageInterceptor();
//            interceptor.setProperties(properties);
//            sessionFactory.setPlugins(new Interceptor[] {interceptor});

        return bean.getObject();
    }

    @Bean(name = "SqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
