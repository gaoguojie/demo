package com.example.demo.connection;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 数据库连接池简单示例
 *
 * @author gaoguojie
 * @version 1.0 2018/07/11
 */
public class ConnectionPool {

    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initalSize) {
        if (initalSize > 0) {
            for (int i = 0; i < initalSize; i++) {
                pool.addLast(ConnectionDriver.creaeteConnection());
            }
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                //链接释放后需要进行通知，这样其他消费者能够感知到连接池中已经归还了一个链接
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    public  Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool){
            //完全超时
            if(mills < 0){
                while(pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }else{
                long future = System.currentTimeMillis();
                long remaining = mills;
                while(pool.isEmpty() && remaining > 0){
                    pool.wait();
                    remaining = future - System.currentTimeMillis();
                }
                Connection result = null;
                if(!pool.isEmpty()){
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }
}
