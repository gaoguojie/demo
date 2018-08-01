package com.example.demo.thread;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/06
 */
public class JoinTest {

    public static int a = 0;

    private transient String name;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new joinTest1(), "JoinTest1");
        thread.start();
        thread.join();
        System.out.println(a);
    }

    static class joinTest1 implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                a = a + 1;
            }
        }
    }
}
