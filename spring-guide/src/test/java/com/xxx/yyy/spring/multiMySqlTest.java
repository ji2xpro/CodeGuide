package com.xxx.yyy.spring;

import com.xxx.yyy.spring.utils.DateUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author: maoyan
 * @date: 2020/12/16 11:47:34
 * @description:
 */
public class multiMySqlTest {
    private final static String url = "jdbc:mysql://10.72.108.63:5002/myshowfans";
    private final static String name = "com.mysql.jdbc.Driver";
    private final static String user = "myshowfans";
    private final static String password = "myshowfans";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName(name);//指定连接类型
        Connection conn = DriverManager.getConnection(url, user, password);//获取连接
        if (conn != null) {
            System.out.println("获取连接成功");
            insert(conn);
        } else {
            System.out.println("获取连接失败");
        }
    }

    public static void insert(Connection conn) throws SQLException {
        // 开始时间
        Long begin = new Date().getTime();
        // sql前缀
        String prefix = "INSERT INTO sheep_inside_code (id,inside_code) VALUES ";
        prefix = "INSERT INTO `Fans_Comment` (`Id`, `UgcGlobalId`, `SubjectId`, `RefId`, `Level`, `IdolId`, `BrokerId`, `UserId`, `Platform`, `CommentType`, `Status`, `IsDelete`, `Creator`, `Updater`, `CreateTime`, `UpdateTime`) VALUES ";

        // 保存sql后缀
        StringBuffer suffix;
        // 设置事务为非自动提交
        conn.setAutoCommit(false);
        // 比起st，pst会更好些
        PreparedStatement pst = conn.prepareStatement(" ");//准备执行语句
        // 外层循环，总提交事务次数
        long index = 5008029;
        long userID = 9000006005837L;
        for (int i = 1; i <= 10; i++) {
            suffix = new StringBuffer();

            for (int j = 1; j <= 10000; j++) {
                index+=j;
                userID+=j;
                suffix.append("(" + index + ", 0, 631, 631, 1, 0, 195, " + userID + ",3, 2, 1, 0, " + userID + ", " + userID + ", '" + DateUtil.getTodayTime(DateUtil.DATE_TIME_HOUR_MINUTE_SECOND) + "', '" + DateUtil.getTodayTime(DateUtil.DATE_TIME_HOUR_MINUTE_SECOND) + "'),");
            }

            // 构建完整SQL
            String sql = prefix + suffix.substring(0, suffix.length() - 1);
            // 添加执行SQL
            pst.addBatch(sql);
            // 执行操作
            pst.executeBatch();
            // 提交事务
            conn.commit();
            // 清空上一次添加的数据
            suffix.setLength(0);
        }
        // 头等连接
        pst.close();
        conn.close();
        // 结束时间
        Long end = new Date().getTime();
        // 耗时
        System.out.println("10万条数据插入花费时间 : " + (end - begin) / 1000 + " s");
        System.out.println("插入完成");
    }
}
