//package com.xxx.yyy.springbootguide.config;
//
//import com.alibaba.druid.filter.Filter;
//import com.alibaba.druid.pool.DruidDataSource;
//import com.xxx.yyy.springbootguide.config.datasource.DynamicDataSource;
//import com.xxx.yyy.springbootguide.config.datasource.DynamicDataSourceContextHolder;
//import com.xxx.yyy.springbootguide.enums.DataSources;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.MutablePropertyValues;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.bind.BindResult;
//import org.springframework.boot.context.properties.bind.Binder;
//import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.convert.ConversionService;
//import org.springframework.core.convert.support.DefaultConversionService;
//import org.springframework.core.env.Environment;
//import org.springframework.core.env.MutablePropertySources;
//import org.springframework.core.env.PropertyResolver;
//import org.springframework.core.env.PropertySourcesPropertyResolver;
//import org.springframework.core.env.StandardEnvironment;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.validation.DataBinder;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//import java.util.Set;
//
///**
// * 多数据源配置类
// *
// * @author: maoyan
// * @date: 2020/2/25 10:55:18
// * @description:
// */
//@Slf4j
//@Configuration
////@MapperScan(basePackages = "com.xxx.yyy.springbootguide.mapper", sqlSessionFactoryRef = "SqlSessionFactory")
//public class DataSourceConfig1 {
//    private List<String> customDataSourceNames = new ArrayList<>();
//
//    private ConversionService conversionService = new DefaultConversionService();
//
//    @Autowired
//    StandardEnvironment env;
//
//    @Autowired
//    Environment environment;
//
//    @Bean(name = "dataSource")
//    @Primary
//    public AbstractRoutingDataSource dataSource() {
//        DynamicDataSource dynamicDataSource = new DynamicDataSource();
//        LinkedHashMap<Object, Object> targetDataSources = new LinkedHashMap<>();
//        initCustomDataSources(targetDataSources);
//        dynamicDataSource.setDefaultTargetDataSource(targetDataSources.get(customDataSourceNames.get(0)));
//        dynamicDataSource.setTargetDataSources(targetDataSources);
//        dynamicDataSource.afterPropertiesSet();
//
//        return dynamicDataSource;
//    }
//
//    public void initCustomDataSources(LinkedHashMap<Object, Object> targetDataResources) {
//
//        Iterable sources = ConfigurationPropertySources.get(environment);
//        Binder binder = new Binder(sources);
//
//        BindResult<Properties> bindResult = binder.bind("spring.datasource.druid", Properties.class);
//        Properties properties = bindResult.get();
//        Set<String> set = properties.stringPropertyNames();
//
//        Map<String, Object> druidValuesMaps = new HashMap<>();
//        for (String key : set) {
//            String druidKey = "spring.datasource.druid." + key;
//
//            druidValuesMaps.put(druidKey, properties.get(key));
//        }
//        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues(druidValuesMaps);
//
//        Map<String, Object> dbPropertiesMaps = binder.bind("spring.custom.datasource", Map.class).get();
//        for (String dsName : dbPropertiesMaps.keySet()) {
//            Object dsMaps = dbPropertiesMaps.get(dsName);
//            Map<String, String> dsMap = new HashMap<>();
//            if (dsMaps instanceof Map) {
//                dsMap = (Map<String, String>) dsMaps;
//            }
//            for (String dsKey : dsMap.keySet()) {
//                if (dsKey.equals("type")) {
//                    mutablePropertyValues.addPropertyValue("spring.datasource.type", dsMap.get(dsKey));
//                } else {
//                    String druidKey = "spring.datasource.druid." + dsKey;
//                    mutablePropertyValues.addPropertyValue(druidKey, dsMap.get(dsKey));
//                }
//            }
//
//            DataSource ds = dataSourceBuild(mutablePropertyValues);
//            if (null != ds) {
//                if (ds instanceof DruidDataSource) {
//                    DruidDataSource druidDataSource = (DruidDataSource) ds;
//                    druidDataSource.setName(dsName);
//                    initDruidFilters(druidDataSource);
//                }
//
//                customDataSourceNames.add(dsName);
//                DynamicDataSourceContextHolder.ALL_DATA_SOURCE_KEY.add(dsName);
//                targetDataResources.put(dsName, ds);
//
//            }
//            log.info("Data source initialization 【" + dsName + "】 successfully ...");
//        }
//    }
//
//    /**
//     * @param dataSourcePropertyValue 数据源创建所需参数
//     * @return DataSource 创建的数据源对象
//     * @Title: DataSourcebuild
//     * @Description: 创建数据源
//     */
//    public DataSource dataSourceBuild(MutablePropertyValues dataSourcePropertyValue) {
//        DataSource ds = null;
//        if (dataSourcePropertyValue.isEmpty()) {
//            return ds;
//        }
//
//        String type = dataSourcePropertyValue.get("spring.datasource.type").toString();
//
//        if (StringUtils.isNotEmpty(type)) {
//            if (StringUtils.equals(type, DruidDataSource.class.getTypeName())) {
//                ds = new DruidDataSource();
//
//                RelaxedDataBinder dataBinder = new RelaxedDataBinder(ds, "spring.datasource.druid.");
//                dataBinder.setConversionService(conversionService);
//                dataBinder.setIgnoreInvalidFields(false);
//                dataBinder.setIgnoreNestedProperties(false);
//                dataBinder.setIgnoreUnknownFields(true);
//                dataBinder.bind(dataSourcePropertyValue);
//            }
//        }
//        return ds;
//    }
//
//    /**
//     * 配置@Transactional注解事物
//     *
//     * @return
//     */
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        return new DataSourceTransactionManager(dataSource());
//    }
//
//    @Bean(name = "SqlSessionFactory")
////    @ConfigurationProperties(prefix = "mybatis")
//    public SqlSessionFactory SqlSessionFactory(@Qualifier("dataSource") DataSource dynamicDataSource)
//            throws Exception {
//        final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(dynamicDataSource);
//        /**
//         * 由于使用了druid连接池，mybatis的配置需要在Datasource中设置（在application.properties中就不需要指定），配置如下：
//         *
//         * # 配置mapper xml文件所在路径
//         * mybatis.mapper-locations=classpath:mapper/*.xml
//         * # 配置映射类所在包名
//         * mybatis.type-aliases-package=com.xxx.yyy.springbootguide.model
//         *
//         */
//        // 设置映射类所在包名
//        bean.setTypeAliasesPackage("com.xxx.yyy.springbootguide.model");
//        // 设置我们的xml文件路径
//        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
//
//
////            final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
////            sessionFactory.setDataSource(mysql);
////            sessionFactory.setMapperLocations(
////                    new PathMatchingResourcePatternResolver()
////                            .getResources(MAPPER_LOCATION));
////
////            //分页插件
////            Properties properties = new Properties();
////            properties.setProperty("helperDialect", "mysql");
////            properties.setProperty("offsetAsPageNum", "true");
////            properties.setProperty("rowBoundsWithCount", "true");
////            properties.setProperty("reasonable", "true");
////            Interceptor interceptor = new PageInterceptor();
////            interceptor.setProperties(properties);
////            sessionFactory.setPlugins(new Interceptor[] {interceptor});
//
//
//        return bean.getObject();
//    }
//
//    @Bean(name = "SqlSessionTemplate")
//    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//
//    private void initDruidFilters(DruidDataSource druidDataSource) {
//        Iterable sources = ConfigurationPropertySources.get(environment);
//        Binder binder = new Binder(sources);
//
//        List<Filter> filters = druidDataSource.getProxyFilters();
//
//        String filterNames = environment.getProperty("spring.datasource.druid.filters");
//        String[] filterNameArray = filterNames.split("\\,");
//
//        for (int i = 0; i < filterNameArray.length; i++) {
//            String filterName = filterNameArray[i];
//            Filter filter = filters.get(i);
//
//            Map<String, Object> filterValueMap = binder.bind("spring.datasource.druid.filter."+filterName, Map.class).get();
//
//            String statFilterEnabled = filterValueMap.get("enabled").toString();
//            if (statFilterEnabled.equals("true")) {
//                MutablePropertyValues propertyValues = new MutablePropertyValues(filterValueMap);
//                DataBinder dataBinder = new DataBinder(filter);
//                dataBinder.bind(propertyValues);
//            }
//        }
//    }
//}
