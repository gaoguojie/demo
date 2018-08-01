package com.example.demo.thread;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 线程的等待和通知
 *
 * @author gaoguojie
 * @version 1.0 2018/07/06
 */
public class WaitNotify {

    static boolean flag = true;
    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread waitThread = new Thread(new Wait(), "waithThread");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);
        Thread notifyThread = new Thread(new Notify(), "notifyThread");
        notifyThread.start();
    }

    static class Wait implements Runnable {

        @Override
        public void run() {

            //setp 1、加锁，拥有lock 的 monitor
            synchronized (lock) {
                System.out.println("111111");
                // setp 2、当条件不满足，继续wait，同事释放了 lock 的锁
                while (flag) {
                    try {
                        System.out.println(Thread.currentThread() + "flag is ture. wait @ "
                                + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        lock.wait();
                        System.out.println("333333");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //setp 3、条件满足时，完成工作
                System.out.println(Thread.currentThread() + "flag is false. running @"
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
            }
        }
    }

    static class Notify implements Runnable {

        @Override
        public void run() {
            //setp 1、加锁，拥有lock 的 monitor
            synchronized (lock) {
                //setp 2、获取lock锁，然后进行通知，通知不会释放lock的锁，
                // 直到当前线程释放了lock后，waithread 才能从wait方法中返回
                System.out.println(Thread.currentThread() + "hold lock . notify @"
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                lock.notify();
                flag = false;
                SleepUtils.second(5);
            }
            System.out.println("22222");
            //setp 3、 再次加锁
            synchronized (lock) {
                System.out.println(Thread.currentThread() + "hold lock agin sleep.@"
                        + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                SleepUtils.second(5);
            }
        }
    }
}
