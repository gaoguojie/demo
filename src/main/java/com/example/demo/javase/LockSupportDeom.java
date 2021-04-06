package com.example.demo.javase;


import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1、为啥要学习lockSupport?
 *
 * 1.1 java - > jvm
 * 1.2 juc - > AQS - > (前置知识 可重入锁、lockSupport-->线程等待唤醒机制（wait/notify）)
 * 是什么？
 * 用于创建锁和其他同步类的基本线程阻塞原语
 * 能干啥？
 * lockSupport 中的park()和unpark()的作用分别是阻塞线程和接触阻塞线程；park == wait;unpark == notify
 * lockSupport 类使用了一种名为Permit（许可）的概念来做到阻塞和唤醒线程的功能，每一个线程都有一个许可（permit）
 * permit 只有两个值0和1，默认是0
 * 可以把许可看成一种（0，1）信号量（Semaphore），但于Semaphore不同的是，许可的累加上限是1
 * 去拿下？
 * 不用下载，jdk自带
 * 怎么玩？
 *
 *3中让线程等待和唤醒的方法
 * 3.1 : 使用Object中的wait()方法让线程等待，使用Object中的notify()方法唤醒线程
 * 3.2 : 使用JUC包中Condition的await()方法让线程等待，使用signal()方法唤醒线程
 * 3.3 : LockSupport类可以阻塞当前线程以及唤醒制定被阻塞的线程；使用park()方法让线程等待，使用unpark()方法唤醒线程
 *
 * 传统的synchronized和lock实现等待唤醒通知的约束
 * 1：线程先要获的并持有锁，必须在锁块（synchronized 或 lock）中
 * 2：必须要先等待后唤醒，线程才能被唤醒
 *
 * 2、学习方法
 * 2.1 是什么？
 * 2.2 能干啥？
 * 2.3 去哪下
 * 2.4 怎么玩
 *
 *
 * 3、ab ----> after - > before
 */
public class LockSupportDeom {

    static Object object = new Object();

    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) {
//         sy();
//        lock1();
        lockSupportDemo1();
    }


    //synchronized 使用wait和notify

    /**
     * 异常1
     * wait()和notify()方法，两个都去掉同步代码块
     * 异常情况
     * Exception in thread "A" java.lang.IllegalMonitorStateException
     * Exception in thread "B" java.lang.IllegalMonitorStateException
     * 结论
     * Object中的wait、notify、notifyAll用于线程等待和唤醒的方法，都必须在synchronized内部执行（必须用到关键字synchronized）
     *
     * 异常2
     * 将notify放到wait前面执行，b线程线notify了，3秒后a线程在wait方法
     * 异常情况
     * 程序无法执行，无法唤醒
     * 结论：
     * 线wait后notify、notifyAll方法，等待中的线程才会被唤醒，否则无法唤醒
     *
     */
    static void sy(){
        new Thread(()->{
           synchronized (object){
                System.out.println(Thread.currentThread().getName() + "\t" + "-----come in ");
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t" + "----被唤醒");
           }
        }, "A").start();

        new Thread(()->{
            synchronized (object){
                object.notify();
                System.out.println(Thread.currentThread().getName() + "\t" + "=====通知线程A");
            }
        },"B").start();

    }


    static void lock1(){
        new Thread(()->{
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t" + "======come in");
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "----被唤醒");
        },"A").start();


        new Thread(()->{
            lock.lock();
            try {
                condition.signal();
                System.out.println(Thread.currentThread().getName() + "\t" + "------通知线程A");
            } finally {
                lock.unlock();
            }
        },"B").start();
    }

    /**
     * 正常+无锁块
     *
     * 之前错误的先唤醒在等待，locksupport照样支持
     *
     * LockSupport是一个阻塞工具类，所有方法都是静态方法，可以在线程任意位置阻塞，阻塞之后也有对应的唤醒方法。
     * 归根结底，LockSupport调用的unsafe中的native代码
     *
     * lockSupport 提供park和unpark实现阻塞线程和解除线程阻塞的过程
     * lockSupport 和每个使用它的线程都有一个许可（permit）关联。permit相当于1，0的开关，默认是0；
     * 调用一次unpark就加1变成1
     * 调用一次park会消费unpark,也就是将1变成0，同时park立即返回
     * 如果再次调用park会变成阻塞（因为permit为0会阻塞，一直到permit为1），这时调用unpark会把permit设置为1
     * 每一个线程都有一个相关的permit，permit最多有一个，重复调用unpark不会积累凭证
     *
     * 形象的理解
     * 线程阻塞需要消耗凭证（permit），这个凭证最多只有一个
     * 当调用park方法
     *      如果有凭证，则会直接消耗掉这个凭证然后正常退出
     *      如果无凭证，就必须阻塞等待凭证可用
     * 而unpark则相反，它会增加一个凭证，但凭证最多只有一个，累加无效
     *
     */
    static  void lockSupportDemo1(){
        Thread t1 = new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "\t" + "=========come in");
            LockSupport.park();//被阻塞。。。。等待通知被放行，它要通过需要许可证
            System.out.println(Thread.currentThread().getName() + "\t" + "=========唤醒");
        },"t1");
        t1.start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t2 = new Thread(()->{
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName() + "\t" + "=========通知T1线程");
        },"t2");
        t2.start();
    }
}
