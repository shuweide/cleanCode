package idv.code.leetcode;

import java.util.HashSet;
import java.util.Set;

public class CountElements {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3};

        System.out.println(countElements(arr));
    }

    public static int countElements(int[] arr) {
        Set<Integer> nums = new HashSet<>();
        for (int num : arr) {
            nums.add(num);
        }

        int result = 0;
        for (int num : arr) {
            if (nums.contains(num + 1)) {
                result++;
            }
        }
        return result;
    }

}
