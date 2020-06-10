package com.xxx.yyy;

import java.util.Arrays;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2019/10/14 17:47:52
 * @description:
 */
public class StringTest {
    private static <T extends Number & Comparable<? super T>> T min(T[] values) {
        if (values == null || values.length == 0)
            return null;
        T min = values[0];
        for (int i = 1; i < values.length; i++) {
            if (min.compareTo(values[i]) > 0) {
                min = values[i];
            }
        }
        return min;
    }

    public static void main(String[] args) {

        Integer[] myArray = {1, 2, 3,34};
        List myList = Arrays.asList(myArray);
        System.out.println(myList.size());// 1
        System.out.println(myList.get(0));// 数组地址值
        System.out.println(myList);
//            myList.add("1");
//            System.out.println(myList);
        System.out.println(myList.getClass());

    }
}
