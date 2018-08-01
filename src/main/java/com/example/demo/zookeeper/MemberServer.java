package com.example.demo.zookeeper;

import com.alibaba.fastjson.JSON;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/17
 */
public class MemberServer implements Watcher {

    private static CountDownLatch downLatch = new CountDownLatch(1);
    ZooKeeperRegistered zkRegistered = new ZooKeeperRegistered();

    private void register(String data) {

        try {
            //创建连接
            ZooKeeper zookeeper = zkRegistered.registeredService(this);
            System.out.println("11111");
            downLatch.await();
            if (zookeeper == null) {
                throw new Exception("create zookeeper connection fail !!!");
            }
            //创建zk节点
            String path = zookeeper.create(ConstantZookeeper.PATH + "/service", data.getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println("服务器节点创建成功，path=" + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("2222");
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            downLatch.countDown();
        }
    }

    public static void main(String[] args) throws Exception {
        MemberServer server = new MemberServer();
        RegisterModle modle = new RegisterModle();
        modle.setServiceIp("127.0.0.1");
        modle.setServicePort("8080");
        modle.setServiceStatus("init");
        modle.setServiceName("会员系统！");
        modle.setClientName("init");
        server.register(JSON.toJSONString(modle));
        System.out.println("33333333");
        System.in.read();
    }
}
