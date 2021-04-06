package com.example.demo.threadpool;

import com.sun.org.apache.xerces.internal.impl.dv.SchemaDVFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static sun.misc.Version.println;

public class ThreadPoolDemo1 implements Runnable {
    public static void main(String[] args) {
        ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(12);
//        LinkedBlockingDeque
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4, 8, 60, TimeUnit.SECONDS, queue);
        for (int i = 0; i < 30; i++) {
            threadPool.execute(new Thread(new ThreadPoolDemo1(), "Thread".concat(i + "")));
            if (queue.size() > 0) {
                System.out.println("阻塞队列有线程了，队列中阻塞的线程数：" + queue.size() + ", 线程池中执行任务的线程数："
                        + threadPool.getPoolSize() + ",核心线程数：" + threadPool.getCorePoolSize());

            }
            System.out.println("线程池中当前的线程数：" + threadPool.getPoolSize()+ ",核心线程数：" + threadPool.getCorePoolSize());
        }
        threadPool.shutdown();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


