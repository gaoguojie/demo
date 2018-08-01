package com.example.demo.curator;

import com.example.demo.zookeeper.ConstantZookeeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/21
 */
public class CuratorBackground {

    static String path = "/zk-book-3";

    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(ConstantZookeeper.CONNECT_STRING)
            .sessionTimeoutMs(ConstantZookeeper.SESSION_TIMEOUT)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    static CountDownLatch semaphore = new CountDownLatch(2);

    static ExecutorService tp = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        client.start();

        System.out.println("Main Thread:" + Thread.currentThread().getName());

        try {
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .inBackground(new BackgroundCallback() {
                        @Override
                        public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                            System.out.println("event[code:" + event.getResultCode() +
                                    ", type:" + event.getType() + "]");

                            System.out.println("Thread of processResult:" + Thread.currentThread().getName());

                            semaphore.countDown();
                        }
                    }, tp)
                    .forPath(path, "init".getBytes());

            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                    .inBackground(new BackgroundCallback() {
                        @Override
                        public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                            System.out.println("event[code:" + event.getResultCode() +
                                    ",type:" + event.getType() + "]");
                            System.out.println("Thread of processResult:" + Thread.currentThread().getName());
                            semaphore.countDown();
                        }
                    }).forPath(path, "init".getBytes());

            semaphore.await();
            tp.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
