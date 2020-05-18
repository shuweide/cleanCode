package idv.code.leetcode;

import java.util.Arrays;

public class PerformStringShifts {

    public static void main(String[] args) {
        System.out.println(stringShift("abcdefg", new int[][]{{1, 1}, {1, 1}, {0, 2}, {1, 3}}));
    }

    public static String stringShift(String s, int[][] shift) {

        if (shift == null || shift.length == 0) {
            return s;
        }

        long sum = Arrays.stream(shift).mapToInt(ia -> ia[0] == 0 ? ia[1] : -ia[1]).sum();

        if (sum == 0) {
            return s;
        } else {
            return sum > 0 ? leftRotate(s, (int) sum) : rightRotate(s, Math.abs((int) sum));
        }

    }

    private static String leftRotate(String s, int d) {
        d = d % s.length();
        return s.substring(d) + s.substring(0, d);
    }

    private static String rightRotate(String s, int d) {
        d = d % s.length();
        return leftRotate(s, s.length() - d);
    }


}
