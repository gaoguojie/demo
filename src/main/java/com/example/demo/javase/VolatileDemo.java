package com.example.demo.javase;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class VolatileDemo {

    static class MyData{
        volatile int number =0;
        public void addTo60 () {
            this.number = 60;
        }

        public void addPlusPlus(){
            number ++;
        }

        AtomicInteger atomicInteger = new AtomicInteger();
        public void atomicPlus(){
            atomicInteger.incrementAndGet();
        }
    }

    /**
     * 1、验证volatile的可见性
     * 1.1假如 int number =0;number 变量没有volatile没有修饰,没有可见性
     * 1.2添加了volatile，可以解决可见性问题
     * 2、验证volatile不保证原子性
     * 2.1原子性是什么意思？
     *      不可分割，完整性，即某个线程正在做某个具体任务时，中间不可以加塞或者被分割。
     *      需要整体完整要么同时成功，要么同时失败。
     * 2.2volatile是否保证原子性，不保证
     * 2.3 why
     *    线程竞争激烈，导致主内存数据变更时还没有来得及通知其它线程，导致线程数据刷新覆盖导致数据丢失。
     * 2.4 如何保证原子性
     *      加syn
     *      使用JUC下面的atomicInteger
     * @param args
     */
    public static void main(String[] args) {
        MyData myData = new MyData();
        for (int i=0; i<20; i++){
            new Thread(()->{
                for (int j=0; j<1000; j++){
                    myData.addPlusPlus();
                    myData.atomicPlus();
                }
            }).start();
            System.out.println("-----------");
        }

        //需要等待上面20个线程执行完成，再用mian线程查看最终结果是多少
        //一个是mian线程，一个是垃圾回收线程
        while(Thread.activeCount() >2) {
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() +"\t int type,finally number value: " + myData.number);
        System.out.println(Thread.currentThread().getName() +"\t int atomic finally number value: " + myData.atomicInteger);


    }

    //volatile 可以保证原子性，即时通知其它线程，主物理内存的值以修改
    public static void seeOkVolatile(){
        MyData myData = new MyData();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t----come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            myData.addTo60();

            System.out.println(Thread.currentThread().getName()+"\t update number value " + myData.number);
        },"t1").start();


        while(myData.number == 0){
            // main 线程就是一直等待循环
        }

        System.out.println(Thread.currentThread().getName() +"\t mission is over");
    }
}
