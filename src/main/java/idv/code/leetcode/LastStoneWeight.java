package idv.code.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

public class LastStoneWeight {


    public static void main(String[] args) {
        int result = lastStoneWeight(new int[]{2,3,4,5,6,7,8,1,1,1,1});
        System.out.println(result);
    }

    public static int lastStoneWeight(int[] stones) {

        PriorityQueue<Integer> heap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int stone : stones) {
            heap.add(stone);
        }

        while (heap.size() > 1) {
            int stone1 = heap.remove();
            int stone2 = heap.remove();
            if (stone1 != stone2) {
                heap.add(stone1 - stone2);
            }
        }

        return heap.isEmpty() ? 0 : heap.remove();
    }
}
