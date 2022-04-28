package app.netlify.nmhillusion.support_tester_app.runner;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * date: 2022-04-28
 * <p>
 * created-by: nmhillusion
 */

class ThirdMax {
    private int findMinInArray(int[] arr) {
        int min = arr[0];
        for (int item : arr) {
            if (min > item) {
                min = item;
            }
        }
        return min;
    }

    private boolean ableToReplace(int x, int[] arr) {
        boolean ableToReplace = false;
        for (int item : arr) {
            if (item < x) {
                ableToReplace = true;
            } else if (item == x) {
                ableToReplace = false;
                break;
            }
        }
        return ableToReplace;
    }

    private void replaceMinOfMaxNums(int currentX, int[] maxNums) {
        final int minInThree = findMinInArray(maxNums);
        for (int idx = 0; idx < maxNums.length; ++idx) {
            if (minInThree == maxNums[idx]) {
                maxNums[idx] = currentX;
            }
        }
    }

    public int thirdMax(int[] nums) {
        nums = Arrays.stream(nums).distinct().toArray();

        if (1 == nums.length) {
            return nums[0];
        } else if (2 == nums.length) {
            return Math.max(nums[0], nums[1]);
        }

        final int[] maxNums = new int[]{nums[0], nums[1], nums[2]};

        for (int idx = 3; idx < nums.length; ++idx) {
            int currentX = nums[idx];
            if (ableToReplace(currentX, maxNums)) {
                replaceMinOfMaxNums(currentX, maxNums);
            }
        }

        return findMinInArray(maxNums);
    }
}
