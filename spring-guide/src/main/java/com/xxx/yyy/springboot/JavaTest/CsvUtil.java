package com.xxx.yyy.springboot.JavaTest;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvUtil {

	private static String url;
	private static String username;
	private static String password;
	private static String driver;
	private static Statement stmt;
	private static Connection conn;
	private static ResultSet rs;

	public CsvUtil() {
		CsvUtil.url = "jdbc:mysql://10.199.111.221:5508/bbs?characterEncoding=utf-8";
		CsvUtil.username = "bbs";
		CsvUtil.password = "bbs";
		CsvUtil.driver = "com.mysql.cj.jdbc.Driver";
		connection();
	}

	/**
	 * Sql连接
	 */
	private void connection() {
		try {
			// STEP 1: Register JDBC driver
			Class.forName(driver);
			// STEP 2: Open a connection
			conn = DriverManager.getConnection(url, username, password);
			stmt = conn.createStatement();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取多行数据
	 * 
	 * @param sql
	 * @return
	 */
	public List<Map<String, String>> selects(String sql) {
		// TODO Auto-generated method stub
		List<Map<String, String>> list = new ArrayList<>();
		try {
			rs = stmt.executeQuery(sql);
			list = getResultSetList(rs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取多行数据
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
	 * 写入csv文件
	 * 
	 * @param filePath
	 * @param list
	 * @param append
	 */
//	public static void wirteCSVFile(String filePath, List<Map<String, String>> list, Boolean append) {
//		try {
//			File file = new File(filePath);
//			if (!file.exists()) {
//				file.createNewFile();
//			}
//
//			FileOutputStream fileOutputStream = new FileOutputStream(filePath, append);
//			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
//			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
//
//			CSVWriter csvWriter = new CSVWriter(bufferedWriter);
//
////			for (int i = 0; i < list.size(); i++) {
////				String[] temp = new String[1];
////				temp[0] = list.get(i).get("id");
////				csvWriter.writeNext(temp);
////			}
//
//			String[] temp = new String[list.size()];
//			for (int i = 0; i < list.size(); i++) {
//				temp[i] = list.get(i).get("id");
//			}
//			csvWriter.writeNext(temp);			
//
//			csvWriter.close();
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 写入csv文件
	 * 
	 * @param filePath
	 * @param list
	 * @param append
	 */
	public static void wirteCSVFile(String filePath, List<String[]> list, Boolean append) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream fileOutputStream = new FileOutputStream(filePath, append);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

			CSVWriter csvWriter = new CSVWriter(bufferedWriter);

			for (int i = 0; i < list.size(); i++) {
				csvWriter.writeNext(list.get(i));	
			}
			
			csvWriter.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 读取csv文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static List<String[]> readCSVFile(String filePath) {
		List<String[]> listCSV = null;
//		List<String> list = new ArrayList<>();
		try {
			FileInputStream fileInputStream = new FileInputStream(filePath);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			CSVReader csvReader = new CSVReader(bufferedReader);

			listCSV = csvReader.readAll();
//			for (String[] tempString : listCSV) {
//				for (String temp : tempString) {
//					if (null != temp && !temp.equals("")) {
//						list.add(temp);
//					}
//				}
//			}

			csvReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listCSV;
	}

	public static void main(String[] args) {

		CsvUtil csvUtil = new CsvUtil();
		String sql = "SELECT * FROM bbs_comment WHERE source_id = '52997'";
		List<Map<String, String>> list = csvUtil.selects(sql);
		
		List<String[]> list2 = new ArrayList<>();
		String[] temp = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			temp[i] = list.get(i).get("id");
		}
		list2.add(temp);
		wirteCSVFile("/Users/jimmy/Desktop/writer.csv", list2, true);
		
//		wirteCSVFile("/Users/jimmy/Desktop/writer.csv", list, true);
		

//		List<String[]> listRead = readCSVFile("/Users/jimmy/Desktop/writer.csv");
//		System.out.println(listRead);

	}
}
