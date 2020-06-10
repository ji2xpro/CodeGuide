package com.xxx.yyy.dao;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: maoyan
 * @date: 2019/10/12 16:09:20
 * @description:
 */
public class SqlDaoImpl implements BaseDao{
    private JdbcTemplate jdbcTemplate;

    public SqlDaoImpl(String beanName) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring-jdbc-stock.xml"});
        context.start();
        this.jdbcTemplate = (JdbcTemplate) context.getBean(beanName);
    }

    /**
     * 增
     *
     * @param sql
     * @param params
     * @return
     */
    @Override
    public int insert(String sql, String... params) {
//        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps) throws SQLException {
//                // TODO Auto-generated method stub
//                for (int i = 1; i <= params.length; i++) {
//                    ps.setString(i, params[i - 1]);
//                }
//            }
//        });
        return jdbcTemplate.update(sql, ps -> {
            // TODO Auto-generated method stub
            for (int i = 1; i <= params.length; i++) {
                ps.setString(i, params[i - 1]);
            }
        });
    }

    /**
     * 删
     *
     * @param sql
     * @param params
     * @return
     */
    @Override
    public int delete(String sql, String... params) {
        return jdbcTemplate.update(sql, ps -> {
            // TODO Auto-generated method stub
            for (int i = 1; i <= params.length; i++) {
                ps.setString(i, params[i - 1]);
            }
        });
    }

    /**
     * 改
     *
     * @param sql
     * @param params
     * @return
     */
    @Override
    public int update(String sql, String... params) {
        return jdbcTemplate.update(sql, ps -> {
            // TODO Auto-generated method stub
            for (int i = 1; i <= params.length; i++) {
                ps.setString(i, params[i - 1]);
            }
        });
    }

    /**
     * 单行数据查询
     *
     * @param sql
     * @param params
     * @return
     */
    @Override
    public Map<String, String> select(String sql, String... params) {
//        return jdbcTemplate.query(sql, new PreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps) throws SQLException {
//                for (int i = 1; i <= params.length; i++) {
//                    ps.setString(i, params[i - 1]);
//                }
//            }
//
//        }, new ResultSetExtractor<Map<String, String>>() {
//            @Override
//            public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
//                // TODO Auto-generated method stub
//                Map<String, String> resultMap = new HashMap<>();
//                while (rs.next()) {
//                    ResultSetMetaData rsd = rs.getMetaData();
//
//                    for (int i = 1; i <= rsd.getColumnCount(); i++) {
//                        resultMap.put(rsd.getColumnName(i), rs.getString(rsd.getColumnName(i)));
//                    }
//                }
//                return resultMap;
//            }
//        });
        return jdbcTemplate.query(sql, ps -> {
            for (int i = 1; i <= params.length; i++) {
                ps.setString(i, params[i - 1]);
            }
        }, rs -> {
            // TODO Auto-generated method stub
            Map<String, String> resultMap = new HashMap<>();
            while (rs.next()) {
                ResultSetMetaData rsd = rs.getMetaData();

                for (int i = 1; i <= rsd.getColumnCount(); i++) {
                    resultMap.put(rsd.getColumnName(i), rs.getString(rsd.getColumnName(i)));
                }
            }
            return resultMap;
        });
    }

    /**
     * 多行数据查询
     *
     * @param sql
     * @param params
     * @return
     */
    @Override
    public List<Map<String, String>> selects(String sql, String... params) {
        return jdbcTemplate.query(sql, ps -> {
            for (int i = 1; i <= params.length; i++) {
                ps.setString(i, params[i - 1]);
            }
        }, rs -> {
            // TODO Auto-generated method stub
            List<Map<String, String>> list = new ArrayList<>();
            while (rs.next()) {
                ResultSetMetaData rsd = rs.getMetaData();

                Map<String, String> resultMap = new HashMap<>();
                for (int i = 1; i <= rsd.getColumnCount(); i++) {
                    resultMap.put(rsd.getColumnName(i), rs.getString(rsd.getColumnName(i)));
                }

                list.add(resultMap);
            }
            return list;
        });
    }

    /**
     * 批量插入
     *
     * @param sql
     * @param list
     * @return
     */
    @Override
    public int[] batchInsert(String sql, List<List<String>> list) {
        // TODO Auto-generated method stub
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                // TODO Auto-generated method stub
                List<String> temp = list.get(i);

                for (int j = 0; j < temp.size(); j++) {
                    ps.setString(j + 1, temp.get(j));
                }
            }

            @Override
            public int getBatchSize() {
                // TODO Auto-generated method stub
                return list.size();
            }
        });
    }

    /**
     * 批量更新
     *
     * @param sql
     * @param list
     * @return
     */
    @Override
    public int[] batchUpdate(String sql, List<List<String>> list) {
        // TODO Auto-generated method stub
        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                // TODO Auto-generated method stub
                List<String> temp = list.get(i);

                for (int j = 0; j < temp.size(); j++) {
                    ps.setString(j + 1, temp.get(j));
                }
            }

            @Override
            public int getBatchSize() {
                // TODO Auto-generated method stub
                return list.size();
            }
        });
    }


}
