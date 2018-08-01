package com.example.demo.curator;

import com.example.demo.zookeeper.ConstantZookeeper;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/21
 */
public class CuratorZkClient {



    static String path = "/zk-book-3/c1";

    static CuratorFramework client = CuratorFrameworkFactory.builder()
            .connectString(ConstantZookeeper.CONNECT_STRING)
            .sessionTimeoutMs(ConstantZookeeper.SESSION_TIMEOUT)
            .retryPolicy(new ExponentialBackoffRetry(1000, 3))
            .build();

    public static void main(String[] args){
        client.start();
        udpateData();

    }

    /**
     * 创建节点
     */
    private static void create(){

        try {
            client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(path, "curator测试".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void delete(){
        try {
            //递归创建节点
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(path, "init".getBytes());
            Stat stat = new Stat();
            //获取数据、并且获取状态
            client.getData().storingStatIn(stat).forPath(path);
            System.out.println("创建节后后版本号：" + stat.getVersion());
            //根据版本号，递归删除节点信息
            client.delete().deletingChildrenIfNeeded()
                    .withVersion(stat.getVersion())
                    .forPath(path);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getDataSample(){
        try {
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(path, "init".getBytes());

            Stat stat = new Stat();

            client.getData()
                    .storingStatIn(stat)
                    .forPath(path);
            System.out.println(stat.getVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void udpateData(){
        try {
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(path, "init".getBytes());

            Stat stat = new Stat();
            client.getData()
                    .storingStatIn(stat)
                    .forPath(path);
            System.out.println("Success set node for : "+ path + ",new version:" +
            client.setData()
                    .withVersion(stat.getVersion())
                    .forPath(path).getVersion());

            client.setData()
                    .withVersion(stat.getVersion())
                    .forPath(path);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
