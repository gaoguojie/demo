package com.example.demo.zookeeperlock;

import com.example.demo.zookeeper.ConstantZookeeper;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 控制时序锁
 *
 * @author gaoguojie
 * @version 1.0 2018/07/18
 */
public class ZookeeperLock implements Watcher {

    CountDownLatch downLatch = new CountDownLatch(1);
    ZooKeeper zk;
    ZookeeperConnection zc = new ZookeeperConnection();

    private  String currentNode;
    public ZookeeperLock() {
        try {
            zk = zc.zkConnection(this);
            downLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            downLatch.countDown();
        }
    }

    public void lock(String path) {
        try {
            currentNode = zk.create(ConstantZookeeper.PATH + "/" + path, "zk锁".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println("lock节点创建成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<String> listNode = zk.getChildren(ConstantZookeeper.PATH, false);
            Collections.sort(listNode);
            if(currentNode.equals(ConstantZookeeper.PATH + "/" + listNode.get(0))){
                return;
            }else{
                String childNode = currentNode.substring(currentNode.indexOf("/") + 1);
                int num = Collections.binarySearch(listNode, childNode);//当前节点去找下 看看在什么问题
                if(num==0){
                    num=1;
                }
                String waitNode = listNode.get(num - 1);
                Stat stat = zk.exists(ConstantZookeeper.PATH + "/" + waitNode, true);
                if (null != stat) {
                    downLatch.await(5000, TimeUnit.MILLISECONDS);

                }

            }

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void unLock() {
        try {
            zk.delete(currentNode, -1);
            currentNode=null;
            zk.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
