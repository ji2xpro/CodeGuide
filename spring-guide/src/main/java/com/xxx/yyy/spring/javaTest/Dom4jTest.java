package com.xxx.yyy.spring.javaTest;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class Dom4jTest {
	
	public Element dom4jTest() {
		Element root = null;
		try {
			//创建SAXReader对象
			SAXReader saxReader = new SAXReader();
			//读取文件转换Document
			String path = System.getProperty("user.dir") +"/src/test/resources/students.xml";
			Document document = saxReader.read(new File(path));
			//获取根节点元素对象
			root = document.getRootElement();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return root;
	}

	//遍历当前节点下的所有节点  
	public void listNodes(Element element) {
		System.out.println("当前节点的名称：" + element.getName());
		//首先获取当前节点的所有属性节点
		List<Attribute> list = element.attributes();
		//遍历属性节点
		for (Attribute attribute : list) {
			System.out.println("属性" + attribute.getName() + "------" + attribute.getValue());
		}
		//如果当前节点内容不为空，则输出
		if (!(element.getTextTrim().equals(""))) {
			System.out.println(element.getName() + ":::" + element.getText());
		}
		//使用递归，迭代当前节点下面的所有子节点
		Iterator<Element> iterator = element.elementIterator();
		while (iterator.hasNext()) {
			Element e = iterator.next();
			listNodes(e);
		}
	}
	
	//添加、删除节点属性
	public void nodeUpdate(Element element) {
		System.out.println("--------添加属性前--------");
		//获取节点student1
		Element newElement = element.element("student1");
		//遍历
		listNodes(newElement);
		//获取其属性
		Attribute attribute = newElement.attribute("id");
		//删除其属性
		newElement.remove(attribute);
		//为其添加新属性
		newElement.addAttribute("name", "新节点属性");
		System.out.println("--------添加属性后--------");
		listNodes(newElement);
	}
	
	
	
	@Test
	public void nodeQuery() {
		Element element = dom4jTest();
		//遍历
		listNodes(element);
	}
	
	@Test
	public void nodeOperation() {
		Element element = dom4jTest();
		nodeUpdate(element);
	}

}
