package com.example.demo.threadpool;

import java.util.concurrent.*;

public class ThreadPoolRejectDemo {

    public static class MyTask implements Runnable{

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ",thread id: " + Thread.currentThread().getId());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
            MyTask task = new MyTask();
            LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<Runnable>(4);
            ExecutorService es = new ThreadPoolExecutor(5, 5, 0L,
                    TimeUnit.MILLISECONDS,queue, Executors.defaultThreadFactory(),
                    new RejectedExecutionHandler() {
                        @Override
                        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                            System.out.println(r.toString() + ",is discard");
                            System.out.println(" log the rejectedExecution");
                        }
                    });
            for (int i = 0 ; i < 200; i++){
                es.submit(task);
            }
        }
    }
}
