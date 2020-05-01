package com.earth.message.algorithms;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class ArraySolution {

    public static int search(int A[], int n, int target) {
        if (n <= 0)
            return -1;
        int left = 0, right = n - 1;
        while (left <= right) {
            int mid = left + ((right - left) / 2);
            if (A[mid] == target)
                return mid;

            if (A[left] <= A[mid])//转折点在右半边
            {
                if (A[left] <= target && target < A[mid])
                    right = mid - 1;
                else
                    left = mid + 1;
            } else //转折点在左半边
            {
                if (A[mid] < target && target <= A[right])
                    left = mid + 1;
                else
                    right = mid - 1;
            }
        }
        return -1;
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length < k || k == 0) return new int[0];
        int[] res = new int[nums.length - k + 1];
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < nums.length; i++) {
            //在尾部添加元素，并保证左边元素都比尾部大
            while (!deque.isEmpty() && nums[deque.getLast()] < nums[i]) {
                deque.removeLast();
            }
            deque.addLast(i);
            //在头部移除元素
            if (deque.getFirst() == i - k) {
                deque.removeFirst();
            }
            //输出结果
            if (i >= k - 1) {
                res[i - k + 1] = nums[deque.getFirst()];
            }
        }
        return res;
    }

    public static int binarySearch(int[] nums, int target) {
        int i = 0;
        int j = nums.length;
        int mid = (j + i) / 2;
        while(i < j) {
            if (nums[mid] == target) {
                mid++;
            }
            if (nums[mid] > nums[i]) {
                i = mid +1;
            }else {
                j = mid -1;
            }
        }
        return mid-1;
    }

    public static void main(String[] args) {
//        int[] nums = {4, 5, 6, 7, 8, 9, 0, 1, 2, 3};
//        int search = ArraySolution.search(nums, nums.length, 7);
//        System.out.println("the result: " + search);

//        int[] nums = {1,3,-1,-3,5,3,6,7};
//        int[] ints = ArraySolution.maxSlidingWindow(nums, 3);
//        log.info("ints {}", ints);

        int[] array = {1,2,2,2,3,4,4,5};
        int pos = ArraySolution.binarySearch(array, 4);
        System.out.println("the result is: " + pos);
    }
}
