package com.example.demo.javase;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 集合类不安全的问题
 * arrayList
 */
public class ContainerNotSafeDemo {
    //        Collection; -- 接口
//        Collections; -- 集合工具类
    public static void main(String[] args) {
        Map<String,String> map = new ConcurrentHashMap<>();//Collections.synchronizedMap(new HashMap<>());//new HashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
        // hashset 底层实现的是hashmap，为什么map.add(k,v),set.add()只需要一个值，
        // 应为hashset.add方法添加的值都为key，value为一个恒定的值(PRESENT)
        new HashSet<>().add("a");

    }


    public static void setNotSafe(){
        Set<String> set = new CopyOnWriteArraySet<>();//Collections.synchronizedSet(new HashSet<>());//new HashSet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
        // hashset 底层实现的是hashmap，为什么map.add(k,v),set.add()只需要一个值，
        // 应为hashset.add方法添加的值都为key，value为一个恒定的值(PRESENT)
        new HashSet<>().add("a");
    }
    /**
     * java.util.ConcurrentModificationException arraylist多线程下的常见错误
     * 1 故障现象
     *       java.util.ConcurrentModificationException
     * 2 导致原因
     *      并发争抢修改导致，参考我们的花名册签名情况。
     *      一个人正在写入，另一个同学过来抢夺，导致数据不一致异常。并发修改异常。
     * 3 解决方案
     *  3.1 new Vector<>();
     *  3.2 Collections.synchronizedList(new ArrayList<>());
     *  3.3 new CopyOnWriteArrayList<>();
     *
     * 4 优化建议（同样的错误不犯第二次）
     */
    public static void listNotSafe(){

//      List<String> list1 = new ArrayList<>();
//      List<String> list1 = Collections.synchronizedList(new ArrayList<>());
//      vector 的add方法加锁，性能极具下降；当前很少使用vector
//      List<String> list1 = new Vector<>();
        List<String> list1 = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list1.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list1);
            }, String.valueOf(i)).start();
        }

        /**
         * arraylist是线程不安全，写一个代码
         *  写时复制
         * 复制出一个新的容器Object[] newElements,然后新的容器Object[] newElements里添加元素，
         * 添加完元素之后，再将原容器的引用指向新的容器 setArray(newElements)；
         * 这样做的好处是可以对CopayOnWrite容器进行并发的读，而不需要加锁，应为当前容器不会添加任何元素。
         * 所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器

        public boolean add(E e) {
            final ReentrantLock lock = this.lock;
            lock.lock();
            try {
                Object[] elements = getArray();
                int len = elements.length;
                Object[] newElements = Arrays.copyOf(elements, len + 1);
                newElements[len] = e;
                setArray(newElements);
                return true;
            } finally {
                lock.unlock();
            }

        }

         **/

    }
}