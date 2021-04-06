package com.example.demo.javase;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ProdConsumer_BlockingQueueDemo {

    static class MyResource {
        //默认开启：进行生产和消费
        private volatile boolean flag = true;

        private AtomicInteger atomicInteger = new AtomicInteger();

        BlockingQueue<String> blockingQueue = null;

        public MyResource(BlockingQueue<String> blockingQueue) {
            this.blockingQueue = blockingQueue;
            System.out.println(blockingQueue.getClass().getName());
        }



        public void myProd() {
            String data;
            boolean result;
            // 多线程环境判断，使用while
            while (flag) {
                data = atomicInteger.incrementAndGet() + "";
                try {
                    result = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
                    if (result) {
                        System.out.println(Thread.currentThread().getName() + "\t 插入队列[" + data + "]成功");
                    } else {
                        System.out.println(Thread.currentThread().getName() + "\t 插入队列[" + data + "]失败");
                    }
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "\t 老板叫停生产，表示flag=false,生产结束");
        }

        public void myConsumer() {
            String result = null;
            while (flag) {
                try {

                    result = blockingQueue.poll(2L, TimeUnit.SECONDS);
                    if (null == result || result.equalsIgnoreCase("")) {
                        flag = false;
                        System.out.println(Thread.currentThread().getName() + "\t 超过两秒钟没有取到蛋糕，消费者退出");
                        System.out.println();
                        System.out.println();
                        return;
                    }
                    System.out.println(Thread.currentThread().getName() + "\t 消费蛋糕【" + result + "】成功");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void stop(){
            flag = false;
        }
    }


    public static void main(String[] args) {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t 生产者线程启动");
            myResource.myProd();
        }, "prod").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t 消费者线程启动");
            myResource.myConsumer();
        },"consumer").start();

        //暂停一会线程
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println();
        myResource.stop();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
