package com.example.demo.zookeeperlock;

import com.example.demo.zookeeper.ConstantZookeeper;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 独占锁
 *
 * @author gaoguojie
 * @version 1.0 2018/07/18
 */
public class DistributeLock implements Watcher {

    CountDownLatch downLatch = new CountDownLatch(1);
    ZookeeperConnection zk = new ZookeeperConnection();
    ZooKeeper zookeeper;

    public DistributeLock() {
        try {
            zookeeper = zk.zkConnection(this);
            downLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void lock(String path) {
        try {
            zookeeper.create(ConstantZookeeper.PATH + "/" + path, "zk锁".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            System.out.println("lock节点创建成功");
        } catch (Exception e) {
            try {
                downLatch.await(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            lock(path);
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent.getType() + "----" + watchedEvent.getState());
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            System.out.println("zk连接以创建");
            downLatch.countDown();
        }
    }

    public void unlock(String path) {
        try {
            zookeeper.delete(ConstantZookeeper.PATH + "/" + path, -1);
            System.out.println("lock节点已删除");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
