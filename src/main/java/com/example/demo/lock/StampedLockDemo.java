package com.example.demo.lock;

import org.I0Itec.zkclient.ExceptionUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

public class StampedLockDemo {
    int value = 0;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static void main(String[] args) {
        StampedLock stampedLock = new StampedLock();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        StampedLockDemo cst = new StampedLockDemo();
        Runnable readTask = () -> {
            long stamp = stampedLock.readLock();
            try {
                System.out.println("read value " + cst.getValue());
            } finally {
                stampedLock.unlockRead(stamp);
            }
        };

        Runnable writeTask = () -> {
            long stamp = stampedLock.writeLock();
            try {
                cst.increment();
            } finally {
                stampedLock.unlockWrite(stamp);
            }
        };


        // 3. write tasks
        executor.submit(writeTask);
        executor.submit(writeTask);
        executor.submit(writeTask);

        // 1. read task
        executor.submit(readTask);

        executor.shutdown();

    }

    public void increment(){
        value ++;
        System.out.println("write , in increment " + value);
    }
}
