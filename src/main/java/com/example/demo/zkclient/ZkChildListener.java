package com.example.demo.zkclient;

import com.example.demo.zookeeper.ConstantZookeeper;
import com.example.demo.zookeeperlock.ZookeeperConnection;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/21
 */
public class ZkChildListener {

    public static void main(String[] args) throws InterruptedException {
        String path = "/zk-book-2";

        ZkClient zkClient = new ZkClient(ConstantZookeeper.CONNECT_STRING, ConstantZookeeper.SESSION_TIMEOUT);

        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            //新增节点
            //减少节点
            //删除节点
            //都会收到信息
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println(path + "s child changed, currentChilds" + currentChilds);
            }
        });


        zkClient.createPersistent(path);
        Thread.sleep(1000);

        System.out.println(zkClient.getChildren(path));

        Thread.sleep(1000);

        zkClient.createPersistent(path + "/c1");

        Thread.sleep(1000);

        zkClient.delete(path + "/c1");

        Thread.sleep(1000);

        zkClient.delete(path);

        Thread.sleep(Integer.MAX_VALUE);


    }

}
