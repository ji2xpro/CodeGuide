package com.xxx.yyy.spring.MultiEnv;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Auther: maoyan
 * @Date: 2019/6/20 15:00
 * @Description:
 */
public class MultiEnvTest {

    @Test
    public void test(){
        try {
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream("env.properties");
            Properties properties = new Properties();
            properties.load(stream);
            System.out.println(properties.getProperty("test.properties"));
            System.out.println(properties.getProperty("host"));
            Assert.assertEquals("1","1","未炸到");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
