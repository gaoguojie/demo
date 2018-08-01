package com.example.demo.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gaoguojie
 * @version 1.0 2018/07/24
 */
public class HashMapTest {


    public static void main(String[] args){

//        final HashMap<String, String> map = new HashMap<String, String>(2);
//
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i =0; i < 10000; i++){
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            map.put(UUID.randomUUID().toString(), "");
//                        }
//                    }, "ftf" + i).start();
//                }
//            }
//        }, "ftf");
//        t.start();
//        try {
//            t.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        Set set = map.keySet();
//        Iterator<String> it = set.iterator();
//        while(it.hasNext()){
//            System.out.println(it.next());
//        }

        ConcurrentHashMap<String ,String > ch = new ConcurrentHashMap<>(10);
        ch.put("12","222");
        System.out.println(ch.size());
    }




}
