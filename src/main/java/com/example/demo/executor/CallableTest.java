package com.example.demo.executor;

import java.util.concurrent.Callable;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/23
 */
public class CallableTest implements Callable<String> {

    @Override
    public String call() throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("成功调用callable方法");
        System.out.println(sb.toString());

        return sb.toString();
    }
}
