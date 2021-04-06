package com.example.demo.atomic;

import org.apache.zookeeper.data.Id;

import javax.xml.soap.Node;
import java.util.*;

public class Demo1 {
    private Integer value;
    private Node next;

    public static void main(String[] args) {


        System.out.println(logA(126));


        System.out.println(25 / 5);
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 22, 31, 14, 15, 17, 123, 110, 32, 43, 54, 67, 78};
        System.out.println(array.length);
        sortArray(array);
        System.out.println(Arrays.toString(sortArray(array)));

        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
        int left = 0;
        int right = 5;
        int mid = left + ((right - left) >> 1);
        System.out.println("-----------" + mid);

        int k =1;
        System.out.println(k *=2);
    }

    public static int[] sortArray(int[] nums) {
        int tmp;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    tmp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = tmp;
                }
            }
        }
        return nums;
    }


    public static int[] sort(int[] array) {
//        int[] a =
        return null;
    }

    public static boolean logA(int a) {

        if (a % 5 == 0) {
            while (a >= 5) {
                a /= 5;
            }
        }
        return a == 1 ? true : false;
    }

}