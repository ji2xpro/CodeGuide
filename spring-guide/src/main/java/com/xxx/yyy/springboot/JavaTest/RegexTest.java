package com.xxx.yyy.springboot.JavaTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
	
	/**
	 * 全部匹配
	 * @param string
	 */
	public static void regexTest(String string) {
		// 将字符串编译为正则表达式的对象表示形式 
		Pattern pattern =  Pattern.compile("a");
		// 创建对字符串 string 根据正则表达式 pattern 进行匹配操作的匹配器对象
		Matcher matcher = pattern.matcher(string);
		// 查找下一个匹配的字符串内容，如果找到返回 true，找不到返回 false
		while (matcher.find()) {
			System.out.println(matcher.group());
		}	
	}
	
	/**
	 * 忽略大小写匹配
	 * @param string
	 */
	public static void regexTest1(String string) {
		// 在编译表达式时使用标记 CASE_INSENSITIVE，使表达式忽略大小写        
		 Pattern pattern = Pattern.compile("cat", Pattern.CASE_INSENSITIVE);
		 Matcher matcher = pattern.matcher(string);
		 while(matcher.find()) {                                               
		     System.out.println(matcher.group());
		 }     
	}
	
	public static void regexText2() {
		// 判断字符串是否一个合法的16进制                                        
		String regex = "[-+]?0[xX]?[0-9a-fA-F]+";
		System.out.println("0xFF".matches(regex));  // true
		System.out.println("-0Xff".matches(regex)); // true
		System.out.println("ff".matches(regex));    // false
		System.out.println("0x1H".matches(regex));  // false
		// 简单地判断一个字符串是否合法的身份证号码                              
		regex = "\\d{15}|\\d{18}";                                               
		System.out.println("440104700101001".matches(regex));    // True;
		System.out.println("44010700101001".matches(regex));     // false;
		System.out.println("440104197001010015".matches(regex)); // True;
		System.out.println("4401041970010100015".matches(regex));// false;
	}
	
	/**
	 * Greedy 贪婪匹配
	 */
	public static void regexTest3() {
		Pattern p = Pattern.compile(".*foo");
		Matcher m = p.matcher("xfooxxxxxxfoo");
		while (m.find()) {
			System.out.println(m.group());
		}
	}
	
	/**
	 * Reluctant 懒惰匹配
	 */
	public static void regexTest4() {
		Pattern p = Pattern.compile(".*?foo");
		Matcher m = p.matcher("xfooxxxxxxfoo");
		while (m.find()) {
			System.out.println(m.group());
		System.out.println("matched form " + m.start() + " to " + m.end());
		}
	}
	
	/**
	 * Possessive 独占匹配
	 */
	public static void regexTest5() {
		Pattern p = Pattern.compile(".*+foo");
		Matcher m = p.matcher("xfooxxxxxxfoo");
		while (m.find()) {
			System.out.println(m.group());
		}
	}
	
	public static void main(String[] args) {
//		regexTest("Jack is a boy");
//		regexTest1("About Cats and dogs");
//		regexText2();
//		regexTest3();
//		regexTest4();
		regexTest5();
	}

}
