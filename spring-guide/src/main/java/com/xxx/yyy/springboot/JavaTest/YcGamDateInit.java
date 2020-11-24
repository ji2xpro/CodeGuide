package com.xxx.yyy.springboot.JavaTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


public class YcGamDateInit {

	/**
	 * 增
	 * 
	 * @throws SQLException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void insert() throws SQLException, IOException, ClassNotFoundException {

		InputStream inputStream = new FileInputStream(
				System.getProperty("user.dir") + "/src/test/resources/stock.properties");

		Properties properties = new Properties();
		properties.load(inputStream);

		String url = properties.getProperty("gam.jdbc.url");
		String username = properties.getProperty("gam.jdbc.user");
		String password = properties.getProperty("gam.jdbc.password");
	

		Connection conn = DriverManager.getConnection(url, username, password);
//		conn.setAutoCommit(false);
		
		
		String sql = "INSERT INTO `gam`.`sso_account_copy1`(`id`, `sys_id`, `account`, `parent_account`, `account_type`, `account_name`, `account_pwd`, `status`, `mobile_no`, `inserttimestamp`, `updatetimestamp`, `sns_account`, `encr_upat`) VALUES (?, '00', ?, ?, '0000', NULL, NULL, '1', NULL, ?, ?, NULL, ?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		String idTemp = "666666000";
		String accountTemp = "SNS6666666666666666000";

		final int batchSize = 5000;
		int count = 0;
		for (int i = 1000000; i < 1500000; i++) {
			String id = idTemp + String.valueOf(i);
			String account = accountTemp + String.valueOf(i);
			String time = getToday();

			ps.setString(1, id);
			ps.setString(2, account);
			ps.setString(3, account);
			ps.setString(4, time);
			ps.setString(5, time);
			ps.setString(6, time);
			ps.addBatch();
			System.out.println(++count);
			if(++count % batchSize == 0) {
		        ps.executeBatch();
		    }
		}
		ps.executeBatch();
//		conn.commit();  
		ps.close();
		conn.close();
		
		
		
		System.out.println("数据已插入成功");
		
	}
	
	
	

	/**
	 * 生成日期格式字符串 格式：yyyy/MM/dd HH:mm:ss
	 * 
	 * @return String
	 */
	public static String getToday() {
		SimpleDateFormat timePattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return timePattern.format(new Date());
	}

	/**
	 * 获取date日期的后update天
	 * 
	 * @param date
	 * @param update
	 * @return
	 */
	public static String getDate(String date, int update) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = null;
		try {
			dateString = sdf.format(sdf.parse(date).getTime() + (long) update * 24000 * 3600);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateString + date.substring(10);
	}

	public static void main(String[] args) {
		try {
			insert();
		} catch (SQLException | ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
