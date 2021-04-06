package com.example.demo.javase;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone implements Runnable {
    public synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getId() + "\t invoked sendSMS()");
        sendEmail();
    }

    public synchronized void sendEmail() {
        System.out.println(Thread.currentThread().getId() + "\t ------invoked sendEmail()");

    }

    Lock lock = new ReentrantLock();

    @Override
    public void run() {

    }

    public void get() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getId() + "\t invoked get()");
            set();
        } finally {
            lock.unlock();
        }
    }

    public void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getId() + "\t -------invoked set()");
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 可重入锁也叫递归锁
 * 指的是同一个线程外层函数获得锁之后，内层递归函数仍然能获取该锁的代码，
 * 在同一个线程在外层方法获取锁的时候，在进入内层方法会自动获取锁
 * <p>
 * 也即是说，线程可以进入任何一个它以及拥有的锁所同步着的代码块
 * <p>
 * case one synchronized 就是一个典型的可重入锁
 * t1 invoked sendSMS() t1线程在外层方法获取锁的时候
 * t1 invoked sendEmail() t1线程在进入内层方法会自动获取锁
 * <p>
 * <p>
 * case two ReenterLock
 */
public class ReenterLockDemo1 {

    public static void main(String[] args) {

        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendSMS();
        }, "t1").start();

        new Thread(() -> {
            phone.sendSMS();
        }, "t2").start();


        new Thread(() -> {
            phone.get();
        }, "t3").start();

        new Thread(() -> {
            phone.get();
        }, "t4").start();
    }
}
