package com.example.demo.zkclient;

import com.example.demo.zookeeper.ConstantZookeeper;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.data.Stat;

import javax.sound.midi.Patch;
import java.util.List;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/18
 */
public class ZkClientTest {

    private final static String path = "/zk-book/c2";

    private final static String rootPath = "/zk-book";

    public static void main(String[] args){

        ZkClient zkClient = new ZkClient(ConstantZookeeper.CONNECT_STRING, ConstantZookeeper.SESSION_TIMEOUT);
        boolean existsBool = exists_Node_Sample(zkClient);
        if(existsBool){
            delete_Node_Sample(zkClient);
        }
        create_Node_Sample(zkClient);
        setData(zkClient);
        getData(zkClient);
    }

    /**
     * 创建zk节点
     */
    private static void create_Node_Sample(ZkClient zkClient){
        //递归创建节点
        zkClient.createPersistent(path, true);
    }

    /**
     * 判断节点是否存在
     */
    private static boolean exists_Node_Sample(ZkClient zkClient){
        boolean existsNode = zkClient.exists(path);
        System.out.println(existsNode);
        return existsNode;

    }

    private static void delete_Node_Sample(ZkClient zkClient){
        //删除节点
//        boolean deletNode = zkClient.delete(path);
//        System.out.println("删除path节点："+deletNode);

        //递归删除节点
        boolean deleteRecursiveNode = zkClient.deleteRecursive(path);
        System.out.println("递归删除节点：" + deleteRecursiveNode);
    }

    /**
     * 修改节点信息
     * @param zkClient
     */
    private static void setData(ZkClient zkClient){
       Stat stat = zkClient.writeData(path, "测试c2");
       System.out.println("节点版本号：" + stat.getVersion());
    }

    /**
     * 获取节点信息
     * @param zkClient
     */
    private static void getData(ZkClient zkClient){
        String str = zkClient.readData(path);
        System.out.println("readData===" + str);
        List<String> list = zkClient.getChildren(rootPath);
        for (String s : list){
            System.out.println("getChilder===" + s);
        }
    }

}
