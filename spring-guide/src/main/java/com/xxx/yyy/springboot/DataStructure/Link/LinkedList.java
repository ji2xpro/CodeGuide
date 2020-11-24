package com.xxx.yyy.springboot.DataStructure.Link;

public class LinkedList {
	
	private Node first;
	private Node last;
	
	private int size;
	   
	public int size() {
		return size;
	}
	
	public void add(Object obj) {
		Node node = new Node();
		if (first == null) {
			node.setPrevious(null);
			node.setObject(obj);
			node.setNext(null);
			
			first = node;
			last = node;
		} else {
			node.setPrevious(last);
			node.setObject(obj);
			node.setNext(null);
			
			last.setNext(node);
		}
		size++;
	}
	
	
	
	

}
