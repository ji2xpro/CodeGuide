package com.xxx.yyy.springbootguide.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.xxx.yyy.springbootguide.config.datasource.DynamicDataSource;
import com.xxx.yyy.springbootguide.enums.DataSources;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.validation.DataBinder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 多数据源配置类
 * @author: maoyan
 * @date: 2020/2/25 10:55:18
 * @description:
 */
@Configuration
//@MapperScan(basePackages = "com.xxx.yyy.springbootguide.mapper", sqlSessionFactoryRef = "SqlSessionFactory")
public class DataSourceConfig {

//    @Value("${spring.datasource.db1.name}")
//    private String db1Name;
//    @Value("${spring.datasource.db1.jdbc-url}")
//    private String db1Url;
//    @Value("${spring.datasource.db1.username}")
//    private String db1Username;
//    @Value("${spring.datasource.db1.password}")
//    private String db1Password;
//    @Value("${spring.datasource.db1.driver-class-name}")
//    private String db1Classname;
//
//    @Value("${spring.datasource.db2.name}")
//    private String db2Name;
//    @Value("${spring.datasource.db2.jdbc-url}")
//    private String db2Url;
//    @Value("${spring.datasource.db2.username}")
//    private String db2Username;
//    @Value("${spring.datasource.db2.password}")
//    private String db2Password;
//    @Value("${spring.datasource.db2.driver-class-name}")
//    private String db2Classname;

//    @Autowired
//    StandardEnvironment env;


    @Autowired
    Environment environment;



//    @Value("${spring.datasource.type}")
//    private Class<? extends DataSource> dataSourceType;



    //数据源1
    @Bean(DataSources.DB1)
    @Primary
    // application.properteis中对应属性的前缀
    @ConfigurationProperties(prefix = "spring.datasource.druid.db1")
    public DataSource dataSource1() {
//        return DataSourceBuilder.create().build();

//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setDriverClassName(db1Classname);
//        druidDataSource.setUrl(db1Url);
//        druidDataSource.setName(db1Username);
//        druidDataSource.setPassword(db1Password);
//        druidDataSource.setName(db1Name);
//        return druidDataSource;


//        return initCustomDataSources("db1");

        return DruidDataSourceBuilder.create().build();


    }

    // 数据源2
    @Bean(DataSources.DB2)
    // application.properteis中对应属性的前缀
    @ConfigurationProperties(prefix = "spring.datasource.druid.db2")
    public DataSource dataSource2() {
//        return DataSourceBuilder.create().build();

//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setDriverClassName(db2Classname);
//        druidDataSource.setUrl(db2Url);
//        druidDataSource.setName(db2Username);
//        druidDataSource.setPassword(db2Password);
//        druidDataSource.setName(db2Name);
//        return druidDataSource;


//        return DruidDataSourceBuilder.create().build();
//        DruidDataSource druidDataSource = new DruidDataSource();
//        return common(env,druidDataSource);


//        return initCustomDataSources("db2");

        return DruidDataSourceBuilder.create().build();


    }

    public DruidDataSource initCustomDataSources(String database){
//        MutablePropertySources mutablePropertySources = env.getPropertySources();
//        PropertyResolver propertyResolver = new PropertySourcesPropertyResolver(mutablePropertySources);
//        String ue = propertyResolver.getProperty("spring.datasource.db1.name");


        Binder binder = Binder.get(environment);

        Map<String,Object> druidPropertiesMaps = binder.bind("spring.datasource.druid", Map.class).get();


        Map<String,Object> druidValuesMaps = new HashMap<>();
        for(String key : druidPropertiesMaps.keySet()) {
            String druidKey = "spring.datasource.druid." + key;
            druidValuesMaps.put(druidKey,druidPropertiesMaps.get(key));
        }

        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues(druidValuesMaps);

        Map<String,Object> dbPropertiesMaps = binder.bind("spring.datasource."+database, Map.class).get();
        for (String key: dbPropertiesMaps.keySet()) {
            if(key.equals("type")) {
                mutablePropertyValues.addPropertyValue("spring.datasource.type", dbPropertiesMaps.get(key));
            } else {
                String druidKey = "spring.datasource.druid." + key;
                mutablePropertyValues.addPropertyValue(druidKey, dbPropertiesMaps.get(key));
            }
        }
        DataSource ds = dataSourcebuild(mutablePropertyValues);
        DruidDataSource druidDataSource = new DruidDataSource();

        if(null != ds){
            if(ds instanceof DruidDataSource)
            {
                druidDataSource = (DruidDataSource)ds;

                druidDataSource.setName(database);
//                initDruidFilters(druidDataSource);
            }

//            customDataSourceNames.add(dataSourceName);
//            DynamicDataSourceContextHolder.datasourceId.add(dataSourceName);
//            targetDataResources.put(dataSourceName,ds);

        }

//        Properties properties = new Properties();
//        PropertySource<?> appProperties =  env.getPropertySources().get("applicationConfig: [classpath:/application.properties]");
//        Map<String,Object> source = (Map<String, Object>) appProperties.getSource();
//
//        properties.putAll(source);
//        druidDataSource.configFromPropety(properties);

        return druidDataSource;
    }

    /**
     * @Title: DataSourcebuild
     * @Description: 创建数据源
     * @param dataSourcePropertyValue 数据源创建所需参数
     *
     * @return DataSource 创建的数据源对象
     */
    public DataSource dataSourcebuild(MutablePropertyValues dataSourcePropertyValue)
    {
        DataSource ds = null;


        if(dataSourcePropertyValue.isEmpty()){
            return ds;
        }

        String type = dataSourcePropertyValue.get("spring.datasource.type").toString();

        if(StringUtils.isNotEmpty(type))
        {
            if(StringUtils.equals(type,DruidDataSource.class.getTypeName()))
            {
                ds = new DruidDataSource();


                DataBinder dataBinder = new DataBinder(ds, "spring.datasource.druid");

//                dataBinder.setConversionService(conversionService);
                dataBinder.setIgnoreInvalidFields(false);
                dataBinder.setIgnoreUnknownFields(true);
                dataBinder.bind(dataSourcePropertyValue);
            }
        }
        return ds;
    }

    /**
     * 动态数据源: 通过AOP在不同数据源之间动态切换
     * @return
     */
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        // 配置多数据源，集合是我们数据库和名字之间的映射
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSources.DB1, dataSource1());
        targetDataSources.put(DataSources.DB2, dataSource2());

//        DynamicDataSource dynamicDataSource = new DynamicDataSource();
//        dynamicDataSource.setTargetDataSources(targetDataSources);
//        // 设置默认数据源
//        dynamicDataSource.setDefaultTargetDataSource(dataSource1());

        DynamicDataSource dynamicDataSource = DynamicDataSource.build(dataSource1(),targetDataSources);

        return dynamicDataSource;
    }

    /**
     * 配置@Transactional注解事物
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
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        bean.setTypeAliasesPackage("com.xxx.yyy.springbootguide.model");
        // 设置我们的xml文件路径
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "SqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
