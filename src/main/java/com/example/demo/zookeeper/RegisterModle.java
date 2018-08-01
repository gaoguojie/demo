package com.example.demo.zookeeper;

import java.io.Serializable;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/17
 */
public class RegisterModle implements Serializable {

    //服务ip地址
    public String serviceIp;
    //服务名称
    public String serviceName;
    //服务端口号
    public String servicePort;
    //客户端名称
    public String clientName;
    //客户端ip地址
    public String clientIp;
    //服务提供状态
    public String serviceStatus;

    public String getServiceIp() {
        return serviceIp;
    }

    public void setServiceIp(String serviceIp) {
        this.serviceIp = serviceIp;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePort() {
        return servicePort;
    }

    public void setServicePort(String servicePort) {
        this.servicePort = servicePort;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
}
