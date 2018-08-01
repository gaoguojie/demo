package com.example.demo.zookeeperlock;

import com.example.demo.zookeeper.ConstantZookeeper;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * 连接zk
 * @author gaoguojie
 * @version 1.0 2018/07/18
 */
public class ZookeeperConnection {


    public ZooKeeper zkConnection(Watcher watcher) throws IOException {
        ZooKeeper zk = new ZooKeeper(ConstantZookeeper.CONNECT_STRING, ConstantZookeeper.SESSION_TIMEOUT, watcher);
        return zk;
    }

}
