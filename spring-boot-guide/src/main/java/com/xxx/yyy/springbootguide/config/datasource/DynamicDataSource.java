package com.xxx.yyy.springbootguide.config.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源
 * 多数据源选择类：拿到动态切换的Key，spring给你选择数据源
 *
 * @author: maoyan
 * @date: 2020/2/25 10:57:16
 * @description:抽象的数据源实现（javax.sql.DataSource），该实现基于查找键将getConnection()路由到各种目标数据源，目标数据源通常但是不限于通过一些线程绑定的事务上下文来确定
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {
    public DynamicDataSource(){}

    /**
     * 构件DynamicDataSource对象静态方法
     */
    public static DynamicDataSource build(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources){
        return new DynamicDataSource(defaultTargetDataSource, targetDataSources);
    }

    /**
     * 私有的构造函数
     * @param defaultTargetDataSource 默认数据源
     * @param targetDataSources 所有的数据源
     */
    private DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources){
        /**
         * 如果存在默认数据源，指定默认的目标数据源；映射的值可以是javax.sql.DataSource或者是数据源（data source）字符串；
         * 如果setTargetDataSources指定的数据源不存在，将会使用默认的数据源
         */
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        /**
         * 指定目标数据源的Map集合映射，使用查找键（Look Up Key）作为Key,这个Map集合的映射Value可以是javax.sql.DataSource或者是数据源（data source）字符串；
         * 集合的Key可以为任何数据类型，当前类会通过泛型的方式来实现查找，
         */
        super.setTargetDataSources(targetDataSources);
        /**
         * 指定对默认的数据源是否应用宽松的回退，如果找不到当前查找键（Look Up Key）的特定数据源，就返回默认的数据源，默认为true;
         */
        super.setLenientFallback(true);
        /**
         * 设置DataSourceLookup为解析数据源的字符串，默认是使用JndiDataSourceLookup；允许直接指定应用程序服务器数据源的JNDI名称；
         */
        super.setDataSourceLookup(null);
        /**
         * 将设置的默认数据源、目标数据源解析为真实的数据源对象赋值给resolvedDefaultDataSource变量和resolvedDataSources变量
         */
        super.afterPropertiesSet();
        /**
         * 将数据源查找键（look up key）KEY存储进入静态变量中，供其它地方校验使用
         */
        DynamicDataSourceContextHolder.ALL_DATA_SOURCE_KEY.addAll(targetDataSources.keySet());
    }

    /**
     * 确定当前线程的查找键，这通常用于检查线程绑定事物的上下文，允许是任意的键（Look Up Key），
     * 返回的查找键（Look Up Key）需要与存储的查找键（Look Up key）类型匹配
     *
     * 代码中的determineCurrentLookupKey方法取得一个字符串，
     * 该字符串将与配置文件中的相应字符串进行匹配以定位数据源，配置文件，即applicationContext.xml文件中需要要如下代码：(non-Javadoc)
     * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
     */
    @Override
    protected Object determineCurrentLookupKey() {
        /**
         * DynamicDataSourceContextHolder代码中使用setDataSourceType
         * 设置当前的数据源，在路由类中使用getDataSourceType进行获取，
         * 交给AbstractRoutingDataSource进行注入使用。
         *
         */
        log.info("请求数据源为：{}",DynamicDataSourceContextHolder.getDataSource());
        return DynamicDataSourceContextHolder.getDataSource();
    }

}

