package com.xxx.yyy.spring.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author: maoyan
 * @date: 2019/10/12 16:09:20
 * @description:
 */
public class SqlDaoImpl1 implements BaseDao1 {
    private static String url;
    private static String username;
    private static String password;
    private static String driver;

    private static Connection conn;
    private static PreparedStatement stmt;

    private static ResultSet rs;

    public SqlDaoImpl1(String url, String username, String password, String driver) {
        SqlDaoImpl1.url = url;
        SqlDaoImpl1.username = username;
        SqlDaoImpl1.password = password;
        SqlDaoImpl1.driver = driver;
        connection();
    }

    /**
     * sql连接
     */
    private void connection() {
        try {
            InputStream inputStream = SqlDaoImpl1.class.getClassLoader().getResourceAsStream("jdbc.properties");
            Properties properties = new Properties();
            properties.load(inputStream);

            url = properties.getProperty(url);
            username = properties.getProperty(username);
            password = properties.getProperty(password);
            driver = properties.getProperty(driver);

            // STEP 1: Register JDBC driver
            Class.forName(driver);

            // STEP 2: Open a connection
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * sql连接关闭
     */
    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
        int index = 0;
        try {
            stmt = conn.prepareStatement(sql);
            for (int j = 1; j <= params.length; j++) {
                stmt.setString(j, params[j - 1]);
            }
            index = stmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            //close();
        }
        return index;
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
        int index = 0;
        try {
            stmt = conn.prepareStatement(sql);
            for (int j = 1; j <= params.length; j++) {
                stmt.setString(j, params[j - 1]);
            }
            index = stmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            //close();
        }
        return index;
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
        int index = 0;
        try {
            stmt = conn.prepareStatement(sql);
            for (int j = 1; j <= params.length; j++) {
                stmt.setString(j, params[j - 1]);
            }
            index = stmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            //close();
        }
        return index;
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
        Map<String, String> map = new HashMap<>();
        try {
            stmt = conn.prepareStatement(sql);
            for (int j = 1; j <= params.length; j++) {
                stmt.setString(j, params[j - 1]);
            }
            rs = stmt.executeQuery();
            map = resultSet2Map(rs);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            //close();
        }
        return map;
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
        List<Map<String, String>> list = new ArrayList<>();
        try {
            stmt = conn.prepareStatement(sql);
            for (int j = 1; j <= params.length; j++) {
                stmt.setString(j, params[j - 1]);
            }
            rs = stmt.executeQuery();
            list = getResultSetList(rs);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            //close();
        }
        return list;
    }

    /**
     * 返回查询到的结果集行数量
     *
     * @param sql
     * @param params
     * @return
     */
    @Override
    public int selectRow(String sql, String... params) {
        int index = 0;
        try {
            stmt = conn.prepareStatement(sql);
            for (int j = 1; j <= params.length; j++) {
                stmt.setString(j, params[j - 1]);
            }
            rs = stmt.executeQuery();
            index = getResultSetRow(rs);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            //close();
        }
        return index;
    }

    /**
     * 单行数据查询
     *
     * @param rs
     * @return
     * @throws Exception
     */
    private Map<String, String> resultSet2Map(ResultSet rs) throws Exception {
        Map<String, String> map = new HashMap<>();
        while (rs.next()) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            for (int i = 1; i <= count; i++) {
                String key = rsmd.getColumnLabel(i);
                String value = rs.getString(i);
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * 多行数据查询
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private List<Map<String, String>> getResultSetList(ResultSet rs) throws SQLException {
        List<Map<String, String>> list = new ArrayList<>();
        ResultSetMetaData resultSetMetaData = rs.getMetaData();

        int index = resultSetMetaData.getColumnCount();

        while (rs.next()) {
            Map<String, String> map = new HashMap<>();
            for (int i = 0; i < index; i++) {
                map.put(resultSetMetaData.getColumnName(i + 1), rs.getString(i + 1));
            }
            list.add(map);
        }
        return list;
    }

    /**
     * 返回查询到的结果集行数量
     *
     * @param rs
     * @return
     * @throws Exception
     */
    private int getResultSetRow(ResultSet rs) throws Exception {
        int row = 0;
        while (rs.next()) {
            row = row + 1;
        }
        return row;
    }
}
