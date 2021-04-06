package com.example.demo.lock;


import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDemo {

    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static String message = "a";

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Read());
        Thread thread2 = new Thread(new WriteB());
        Thread thread3 = new Thread(new WriteC());

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
    }

    static class Read implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                if(lock.isWriteLocked()){
                    System.out.println("I'll take the lock from write");
                }
                lock.readLock().lock();
                System.out.println("ReadThread " + Thread.currentThread().getId() + "---->Message is " + message);
                lock.readLock().unlock();
            }
        }
    }

    static class WriteB implements Runnable{

        @Override
        public void run() {
            for (int i=0; i<10; i++){

                try {
                    lock.writeLock().lock();
                    message = message.concat("b");
                    System.out.println("WriteThread " + Thread.currentThread().getId() + "---->message is " + message);
                } finally {
                    lock.writeLock().unlock();
                }
            }
        }
    }

    static class WriteC implements Runnable{

        @Override
        public void run() {
            for (int i =0; i<10; i++){
                try {
                    lock.writeLock().lock();
                    message = message.concat("C");
                    System.out.println("WriteThread " + Thread.currentThread().getId() + "---->message is " + message);
                } finally {
                    lock.writeLock().unlock();
                }
            }
        }
    }
}
