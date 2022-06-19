package com.xxx.yyy.spring.javaTest;

import java.util.HashMap;
import java.util.Map;

public class queryNum {
	public static Map<Character, Integer> query(String str) {
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		// 遍历字符串
		for (int x = 0; x < str.length(); x++) {
			// 获取字符
			char c = str.charAt(x);
			// 判断该字符是否存在集合
			if (!map.containsKey(c)) {
				// 不存在就存进去
				map.put(c, 1);
			} else {
				// 存在了就加一
				int count = map.get(c) + 1;
				map.put(c, count);
			}
		}
		return map;
	}
	
	public static void main(String[] args) {
		String str = "aaabbbbc";
		System.out.println(query(str));
	}
}
