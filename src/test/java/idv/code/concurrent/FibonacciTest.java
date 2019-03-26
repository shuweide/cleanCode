package idv.code.concurrent;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ForkJoinPool;

import org.junit.Test;

public class FibonacciTest {
    @Test
    public void testStandard() throws Exception {
        int n = 35;
        LocalDateTime before = LocalDateTime.now();
        Fibonacci fibonacci = new Fibonacci(n);
        ForkJoinPool.commonPool().execute(fibonacci);
        System.out.println(fibonacci.get());
        LocalDateTime after = LocalDateTime.now();
        System.out.println(ChronoUnit.MILLIS.between(before, after));
        //fast fibonacci
        //f(n)=floor((((1+sqrt(5))/2)^n) /sqrt(5) + 1/2) 
        double fn = Math.floor(Math.pow((1 + Math.sqrt(5)) / 2, n) / Math.sqrt(5) + 1 / 2);
        System.out.println(fn);
        System.out.println(ChronoUnit.MILLIS.between(after, LocalDateTime.now()));
    }
}
