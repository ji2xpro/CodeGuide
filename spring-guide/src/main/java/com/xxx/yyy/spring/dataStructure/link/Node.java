package com.xxx.yyy.spring.dataStructure.link;

public class Node {
	private Node previous;
	private Object object;
	private Node next;
	
	public Node() {
		
	}

	public Node getPrevious() {
		return previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}
	
	
	
	
	

}
