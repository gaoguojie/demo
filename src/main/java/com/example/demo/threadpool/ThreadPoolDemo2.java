package com.example.demo.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo2 implements Runnable {

    public static void main(String[] args) {
        LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<>();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4, 8, 60, TimeUnit.SECONDS, queue);
        for (int i = 0; i < 1000; i++) {
            threadPool.execute(new Thread(new ThreadPoolDemo1(), "Thread".concat(i + "")));
            if (queue.size() > 0) {
                System.out.println("阻塞队列有线程了，队列中阻塞的线程数：" + queue.size() + "线程池中执行任务的线程数：" + threadPool.getPoolSize());

            }
            System.out.println("线程池中当前的线程数：" + threadPool.getPoolSize());
        }
        threadPool.shutdown();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
