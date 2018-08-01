package com.example.demo.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/05
 */
public class Shutdown {

    public static void main(String[] args) throws Exception {
        Runner one = new Runner();
        Thread countThread = new Thread(one, "countThread");
        countThread.start();

        // 睡眠1秒，mian线程对countThread进行中断，使countThread能够感知中断而结束
        TimeUnit.SECONDS.sleep(1);
        countThread.isInterrupted();

        Runner two = new Runner();
        countThread = new Thread(two, "countThread");
        countThread.start();

        // 睡眠1秒，mian线程对Runner two进行取消，使countThread 能够感知on为false而结束
        TimeUnit.SECONDS.sleep(1);
        two.cannel();
    }




    private static class Runner implements Runnable{

        private long i;

        private volatile boolean on = true;

        @Override
        public void run() {
            if(on && !Thread.currentThread().isInterrupted()){
                i++;
            }
            System.out.println("count i = " + i);
        }

        public void cannel(){
            on = false;
        }
    }

}
