package com.example.demo.lock;

import ch.qos.logback.core.net.SyslogOutputStream;

import java.util.concurrent.atomic.AtomicInteger;

public class ABADemo {

    public static AtomicInteger value = new AtomicInteger(1);

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            System.out.println("线程名称：" + Thread.currentThread().getName() + ",初始值 = " + value);

            try {
                //等待1秒，以便于干扰线程执行
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("11 value is : " + value);
            //cas操作
            boolean isCASSuccess = value.compareAndSet(1, 2);
            System.out.println("22 value is :" + value);
            System.out.println("线程名称：" + Thread.currentThread().getName() + ",cas 操作结果 ： " + isCASSuccess);
        }, "主操作线程");

        Thread thread2 = new Thread(() -> {
            // 确保thread1 线程优先执行
            Thread.yield();
            //a 加1 ， a+1=1+1=2
            value.incrementAndGet();
            System.out.println("线程名称：" + Thread.currentThread().getName() + ", 执行加操作，值=" + value);
            // a 减 1， a - 1 = 2 -1 =1
            value.decrementAndGet();
            System.out.println("线程名称：" + Thread.currentThread().getName() + ", 执行加操作，值=" + value);

        }, "干扰线程");

        thread1.start();
        thread2.start();
    }
}
