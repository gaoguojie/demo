package com.example.demo.javase;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

// ABA问题解决 -》 AtomicStampedReference
public class ABADemo {

    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);
    public static void main(String[] args) {
        new Thread(()->{
            atomicReference.compareAndSet(100,101);
            atomicReference.compareAndSet(101,100);
        },"t1").start();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(atomicReference.compareAndSet(100,2019) +"\t"+atomicReference.get() );
        },"t2").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=========解决ABA问题==============");
        new Thread(()->{
            //获取版本号
            int stamped = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第一次版本号："+stamped);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedReference.compareAndSet(100,101,stamped, stamped +1);
            System.out.println(Thread.currentThread().getName()+"\t第二次版本号："+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() +1);
            System.out.println(Thread.currentThread().getName()+"\t第三次版本号："+atomicStampedReference.getStamp());

        },"t3").start();
        new Thread(()->{
            try {
                int stamped = atomicStampedReference.getStamp();
                System.out.println(Thread.currentThread().getName()+"\t 获取第一次版本号："+stamped);
                TimeUnit.SECONDS.sleep(3);
                boolean result = atomicStampedReference.compareAndSet(100,2019, stamped,stamped+1);
                System.out.println(Thread.currentThread().getName()+"\t修改是否成功："+result+"\t当前的版本号:"+atomicStampedReference.getStamp());
                System.out.println(Thread.currentThread().getName()+"\t当前最新值"+atomicStampedReference.getReference());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        },"t4").start();
    }
}
