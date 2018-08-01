package com.example.demo.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/23
 */
public class ThreadTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("111111");
            }
        });

        executorService.shutdown();

        ExecutorService executorService1 = Executors.newScheduledThreadPool(1);

        CallableTest ct = new CallableTest();

        Future<String> ft = executorService1.submit(ct);

        try {
            if (!ft.isDone()) {
                String s = ft.get();
                System.out.println(s);
            }
            executorService1.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FutureTask<String> ftask = new FutureTask<>(ct);
        new Thread(ftask).start();
//        ftask.cancel(true);
        try {
            System.out.println(ftask.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ftask.cancel(true);
    }
}
