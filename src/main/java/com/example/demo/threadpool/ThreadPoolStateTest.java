package com.example.demo.threadpool;

public class ThreadPoolStateTest {

    // ct1 是线程池中一个非常重要的的变量，以它的低29位表示线程池中处于RUNNING状态的线程个数，
    // 高3位表示线程池所处的状态
    public static int COUNT_BITS = Integer.SIZE - 3;

    public static int CAPACITY = (1 << COUNT_BITS) - 1;

    public static void main(String[] args) {
        // << 表示位移 左移
        int RUNNING = -1 <<COUNT_BITS;
        int SHUTDOWN = 0 << COUNT_BITS;
        int STOP = 1 << COUNT_BITS;
        int TIDYING = 2 << COUNT_BITS;
        int TERMINATED = 3 << COUNT_BITS;
        System.out.println("Integer.size is :" + Integer.SIZE + " , COUNT_BITS is :" + COUNT_BITS);
        System.out.println("CAPACITY is : " + CAPACITY  + " , " + Integer.toBinaryString(CAPACITY));
        // 五种状态中SHUTDOWN值等于0，RUNNING值小于0，其它三种状态STOP/TIDYING/TERMINATED值均大于0
        System.out.println("Running  = " + RUNNING + " = " + Integer.toBinaryString(RUNNING));
        System.out.println("SHUTDOWN = " + SHUTDOWN + "= " + Integer.toBinaryString(SHUTDOWN));
        System.out.println("STOP = " + STOP + " =0 " + Integer.toBinaryString(STOP));
        System.out.println("TIDYING = " + TIDYING +" =0 " +Integer.toBinaryString(TIDYING));
        System.out.println("TERMINATED = " + TERMINATED + " =0 " + Integer.toBinaryString(TERMINATED));
    }
}
