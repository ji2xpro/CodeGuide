package com.xxx.yyy.spring.javaTest;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
	static class myTask extends TimerTask {
		@Override
		public void run() {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			System.out.println("获取当前时间："+ simpleDateFormat.format(System.currentTimeMillis()));
		}
	}
	
	public static void main(String[] args) {
		Timer timer = new Timer();
		//在0秒后执行此任务,每次间隔2秒执行一次,如果传递一个Data参数,就可以在某个固定的时间执行这个任务.     
		timer.schedule(new myTask(), 0, 2000);	
		//这个是用来停止此任务的,否则就一直循环执行此任务 
		while (true) {
			try {
				int in = System.in.read();
				if (in == 's') {
					timer.cancel();
					break;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
