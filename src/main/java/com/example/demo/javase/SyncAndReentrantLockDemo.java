package com.example.demo.javase;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SyncAndReentrantLockDemo {

    /**
     * *
     * * 题目：synchronous 和 lock 有什么区别？用新的lock有什么好处？
     * *
     * 1、原始构成
     * <p>
     * synchronized是 关键字属于JVM层面
     * <p>
     * monitorenter 底层是通过monitor对象来完成，其实wait/notify等方法也依赖与monitor对象只有在同步块或方法中才能调用wait/notify等方法
     * <p>
     * mointorexit。
     * <p>
     * lock 是具体类java.util.concurrent.locks.lock 是api层面的锁
     * <p>
     * <p>
     * <p>
     * 2、使用方法
     * <p>
     * synchronized 不需要用户区手动释放锁，当synchronized代码执行完后系统会自动让线程释放对锁的占用
     * <p>
     * reentrantlock 则需要用户手动释放锁若没有主动释放锁，就有可能导致出现死锁现象。需要lock和unlock方法配合try/finally语句块来完成
     * <p>
     * <p>
     * <p>
     * 3、等待是否可中断
     * <p>
     * synchronized 不需要用户区手动释放锁，当synchronized代码执行完后系统会自动让线程释放对锁的占用
     * <p>
     * ReentrantLock 可中断；1.设置超时方法tryLock（long timeout， timeunit unit）
     * <p>
     * 2.lockInterruptibly（）方法代码块中，调用interrupt（）方法可中断
     * <p>
     * <p>
     * <p>
     * 4、加锁是否公平
     * <p>
     * synchronized 非公平锁
     * <p>
     * ReentrantLock 默认非公平锁，构造方法可以传boolean值，true为公平锁，false为非公平锁
     * <p>
     * <p>
     * <p>
     * 5、锁绑定多个条件condition
     * <p>
     * synchronized 没有
     * <p>
     * ReentrantLock 用来实现分组唤醒需要唤醒的线程，可以精确唤醒，而不是想synchronized要么随机唤醒一个线程要么唤醒全部线程。
     * <p>
     * 题目：多线程之间顺序调用，实现A-》B-》C三个线程启动，要求如下：
     * A 打印 5次，B打印10次，C打印15次
     * 紧接着
     * A 打印 5次，B打印10次，C打印15次
     * ....
     * 来10轮
     */


    static class ShareResource {
        // a-1;b-2;c-3
        private int number = 1;
        Lock lock = new ReentrantLock();
        Condition a = lock.newCondition();
        Condition b = lock.newCondition();
        Condition c = lock.newCondition();



        private void print5() {
            lock.lock();
            try {
                // 1、判断
                while (number != 1) {
                    a.await();
                }
                // 2、干活
                for (int i = 1; i <= 5; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t ----" + i);
                }
                // 3、通知
                number = 2;
                b.signal();
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                lock.unlock();
            }
        }

        private void print10() {
            lock.lock();
            try {
                // 1、判断
                while (number != 2) {
                    b.await();
                }
                // 2、干活
                for (int i = 1; i <= 10; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t ----" + i);
                }
                // 3、通知
                number = 3;
                c.signal();
            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                lock.unlock();
            }
        }

        private void print15() {
            lock.lock();
            try {
                // 1、判断
                while (number != 3) {
                    c.await();
                }
                // 2、干活
                for (int i = 1; i <= 15; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t ----" + i);
                }
                // 3、通知
                number = 1;
                a.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }

    public static void main(String[] args) {


        ShareResource shareResource = new ShareResource();

        new Thread(() -> {
            for (int j = 1; j <= 10; j++) {
                shareResource.print5();
            }

        }, "A").start();


        new Thread(() -> {
            for (int j = 1; j <= 10; j++) {
                shareResource.print10();
            }

        }, "B").start();


        new Thread(() -> {
            for (int j = 1; j <= 10; j++) {
                shareResource.print15();
            }

        }, "C").start();
    }

}
