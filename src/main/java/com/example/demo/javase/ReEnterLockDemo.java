package com.example.demo.javase;


import com.example.demo.threadpool.ThreadPoolDemo2;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 *
 *
 *
 *
 * 可重入锁：可重复可递归调用的锁，在外层使用锁后，在内层仍然可以使用，并且不会发生死锁，这样的锁叫做可重入锁
 * 在一个synchronized修饰的方法代码块的内部
 * 调用本类的其它synchronized修饰的方法或代码块，是永远可以得到锁
 *
 * 可重入锁 加几次锁，就要释放几次，否则会影响后续的代码
 */
public class ReEnterLockDemo {

    static Object objectLock = new Object();

    public static void m1() {
        new Thread(() -> {
            synchronized (objectLock) {
                System.out.println(Thread.currentThread().getName() + "------------外层调用");
                synchronized (objectLock) {
                    System.out.println(Thread.currentThread().getName() + "------------中层调用");
                    synchronized (objectLock) {
                        System.out.println(Thread.currentThread().getName() + "------------内层调用");

                    }
                }
            }
        }, "input thread name").start();
    }

    public static void main(String[] args) {
        m1();
        m2();

        lock1();
    }


    public static synchronized void m2() {
        System.out.println("=====外");
        m3();
    }

    public static synchronized void m3() {
        System.out.println("=====中");
        m4();
    }

    public static synchronized void m4() {
        System.out.println("======内");
    }


    public static void lock1() {
        Lock lock = new ReentrantLock();
        System.out.println("33333");
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("========外");
                lock.lock();
                try {
                    System.out.println("===========内");
                } finally {
                    lock.unlock();
                }
            } finally {
                lock.unlock();
            }
        }, "input thread name lock").start();

        new Thread(()->{
            lock.lock();

            try {
                System.out.println("------------0000---第二次");
            } finally {

                lock.unlock();
            }
        },"input thread name lock2").start();

    }
}
