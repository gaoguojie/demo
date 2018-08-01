package com.example.demo.zookeeper;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/17
 */
public class ZooKeeperRegistered {

    private CountDownLatch downLatch = new CountDownLatch(1);

    ZooKeeper zk;

    public ZooKeeper registeredService(Watcher watcher) throws Exception {
        zk = new ZooKeeper(ConstantZookeeper.CONNECT_STRING,
                ConstantZookeeper.SESSION_TIMEOUT, watcher);
        System.out.println(zk.getState());
        System.out.println("Zookeeper session established");
        return zk;
    }

}
