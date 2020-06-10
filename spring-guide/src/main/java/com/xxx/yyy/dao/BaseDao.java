package com.xxx.yyy.dao;

import java.util.List;
import java.util.Map;

/**
 * 数据库接口
 *
 * @author: maoyan
 * @Date: 2018/10/26 11:59
 * @Description:
 */
public interface BaseDao {
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
     *批量插入
     * @param sql
     * @param list
     * @return
     */
     int[] batchInsert(String sql,List<List<String>> list);

    /**
     * 批量更新
     * @param sql
     * @param list
     * @return
     */
     int[] batchUpdate(String sql,List<List<String>> list);
}
