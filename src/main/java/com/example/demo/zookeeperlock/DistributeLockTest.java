package com.example.demo.zookeeperlock;

/**
 *
 * 独占锁测试类
 * @author gaoguojie
 * @version 1.0 2018/07/18
 */
public class DistributeLockTest implements Runnable{

    static int j =0;
    DistributeLock lock = new DistributeLock();

    public static void main(String[] args) throws InterruptedException {

        DistributeLockTest test = new DistributeLockTest();
        Thread t1 = new Thread(test);
        Thread t2 = new Thread(test);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(j);

    }

    @Override
    public void run() {
        lock.lock("lock");
        for (int i = 0; i< 100000; i++){
            j ++;
        }
        lock.unlock("lock");
    }
}
