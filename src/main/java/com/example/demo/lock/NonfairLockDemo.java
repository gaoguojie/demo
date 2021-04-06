package com.example.demo.lock;

import java.util.concurrent.locks.ReentrantLock;

public class NonfairLockDemo {

    private ReentrantLock lock = new ReentrantLock(false);


    public void testLock(){

        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "获得类锁");
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        NonfairLockDemo nonfairLockDemo = new NonfairLockDemo();
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName()+"启动");
            nonfairLockDemo.testLock();
        };
        Thread[] threadArray = new Thread[20];
        for (int i=0; i<20; i++){
            threadArray[i] = new Thread(runnable);
            threadArray[i].start();
        }
    }
}
