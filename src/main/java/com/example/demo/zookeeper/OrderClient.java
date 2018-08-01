package com.example.demo.zookeeper;

import com.alibaba.fastjson.JSON;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/17
 */
public class OrderClient implements Watcher {

    ZooKeeperRegistered zkRegistered = new ZooKeeperRegistered();

    private void subscribe(String clientName, String clientIp) {

        try {
            //创建连接
            ZooKeeper zookeeper = zkRegistered.registeredService(this);
            if(zookeeper == null){
                throw new Exception("create zookeeper connection fail !!!");
            }
            //创建zk节点
            List<String> nodes = zookeeper.getChildren(ConstantZookeeper.PATH , true);
            if(nodes.isEmpty()){
                throw new RuntimeException("该path="+ConstantZookeeper.PATH + "没有可用的服务");
            }
            for (String node : nodes) {
                System.out.println(node);
                byte[] nodeDate = zookeeper.getData(ConstantZookeeper.PATH + "/" + node, true, null);
                RegisterModle modle = JSON.parseObject(new String(nodeDate), RegisterModle.class);
                if(modle == null || !"init".equals(modle.getServiceStatus())){
                    throw new RuntimeException("没有找到可用的run服务");
                }
                modle.setClientName(clientName);
                modle.setClientIp(clientIp);
                modle.setServiceStatus("RUN");
                Stat stat = zookeeper.setData(ConstantZookeeper.PATH + "/" + node, JSON.toJSONString(modle).getBytes(), -1);
                System.out.println(stat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
       Event.EventType eventType = watchedEvent.getType();
       if(eventType == Event.EventType.NodeChildrenChanged){
           String clientName = "交易系统";
           String clientIp = "127.0.0.1";
           OrderClient orderClient = new OrderClient();
           orderClient.subscribe(clientName, clientIp);
       }
    }

    public static void main(String[] args) {
        OrderClient orderClient = new OrderClient();
        String clientName = "交易系统";
        String clientIp = "127.0.0.1";
        orderClient.subscribe(clientName, clientIp);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
