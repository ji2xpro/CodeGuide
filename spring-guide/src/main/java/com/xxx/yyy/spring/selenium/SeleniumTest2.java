package com.xxx.yyy.spring.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTest2 {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "/Users/maoyan/Downloads/chromedriver");
		WebDriver driver = new ChromeDriver();

		// 让浏览器访问 Baidu
		driver.get("http://www.baidu.com");
		// 用下面代码也可以实现
		// driver.navigate().to("http://www.baidu.com");

		// 浏览器窗口变大
		driver.manage().window().maximize();

		// 获取 网页的 title
		System.out.println("1 Page title is: " + driver.getTitle());

		// 定位输入框元素
		WebElement txtbox = driver.findElement(By.name("wd"));
		
	
		
	

		// 在输入框输入文本
		txtbox.sendKeys("HelloWorld");

		// 定位按钮元素
		WebElement btn = driver.findElement(By.id("su"));
		

		// 点击按钮
		btn.click();

		// 关闭驱动
		driver.close();
		
	}
}
