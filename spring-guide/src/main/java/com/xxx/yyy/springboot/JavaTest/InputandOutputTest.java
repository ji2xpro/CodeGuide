package com.xxx.yyy.springboot.JavaTest;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InputandOutputTest {
	
	/**
	 * 读入文件
	 * @param path
	 * @return
	 */
	public static String readFile(String path) {
		String result = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(path);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
			StringBuilder stringBuilder = new StringBuilder();
			String date = null;
			while ((date = bufferedReader.readLine()) != null) {
				stringBuilder.append(date + "\n");
			}
			
			result = stringBuilder.toString();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return result;
	}
	
	public static List<String> readFile1(String path) {
		List<String> list = new ArrayList<>();
		try {
			FileInputStream fileInputStream = new FileInputStream(path);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, "UTF-8"));
			String date = null;
			while ((date = bufferedReader.readLine()) != null) {
				String[] dateTemp = date.split("\\s+");
				date = dateTemp[0];
				list.add(date);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return list;
	}
	
	/**
	 * 读入excel文件
	 * @param path
	 * @return
	 */
	public static List<String> getExcel(String path) {
		List<String> list = new ArrayList<>();
		try {
			FileInputStream inp = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);
			int rowNum = sheet.getLastRowNum();// 获取的是最后一行的编号（编号从0开始）
			// int rows = sheet.getPhysicalNumberOfRows();//获取的是物理行数，不包括那些空行（隔行）的情况。
			// numTotal += rowNum;
			for (int icount = 1; icount < rowNum + 1; icount++) {
				Row row = sheet.getRow(icount);
				String msg = row.getCell(0).getStringCellValue();
				String bsflag = msg.split("=")[1];
				list.add(bsflag);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 写入文件
	 * @param path
	 * @param date
	 * @param flag
	 */
	public static void writeFile(String path, String date, Boolean flag) {
		try {
			File file = new File(path);
			
			if (!file.exists()) {
				System.out.println("创建新文件：" + path);
				file.createNewFile();
			} 
			
			FileOutputStream fileOutputStream = new FileOutputStream(path, flag);
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));
			bufferedWriter.write(date);
			bufferedWriter.close();
			
			System.out.println("文件已成功写入：" + path);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
