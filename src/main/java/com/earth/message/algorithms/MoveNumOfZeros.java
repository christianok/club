package com.earth.message.algorithms;

public class MoveNumOfZeros {
    public static int[] move(int[] nums) {
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[k++] = nums[i];
            }
        }
        for (int i = k; i < nums.length; i++) {
            nums[i] = 0;
        }

        return nums;
    }

    public static void main(String[] args) {
        int[] test = {3,1,0,0,12};
        int[] move = MoveNumOfZeros.move(test);
        int length = move.length;
    }
}
