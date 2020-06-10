package com.xxx.yyy.dao;

import java.util.List;
import java.util.Map;

/**
 * @author: maoyan
 * @date: 2019/10/12 16:32:03
 * @description:
 */
public interface BaseDao1 {
    /**
     * 增
     *
     * @param sql
     * @param params
     * @return
     */
    int insert(String sql, String... params);

    /**
     * 删
     *
     * @param sql
     * @param params
     * @return
     */
    int delete(String sql, String... params);

    /**
     * 改
     *
     * @param sql
     * @param params
     * @return
     */
    int update(String sql, String... params);

    /**
     * 单行数据查询
     *
     * @param sql
     * @param params
     * @return
     */
    Map<String, String> select(String sql, String... params);

    /**
     * 多行数据查询
     *
     * @param sql
     * @param params
     * @return
     */
    List<Map<String, String>> selects(String sql, String... params);

    /**
     * 返回查询到的结果集行数量
     * @param sql
     * @return
     */
    int selectRow(String sql, String...params);
}
