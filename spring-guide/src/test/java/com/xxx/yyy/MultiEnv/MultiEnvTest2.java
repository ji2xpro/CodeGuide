package com.xxx.yyy.MultiEnv;

import org.springframework.beans.factory.annotation.Value;
import org.testng.annotations.Test;

/**
 * @Auther: maoyan
 * @Date: 2019/6/20 15:00
 * @Description:
 */
public class MultiEnvTest2 extends TestCaseRun {

    @Value("${url}")
    private String url;

    @Test
    public void test(){
        System.out.println(url);

    }
}
