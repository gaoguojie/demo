package com.example.demo.javase;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * ArrayBlockingQueue : 是一个基于数组结构的有界对列，此队列按照FIFO（先进先出）原则对元素进行排序
 * LinkedBlockingQueue ： 是一个基于链表的阻塞队列，此队列按照FIFO（先进先出）排序元素，吞吐量通常高于ArrayBlockingQueue
 * SynchronousQueue ： 一个不存元素的阻塞队列，每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常高于
 *
 *
 * 1 队列
 *
 *
 *
 * 2 阻塞队列
 *   2。1 阻塞队列有没有好的一面
 *
 *   2。2 不得阻塞，你如何管理
 *
 *
 *
 */
public class BlockingQueueDemo {

    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));

        System.out.println(blockingQueue.element());

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());


    }
}
