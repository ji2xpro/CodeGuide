package com.xxx.yyy.spring.javaTest;


import java.io.File;

public class FileTree {
	public static void main(String[] args) {
		//找一个自己硬盘上有用的文件夹
		File f = new File("/Users/jimmy/Downloads/Dubbo/");
		printFile(f, 0);
	}
	
	static void printFile(File file, int level){
		for (int i = 0; i < level; i++) {
			System.out.print("-");
		}
		System.out.println(file.getName());
		
		if(file.isDirectory()){
			File[]  files = file.listFiles();
			for (File temp : files) {
				printFile(temp, level+1);
			}
		}
	}
}

