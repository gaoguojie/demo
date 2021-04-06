package com.example.demo.atomic;

import java.util.*;

public class Demo {

    public static void main(String[] args) {
//        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 22, 31, 14, 15, 17, 123, 110, 32, 43, 54, 67, 78};
        int[] array = new int[]{-3,4,3,90};
        int target = 0;
        int[] a = twoSum(array,target);
        System.out.println(Arrays.toString(a));
    }

    public static int[] twoSum(int[] nums, int target) {
        Map map = new HashMap();
        List list = new ArrayList();
        int[] a = new int[2];
        for (int i = 0; i < nums.length; i++) {
            // 减少循环次数nums
            if (nums[i] <= target) {
                for (int j = i + 1; j < nums.length; j++) {
                    if ( nums[i] + nums[j] == target) {
                        map.put(i,j);
                        list.add(i);
                        list.add(j);
                        a[0] = i;
                        a[1] = j;
                        System.out.println(nums[i]);
                        System.out.println(nums[j]);
                    }
                }
            }
        }
        return a;
    }
}
