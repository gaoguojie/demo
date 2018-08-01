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
public class CuratorFluentZk {


    public static void main(String[] args){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(ConstantZookeeper.CONNECT_STRING)
                .sessionTimeoutMs(ConstantZookeeper.SESSION_TIMEOUT)
                .retryPolicy(retryPolicy)
                .build();

        client.start();
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
