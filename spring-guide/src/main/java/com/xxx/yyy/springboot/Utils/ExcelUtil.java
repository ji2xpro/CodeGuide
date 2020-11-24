package com.xxx.yyy.Utils;

import java.util.LinkedList;
import java.util.List;

/**
 * @Auther: maoyan
 * @Date: 2019/8/11 14:59
 * @Description:
 */
public class ExcelUtil {
    public static void createExceReport(String path,String fileName){
        String excelPath = getExcelPath(path, fileName);

    }


    private static String getExcelPath(String path,String fileName){
        if (fileName.indexOf("/") != -1 || fileName.indexOf("xls") != -1 || fileName.length() < 1) {
            fileName = "未命名";
        }
        path += "/"+ fileName + DateUtil.getTodayTime(DateUtil.DATE_FORMAT) + "xlsx";
        return path;
    }


    private static List<String> getTitle(){
        LinkedList<String> title = new LinkedList<>();

        title.add("用例描述");
        title.add("接口名");
        title.add("步骤描述");
        title.add("状态");
        title.add("详细信息");
        title.add("执行时间");

        return title;
    }

    public static void main(String[] args) {
        getExcelPath("/Users/maoyan/Test","213.xls");
    }
}
