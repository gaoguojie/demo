package com.example.demo.javase;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TwoSumDemo {

    public static void main(String[] args) {
        int[] nums = new int[]{2, 0, 8, 7, 11, 15};
        int target = 13;
        int[] indexs = sum2(nums, target);

        System.out.println(indexs[0]);
        System.out.println(indexs[1]);


        AtomicInteger atomicInteger = new AtomicInteger();
        for (int i=1;i<5;i++) {
            System.out.println(atomicInteger.incrementAndGet() + "===");
        }
    }

    public static int[] sum1(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {

//                    System.out.println(nums[i]);
//                    System.out.println(i + "---------");
//                    System.out.println(nums[j]);
//                    System.out.println(j + "=======");
                    return new int[]{i, j};
                }

            }
        }
        return null;
    }


    public static int[] sum2(int[] nums, int target){
        Map<Integer, Integer> map = new HashMap<>();
        for (int i =0 ; i<nums.length; i++){
            int par = target -  nums[i];
            if(map.containsKey(par)){
                return new int[]{map.get(par), i};
            }
            map.put(nums[i],i);
        }
        return null;
    }

}
