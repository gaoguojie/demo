package com.example.demo.atomic;

import java.util.Arrays;

public class Sort {


    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 22, 31, 14, 15, 17, 123, 110, 32, 43, 54, 67, 78};
        Solution solution = new Solution();
        int[] a = solution.sortArray(array);
        System.out.println(Arrays.toString(a));
    }

   static class Solution {
        public int[] sortArray (int[] nums) {
            //代表子集合大小，1，2，4，8，16.....
            int k = 1;
            int len = nums.length;
            while (k < len) {
                System.out.println(k + "---------------" + len);
                mergePass(nums, k, len);
                k *= 2;
            }
            return nums;

        }
        public void mergePass (int[] array, int k, int len) {

            int i;
            for (i = 0; i < len-2*k; i += 2*k) {
                //归并
                merge(array,i,i+k-1,i+2*k-1);
            }
            //归并最后两个序列
            if (i + k < len) {
                merge(array,i,i+k-1,len-1);
            }

        }
        public void merge (int[] arr,int left, int mid, int right) {
            //第一步，定义一个新的临时数组
            int[] temparr = new int[right -left + 1];
            int temp1 = left, temp2 = mid + 1;
            int index = 0;
            //对应第二步，比较每个指针指向的值，小的存入大集合
            while (temp1 <= mid && temp2 <= right) {
                if (arr[temp1] <= arr[temp2]) {
                    temparr[index++] = arr[temp1++];
                } else {
                    temparr[index++] = arr[temp2++];
                }
            }
            //对应第三步，将某一小集合的剩余元素存到大集合中
            if (temp1 <= mid) System.arraycopy(arr, temp1, temparr, index, mid - temp1 + 1);
            if (temp2 <= right) System.arraycopy(arr, temp2, temparr, index, right -temp2 + 1);
            //将大集合的元素复制回原数组
            System.arraycopy(temparr,0,arr,0+left,right-left+1);
        }
    }


}
