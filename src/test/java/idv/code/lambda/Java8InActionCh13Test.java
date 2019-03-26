package idv.code.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Java8InActionCh13Test {

    private List<List<Integer>> subsets(List<Integer> list) {
        if (list.isEmpty()) {
            List<List<Integer>> ans = new ArrayList<>();
            ans.add(Collections.emptyList());
            return ans;
        }

        Integer first = list.get(0);
        List<Integer> rest = list.subList(1, list.size());

        List<List<Integer>> subans = subsets(rest);
        List<List<Integer>> subans2 = insertAll(first, subans);
        return concat(subans, subans2);
    }

    private List<List<Integer>> concat(List<List<Integer>> subans, List<List<Integer>> subans2) {
        List<List<Integer>> r = new ArrayList<>(subans);
        r.addAll(subans2);
        return r;
    }

    private List<List<Integer>> insertAll(Integer first, List<List<Integer>> lists) {
        List<List<Integer>> result = new ArrayList<>();
        for (List<Integer> list : lists) {
            List<Integer> copyList = new ArrayList<>();
            copyList.add(first);
            copyList.addAll(list);
            result.add(copyList);
        }
        return result;
    }

    @Test
    public void testFunctionalProgramming() {
        System.out.println(subsets(Arrays.asList(1, 4, 9)));
    }

    static int factorialIterative(int n) {
        int r = 1;
        for (int i = 1; i <= n; i++) {
            r *= i;
        }
        return r;
    }

    static long factorialRecursive(long n) {
        return n == 1 ? 1 : n * factorialRecursive(n - 1);
    }

    static long factorialTailRecursive(long n) {
        return factorialHelper(1, n);
    }

    static long factorialHelper(long acc, long n) {
        return n == 1 ? acc : factorialHelper(acc * n, n - 1);
    }

    @Test(expected = StackOverflowError.class)
    public void testIterationAndRecursion() {
        long start = System.nanoTime();
        factorialIterative(1000000);
        System.out.println(String.format("factorialIterative %d ns", (System.nanoTime() - start)));

        //才超過10萬，目前的Java(JDK8)就會出現StackOverflow，所以需要等待之後版本才適合(Scala和Groovy都有支援)
        start = System.nanoTime();
        factorialRecursive(1000000);
        System.out.println(String.format("factorialRecursive %d ns", (System.nanoTime() - start)));

        start = System.nanoTime();
        factorialTailRecursive(1000000);
        System.out.println(String.format("factorialTailRecursive %d ns", (System.nanoTime() - start)));
    }
}
