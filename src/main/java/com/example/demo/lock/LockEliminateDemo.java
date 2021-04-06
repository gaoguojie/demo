package com.example.demo.lock;

public class LockEliminateDemo {

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        for (int i=0; i<10000000; i++){
            appendStr("LockEliminate","test");
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin) + " ms");
    }

    public static String appendStr(String s1, String s2){
        StringBuffer sb = new StringBuffer();
        sb.append(s1);
        sb.append(s2);
        return sb.toString();
    }
}
