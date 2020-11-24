package com.xxx.yyy.springboot.JavaTest;


public class ThreadTest {
    public static void main(String[] args)  {
        MyThread thread = new MyThread();
        thread.start();
    }
}

class MyThread extends Thread {
    private static int num = 0;
    public MyThread(){
        num++;
    }
    @Override
    public void run() {
        System.out.println("主动创建的第"+num+"个线程");
    }
}


