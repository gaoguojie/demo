package com.example.demo.thread;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/04
 */
public class Daemon {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        thread.setDaemon(true);//将线程设置为后台线程
        thread.start();//启动后台线程

        Thread.sleep(1000);
        System.out.println("22222");
    }

    static class DaemonRunner implements Runnable{


        @Override
        public void run() {

            try {
                System.out.println("11111");
                SleepUtils.second(10);
                System.out.println("33333");
            }finally{
                System.out.println("DaemonThread finally run...");
            }
        }
    }

}
