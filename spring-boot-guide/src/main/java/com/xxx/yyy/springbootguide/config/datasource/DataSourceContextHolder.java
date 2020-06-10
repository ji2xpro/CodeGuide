package com.xxx.yyy.springbootguide.config.datasource;

import com.xxx.yyy.springbootguide.enums.DataSources;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 多数据源操作
 * @author: maoyan
 * @date: 2020/2/25 10:58:21
 * @description:
 */
public class DataSourceContextHolder {
    /**
     * 存储当前系统加载的数据源的查找键（look up key）KEY
     */
    public static final Set<Object> ALL_DATA_SOURCE_KEY = new HashSet<>();

    /**
     * 使用ThreadLocal存储当前使用数据源实例的key,实例化时给定默认数据源。
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>(){
        @Override
        protected String initialValue(){
            return DataSources.DB1;
        }
    };

    /**
     * 设置当前线程持有的数据源
     * @param dataSource
     */
    public static void setDataSource(String dataSource){
        if (isExist(dataSource)){
            CONTEXT_HOLDER.set(dataSource);
        }else {
            throw new NullPointerException(StringUtils.join("数据源查找键（Look up key）【", dataSource,"】不存在"));
        }
    }

    /**
     * 获取当前线程持有的数据源
     * @return
     */
    public static String getDataSource(){
        return CONTEXT_HOLDER.get();
    }

    /**
     * 删除当前线程持有的数据源
     */
    public static void removeDataSource(){
        CONTEXT_HOLDER.remove();
    }


    /**
     * 判断数据源在系统中是否存在
     */
    public static boolean isExist(String dataSource){
        if(StringUtils.isEmpty(dataSource)){
            return false;
        }
        if(ALL_DATA_SOURCE_KEY.contains(dataSource)){
            return true;
        }
        return false;
    }
}
