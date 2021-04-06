package com.example.demo.javase;


import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *      接口          工具类
 * 1、array          arrays
 * 2、collection     collections
 * 3、Executor       Executors
 *
 * 第一种：继承thread类
 * 第二种：实现Runnable接口，没有返回值
 * 第三种：实现Callable接口，有返回值
 * 第四种：获得/使用java多线程得方式，线程池
 *
 * 线程池参数如何设置
 * CUP密集型
 *
 * IO密集型
 *
 cup密集型的意思是该任务需要大量的运算，而没有阻塞，cup一直全速运行

 cup密集型任务只有在真正的多核CUP上才可能得到加锁（通过多线程）

 而在单核CUP上，无论你开几个模拟的多线程该任务都不可能得到加速，因为cup总的运算能力就那些。



 cup密集型任务配置尽可能少的线程数量；

 一般公式：CPU核数+1个线程的线程池





 IO密集型

 1、由于IO密集型任务线程并不是一直在执行任务，则应配置尽可能多的线程，如CPU核数*2



 2、IO密集型，即该任务需要大量的IO，即大量的阻塞

 在单线程上运行IO密集型的任务会导致浪费大量的CPU运算能力在等待。

 所以在IO密集型任务中使用多线程可以大大的加速程序运行，即使在单核CPU上，这种加速主要就是利用量被浪费掉的阻塞时间。



 IO密集型时，大部分线程都阻塞，故需要多配置线程数；

 参考公式：cup核数/1 - 阻塞系数    阻塞系数在0.8～0.9之间



 比如8核cpu：8/(1 - 0.9) = 80个线程数


 *
 *
 */
public class MyThreadPoolDemo {

    public static void main(String[] args) {
        // 一池五个处理线程,执行长期任务，性能好很多
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        // 执行很多短期异步得小程序或者负载较轻得服务器
        ExecutorService threadPool1= Executors.newCachedThreadPool();
        // 一个任务一个任务执行的场景
        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();
    }



}
