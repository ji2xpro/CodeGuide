package com.xxx.yyy;

import com.xxx.yyy.dao.SqlDaoImpl;
import com.xxx.yyy.dao.SqlDaoImpl1;

import java.util.List;
import java.util.Map;

public class SqlTest {
	public static void main(String[] args) {
		SqlDaoImpl sqlDao = new SqlDaoImpl("myShowJdbcTemplate");
		String string = "select * from TPS_ProjectShow where ProjectID = '89311'";
		List<Map<String,String>> list = sqlDao.selects(string);
		System.out.println(list);


		SqlDaoImpl1 sqlDaoImpl1 = new SqlDaoImpl1("myshow.jdbc.url","myshow.jdbc.user","myshow.jdbc.password","myshow.jdbc.mysql.driver");
		List<Map<String,String>> list1 = sqlDaoImpl1.selects(string);
		sqlDaoImpl1.close();
		System.out.println(list1);
	}
}
