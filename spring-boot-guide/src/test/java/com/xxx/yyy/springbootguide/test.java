package com.xxx.yyy.springbootguide;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: maoyan
 * @date: 2020/3/1 14:03:23
 * @description:
 */
public class test {

    @Test
    public void test1(){
//        int x = 10;
//        x += x -= x-x;
//        System.out.println(x);


        System.out.println( ~(1 << 31));
        System.out.println(Integer.MAX_VALUE);
        System.out.println(1<<12);
//        System.out.println(letterCombinations("2"));
    }




        private static Map<String, String> phone = new HashMap<>();
        static {
            phone.put("2", "abc");
            phone.put("3", "def");
            phone.put("4", "ghi");
            phone.put("5", "jkl");
            phone.put("6", "mno");
            phone.put("7", "pqrs");
            phone.put("8", "tuv");
            phone.put("9", "wxyz");
        };
        List<String> ans = new ArrayList<>();
        public void backtrack(String combination,String nextDigits) {
            if(nextDigits.length()==0) {//4.数字遍历完
                ans.add(combination);//6.放入ans
                return;//7.回溯
            }
            String digit = nextDigits.substring(0, 1);//1.获得剩下数字中的第一个
            String letters = phone.get(digit);//2.获得数字映射的字母字符串
            for(int i=0;i<letters.length();i++)
                //3.将字母字符串中的一个字符放入combination,并传入剩余的数字，进行迭代
                backtrack(combination+letters.substring(i,i+1), nextDigits.substring(1));
        }
        public List<String> letterCombinations(String digits) {
            if (digits.length() != 0)
                backtrack("", digits);
            return ans;
        }






//    public List<String>letterCombinations(String digits){
//        List<String> res=new ArrayList<>();
//        if(digits==null||digits.equals(""))
//            return res;
//        HashMap<Character,char[]> map=new HashMap<>();
//        map.put('0',new char[]{});
//        map.put('1',new char[]{});
//        map.put('2',new char[]{'a','b','c'});
//        map.put('3',new char[]{'d','e','f'});
//        map.put('4',new char[]{'g','h','i'});
//        map.put('5',new char[]{'j','k','l'});
//        map.put('6',new char[]{'m','n','o'});
//        map.put('7',new char[]{'p','q','r','s'});
//        map.put('8',new char[]{'t','u','v'});
//        map.put('9',new char[]{'w','x','y','z'});
//        StringBuilder str=new StringBuilder();
//        findComb(digits,map,res,str);
//        return res;
//    }
//    public void findComb(String digits,HashMap<Character,char[]>map,List<String>res,StringBuilder str){
//        if(str.length()==digits.length()){//str的长度等于digits的长度证明是其中一个结果
//            res.add(str.toString());
//            return;
//        }
//        for(char c:map.get(digits.charAt(str.length()))){
//            str.append(c);
//            findComb(digits,map,res,str);//递归回溯找出所有的结果
//            str.deleteCharAt(str.length()-1);//每找出一个结果后，str的长度减一，添加下一种可能
//        }
//    }
}
