package com.xxx.yyy.spring.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Reader and Writer（读取多个txt文件，去掉重复元素，再写入一个新的txt文件。）
 *
 * @author: maoyan
 * @date: 2021/12/4 12:19:11
 * @description:
 */
public class FileUtil {

    public static final String separator = File.separator;

    /**
     * 读取指定目录下的txt文件，并添加到set里面（即去掉重复元素）
     *
     * @param path
     * @return setStr
     */
    public static Set<String> Reader(String path) {
        Set<String> setStr = new HashSet<>();
        try {
            String encoding = "GBK";
            File file = new File(path);
            String[] fileList = file.list();

            for (String fl : fileList) {
                String newPath = path + separator + fl;
                // 考虑到编码格式
                InputStreamReader read = new InputStreamReader(new FileInputStream(newPath), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if (!lineTxt.trim().equals("")) {
                        setStr.add(lineTxt);
                    }
                }
                read.close();
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return setStr;
    }

    /**
     * 将set里面的数据写入到一个新的txt文件里面
     *
     * @param str
     */
    public static void Writer(Set<String> str,String path) {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(path + separator +"Result.txt");
            int count = 0;
            for (String string : str) {
                if (!string.trim().equals("")) {
                    count++;
                    fileWriter.write(string.trim() + "\r\n");
                }
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String path = "D:\\Stop words";
        path = "/Users/maoyan/Desktop/未命名文件夹";
        System.out.println("set>>>" + Reader(path));
        Writer(Reader(path),path);

        System.out.println(System.getProperty("file.separator"));

        System.out.println(File.separator);
    }
}
