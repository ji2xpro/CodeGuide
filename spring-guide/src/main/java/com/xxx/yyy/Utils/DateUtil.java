package com.xxx.yyy.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: maoyan
 * @Date: 2019/8/12 12:01
 * @Description:
 */
public class DateUtil {
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String DATE_TIME_HOUR_MINUTE_SECOUND = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_TIME_HOUR_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";


    /**
     * 根据format格式生成当前日期
     * @param format
     * @return
     */
    public static String getTodayTime(String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

        return simpleDateFormat.format(new Date());
    }

    public static void main(String[] args) {
        System.out.println(getTodayTime(DATE_TIME_HOUR_MINUTE_FORMAT));
    }
}
