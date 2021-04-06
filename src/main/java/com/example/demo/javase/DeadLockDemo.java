package com.example.demo.javase;

public class DeadLockDemo {

    static class HoldLockThread implements Runnable{
        String lockA;
        String lockB;

        HoldLockThread(String lockA, String lockB){
            this.lockA = lockA;
            this.lockB = lockB;
        }

        @Override
        public void run() {
            synchronized (lockA){
                System.out.println(Thread.currentThread().getName() + "\t 当前线程"+lockA+"，正在尝试获取线程"+lockB);
                synchronized (lockB){
                    System.out.println(Thread.currentThread().getName() + "\t 当前线程"+lockB+"，正在尝试获取线程"+lockA);

                }
            }
        }
    }

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new HoldLockThread(lockA,lockB),"ThreadA").start();

        new Thread(new HoldLockThread(lockB,lockA),"ThreadB").start();


        /**
         * linux ps -ef|grep xxxx
         *
         * window 下的java运行程序 也有类似ps的查看进程命令，但是目前我们需要查看的只有java
         *              jps = java ps       jps -l  命令定位进程号
         *              jstack 根据进程号，找到死锁查看
         *
         *
         */

    }
}
