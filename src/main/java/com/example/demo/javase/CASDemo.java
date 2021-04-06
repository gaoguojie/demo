package com.example.demo.javase;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS- 比较并交换
 *  比较当前工作内存中的值和主内存中的值，如果相同则执行规定操作，
 *  否则继续比较直到主内存和工作内存中的值一致为止。
 * CAS - 应用
 * CAS有3个数，内存值V，旧的预期值A，要修改更新值B
 * 当前仅当预期值A和内存值V相同时，将内存值V修改为B，否则什么都不操作。
 *
 * CAS - 缺点
 * 循环时间长开销大
 * 只能保证一个共享变量的原子操作
 * 因出ABA的问题
 * CAS算法实现一个重要前提需要取出内存中某时刻的数据并在当下时刻比较并替换，那么在这个时间差会导致数据的变化
 * 比如说一个线程one从内存位置V中取出A，这时候另一个线程two也从内存中取出A，并且线程two进行了一些操作将值变成了B，
 * 然后线程two又将V位置的数据变成A，这时候线程one进行CAS操作发现内存中仍然是A，然后线程one操作成功.
 *
 * 尽管线程one的CAS操作成功，但是不代表这个过程就是没有问题
 *
 * 使用自旋锁和unsafe
 *
 *
 */
public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        //期望值跟主物理内存值进行比较，如果一致则更新成2019
        System.out.println( atomicInteger.compareAndSet(5, 2019) + "\t" + atomicInteger.get());


        System.out.println( atomicInteger.compareAndSet(5, 2121) + "\t" + atomicInteger.get());


        atomicInteger.getAndIncrement();
        boolean b = false;
        do {
            System.out.println("4444");
            b = true;
        }while (b);
            System.out.println("333333");

    }
}
