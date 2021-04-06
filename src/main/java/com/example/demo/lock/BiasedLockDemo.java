package com.example.demo.lock;

import ch.qos.logback.core.net.SyslogOutputStream;

import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

public class BiasedLockDemo {

    public static List<Integer> vector = new Vector<>();

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        int count = 0;
        int startnum = 0;
        while (count < 30000000){
            vector.add(startnum);
            startnum +=2;
            count ++;
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin);



        String s1 = "a";
        String s2="a";
        System.out.println(s1 == s2);


        String a = "1231231231234591321231212567890";
        String b = "99";
        BigDecimal decimal = new BigDecimal(a).stripTrailingZeros();
        BigDecimal decimal1 = new BigDecimal(b).stripTrailingZeros();
        System.out.println(decimal.add(decimal1));



    }
}
