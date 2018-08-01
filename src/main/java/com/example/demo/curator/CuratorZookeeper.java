package com.example.demo.curator;

import com.example.demo.zookeeper.ConstantZookeeper;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/21
 */
public class CuratorZookeeper {

    public static void main(String[] args) throws InterruptedException {
        //baseSleepTimesMs 初始化sleep时间
        //maxRetries 最大重试次数
        //maxSleepMs 最大sleep 时间
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

        CuratorFramework client = CuratorFrameworkFactory.newClient(ConstantZookeeper.CONNECT_STRING,
                ConstantZookeeper.SESSION_TIMEOUT,
                3000, retryPolicy);

        client.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
