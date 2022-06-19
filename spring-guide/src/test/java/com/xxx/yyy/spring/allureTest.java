package com.xxx.yyy.spring;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @Auther: maoyan
 * @Date: 2019/7/15 14:32
 * @Description:
 */
public class allureTest {

    @Description("报表汇总数据查询")
    @Story("报表基础数据")
    @Step("123456789")
    @Test
    public void all(){
        Assert.assertEquals(2,1,"成功");
        getInfo("测试步骤");
    }

    @Step("{url}")
    public void getInfo(String url){

    }
}
