package idv.code.concurrent;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

public class AtomicTest {

    @Test
    public void testJava8NewMethod() {
        AtomicLong atomicLong = new AtomicLong(100L);
        long result = atomicLong.accumulateAndGet(10, Long::sum);
        System.out.println(result);
    }

    @Test
    public void testJava8NewClass() {
//        LongAdderer, LongAccumulator, Double-Adder, and DoubleAccumulator instead of the Atomic classes
//        是專門設計來更新的Atomic Class
        LongAdder adder = new LongAdder();
        //在Multi Thread中使用
        adder.add(100L);
        adder.add(100L);
        adder.add(100L);
        adder.add(100L);
        adder.add(100L);
        long added = adder.sum();
        System.out.println(added);

        LongAccumulator acc = new LongAccumulator(Long::sum, 0);
        acc.accumulate(100L);
        acc.accumulate(100L);
        acc.accumulate(100L);
        acc.accumulate(100L);
        acc.accumulate(100L);
        long result = acc.get();
        System.out.println(result);
    }
}
