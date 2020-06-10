package com.xxx.yyy;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @Auther: maoyan
 * @Date: 2019/6/28 11:30
 * @Description:
 */
public class otherTest1 {
    private static String id = "11";
//    @Test
//    public void test(){
//        if (id.equals("11")){
//            return;
//        }
//        System.out.println(id);
//    }


    @Test
    public void test123()
    {
        Assert.assertEquals( 6, 6,">>>>>>>>>> test2");
    }
}
