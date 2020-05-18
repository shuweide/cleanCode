package idv.code.leetcode;

import java.util.*;

public class FirstUniqueNumber {

    private Map<Integer, Integer> counts = new HashMap<>();
    private Queue<Integer> queue = new LinkedList<>();

    public static void main(String[] args) {
        FirstUniqueNumber firstUnique = new FirstUniqueNumber(new int[]{2, 3, 5});
        firstUnique.showFirstUnique(); // return 2
        firstUnique.add(5);            // the queue is now [2,3,5,5]
        System.out.println(firstUnique.showFirstUnique()); // return 2
        firstUnique.add(2);            // the queue is now [2,3,5,5,2]
        System.out.println(firstUnique.showFirstUnique()); // return 3
        firstUnique.add(3);            // the queue is now [2,3,5,5,2,3]
        System.out.println(firstUnique.showFirstUnique()); // return -1
        firstUnique.add(17);            // the queue is now [2,3,5,5,2,3]
        System.out.println(firstUnique.showFirstUnique()); // return -1

    }

    public FirstUniqueNumber(int[] nums) {
        for(int num: nums){
            counts.compute(num, (k,v) -> v == null ? 1 : v+1);
            queue.offer(num);
        }
    }

    public int showFirstUnique() {
        while (!queue.isEmpty() && counts.get(queue.peek()) != 1){
            queue.poll();
        }
        return queue.isEmpty() ? -1 : queue.peek();
    }

    public void add(int value) {
        counts.compute(value, (k,v) -> v == null ? 1 : v+1);
        queue.offer(value);
    }
}
