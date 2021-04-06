package com.example.demo.javase;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 是什么
 * AQS为什么是JUC内容的重要的基石
 * 字面意思
 * 抽象的队列同步器 - > 队列先进先出；有单链表和双链表
 * 位置在java.util.concurrent.locks
 * AbstractQueuedSynchronizer
 * AbstractOwnableSynchronizer  简称 AQS
 * AbstractQueuedLongSynchronizer
 * 技术解释
 * 是用来构建锁或这其它同步器组件的重量级基础框架及整个JUC体系的基石，
 * 通过内置的FIFO队列来完成资源获取线程的队列工作，并通过一个int类型变量表示持有锁的状态
 * <p>
 * 和aqs相关的
 * ReentrantLock
 * CountDownLatch
 * ReentrantReadWriteLock
 * Semaphore
 * <p>
 * 进一步理解锁和同步器的关系
 * 锁：面向锁的使用者
 * 同步器：面向锁的实现者
 * <p>
 * 能干啥
 * AQS初步
 * 有阻塞就需要排队，实现排队必然需要队列-》AQS使用一个volatile的int类型的成员变量来表示同步状态，通过内置的
 * FIFO对列来完成资源获取的队列工作将每条要去抢占资源的线程封装成一个node节点来实现锁的分配，通过CAS完成对State值的修改
 * 从我们的ReentrantLock开始解读AQS
 * <p>
 * 解读AQS
 * 1、lock借口实现类，基本都是通过【聚合】了一个【队列同步器】的子类完成线程访问控制的
 * <p>
 * 前置知识
 * 公平锁和非公平锁
 * 可重入锁
 * LockSupport
 * 自旋锁
 * 数据结构之链表
 * 设计模式之模版设计模式
 */
public class AQSDemo {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        //带入一个银行办理业务的案例来模拟我们的AQS如何进行线程的管理和通知唤醒机制

        // 3个线程模拟3个人来银行窗口办理业务

        // A顾客就是第一个顾客，可以直接办理业务
        new Thread(() -> {
            lock.lock();

            try {
                System.out.println(Thread.currentThread().getName() + "\t" + "come in");
                //办理时间比较长
                try {
                    TimeUnit.MINUTES.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {

                lock.unlock();
            }
        }, "A").start();


        // B-顾客，由于受理窗口只有一个，b顾客只能等待
        // 进入侯客厅
        new Thread(() -> {
            lock.lock();

            try {
                System.out.println(Thread.currentThread().getName() + "\t" + "come in");

            } finally {

                lock.unlock();
            }
        }, "B").start();


        // C-顾客，由于受理窗口只有一个，b顾客只能等待
        // 进入侯客厅
        new Thread(() -> {
            lock.lock();

            try {
                System.out.println(Thread.currentThread().getName() + "\t" + "come in");

            } finally {

                lock.unlock();
            }
        }, "C").start();

    }
}
