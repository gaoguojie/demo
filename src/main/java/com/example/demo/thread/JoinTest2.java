package com.example.demo.thread;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/06
 */
public class JoinTest2 {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Join(), "join");
        t.start();
        t.join(1000);
        System.out.println("join Finish");
    }

    static class Join implements Runnable {



        @Override
        public void run() {
            try {
                System.out.println("Begin sleep");
                Thread.sleep(1100);
                System.out.println("End slepp");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
