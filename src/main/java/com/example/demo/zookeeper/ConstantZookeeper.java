package com.example.demo.zookeeper;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/17
 */
public class ConstantZookeeper {

    /**
     * 连接信息
     */
    public static final String CONNECT_STRING = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

    /**
     * 超时时间
     */
    public static final int SESSION_TIMEOUT = 60000;

    /**
     * zk 创建节点
     */
    public static final String PATH = "/root";
}
