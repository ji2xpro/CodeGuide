package com.xxx.yyy.spring.dataStructure.hashMap;

public class Student {
	private String name;
	private String no;
	private double score;

	public Student() {
	}
	
	public Student(String name, String no, double score) {
		this.name = name;
		this.no = no;
		this.score = score;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
	

}
