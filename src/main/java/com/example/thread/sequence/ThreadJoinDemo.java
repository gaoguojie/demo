package com.example.thread.sequence;


/**
 *
 * 1.早上；2.测试人员、产品经理、开发人员陆续的来公司上班；3.产品经理规划新需求；4.开发人员开发新需求功能；5.测试人员测试新功能。
 *
 * 规划需求，开发需求新功能，测试新功能是一个有顺序的，我们把thread1看做产品经理，thread2看做开发人员，thread3看做测试人员。
 * 通过子程序join使线程按顺序执行
 *
 * join():是Thread的方法，作用是调用线程需等待该join()线程执行完成后，才能继续用下运行。
 */
public class ThreadJoinDemo {

    public static void main(String[] args) throws InterruptedException {


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("产品经理规划新需求");
            }
        });


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("开发人员开发新需求功能");
            }
        });


        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    t2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("测试人员测试新功能");
            }
        });

        t3.join();
        System.out.println("早上。。。。");
        System.out.println("测试人员来上班啦...");
        t3.start();
        System.out.println("产品经理来上班了...");
        t1.start();
        System.out.println("开发人员来上班了...");
        t2.start();


    }
}
