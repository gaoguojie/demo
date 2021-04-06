package com.example.demo.javase;

import java.util.concurrent.CountDownLatch;

/**
 * countDownLatch
 * 让一些线程阻塞直到另一些线程完成一些列操作后才被唤醒
 *
 * CountDownLatch主要有两个方法，当一个或多个线程调用await方法时，调用线程会阻塞。
 *
 * 其它线程调用countDown方法会将计数器减1（调用countDown方法的线程不会阻塞），当计数器的值变为零时，因调用await方法被阻塞的线程会被唤醒，继续执行。
 */
public class CountDownLatchDemo {


    public static void main(String[] args) throws InterruptedException {

        CountDownLatch downLatch = new CountDownLatch(6);
        for (int i =1; i<=6; i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t，被灭亡！" );
                downLatch.countDown();
            }, CountryEnum.forEach_CountryEnum(i).getRetMessage()).start();
        }

//        if(Thread.activeCount() > 2 ){
//            Thread.yield();
//        }
        downLatch.await();

        System.out.println(Thread.currentThread().getName() +"\t *************** 秦国统一六国");
    }

    public void closeDoor() throws InterruptedException {
        CountDownLatch downLatch = new CountDownLatch(6);
        for (int i =1; i<=6; i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 上完自习，离开教室" );
                downLatch.countDown();
            },String.valueOf(i)).start();
        }

//        if(Thread.activeCount() > 2 ){
//            Thread.yield();
//        }
        downLatch.await();

        System.out.println(Thread.currentThread().getName() +"\t *************** 班长最后关门走人");
    }
}
