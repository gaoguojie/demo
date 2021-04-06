package com.example.demo.javase;

public class SingletonDemo {

    private static volatile SingletonDemo instance = null;

    private SingletonDemo() {
        System.out.println(Thread.currentThread().getName() + "\t" + "我是构造方法SingletonDemo");

    }

    //DCL(double check lock 双端校验机制)
    public static SingletonDemo getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }


    public static void main(String[] args) {
//        getInstance();

        for (int i = 0; i < 10; i++) {
            new Thread(()-> {
                getInstance();
            },"Thread"+i ).start();
        }
    }
}
