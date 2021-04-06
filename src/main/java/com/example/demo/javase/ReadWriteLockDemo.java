package com.example.demo.javase;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyCash {

    private volatile Map<String, Object> map = new HashMap<>();
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void put(String key, Object value) {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在写入：" + key);
            try {
                TimeUnit.MICROSECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成" + key);
        } finally {
            rwLock.writeLock().unlock();
        }

    }

    public void get(String key) {
        rwLock.readLock().lock();


        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在读：" + key);
            try {
                TimeUnit.MICROSECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object value = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读以完成" + value);
        } finally {
            rwLock.readLock().unlock();
        }

    }


    public void clearMap(){
        map.clear();
    }


}

/**
 * 多个线程同时读一个资源类没有任何问题，所以满足了并发量，读取共享资源应该可以同时进行。。
 * 但是
 * 如果有一个线程想去写共享资源，就不应该再有其它线程可以对该资源进行读和写
 * 小总结：
 * 读-读能共存
 * 读-写不能共存
 * 写-写不能共存
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCash myCash = new MyCash();
        for (int i = 0; i < 5; i++) {
            final int tempI = i;
            new Thread(() -> {
                myCash.put(tempI + "", tempI + "");
            },"AA").start();
        }

        for (int i = 0; i < 5; i++) {
            final int tempI = i;
            new Thread(() -> {
                myCash.get(tempI+"");
            },"BB").start();
        }

    }

}
