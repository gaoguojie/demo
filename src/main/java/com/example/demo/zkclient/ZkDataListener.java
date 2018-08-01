package com.example.demo.zkclient;

import com.example.demo.zookeeper.ConstantZookeeper;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/21
 */
public class ZkDataListener {


    public static void main(String[] args) throws InterruptedException {
        String path = "/zk-book-3";

        ZkClient zkClient = new ZkClient(ConstantZookeeper.CONNECT_STRING, ConstantZookeeper.SESSION_TIMEOUT);

        //创建临时节点
        zkClient.createEphemeral(path, "123");

        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            //节点数据变化
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("Node " + dataPath + " changed, new data :" + data );
            }
            //删除节点Node
            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("Node " + dataPath + " deleted.");
            }
        });

        //获取数据
        System.out.println(zkClient.readData(path).toString());
        zkClient.writeData(path, "456");
        Thread.sleep(1000);

        zkClient.delete(path);

        Thread.sleep(Integer.MAX_VALUE);
    }
}
