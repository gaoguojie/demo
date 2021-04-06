package com.example.demo.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo2 {

   // public static AtomicInteger value = new AtomicInteger(1);
    private static AtomicStampedReference<Integer> value = new AtomicStampedReference<>(1,0);
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            System.out.println("线程名称：" + Thread.currentThread().getName() + ",初始值 = " + value.getReference());
            int stamp = value.getStamp();
            try {
                //等待1秒，以便于干扰线程执行
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("11 value is : " + stamp);
            //cas操作
            boolean isCASSuccess = value.weakCompareAndSet(1, 2, stamp, stamp + 1);
            System.out.println("22 value is :" + stamp);
            System.out.println("线程名称：" + Thread.currentThread().getName() + ",cas 操作结果 ： " + isCASSuccess);
        }, "主操作线程");

        Thread thread2 = new Thread(() -> {
            // 确保thread1 线程优先执行
            Thread.yield();
            //a 加1 ， a+1=1+1=2
            value.weakCompareAndSet(1,2,value.getStamp(), value.getStamp() +1);
            System.out.println("线程名称：" + Thread.currentThread().getName() + ", 执行加操作，值=" + value.getReference());
            // a 减 1， a - 1 = 2 -1 =1
            value.weakCompareAndSet(2,1,value.getStamp(), value.getStamp() + 1);
            System.out.println("线程名称：" + Thread.currentThread().getName() + ", 执行加操作，值=" + value.getReference());

        }, "干扰线程");

        thread1.start();
        thread2.start();
    }
}
