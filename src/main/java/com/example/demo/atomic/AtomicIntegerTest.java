package com.example.demo.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/31
 */
public class AtomicIntegerTest {

    public static void main(String[] args){

        AtomicInteger ai = new AtomicInteger(3);

        System.out.println(ai.getAndIncrement());

        System.out.println(ai.get());

        System.out.println(ai.addAndGet(5));

        System.out.println(ai.getAndDecrement());

        System.out.println(ai.get());

    }

}
