package idv.code.lambda;

import idv.code.concurrent.ForkJoinSumCalculator;
import idv.code.spliterator.WordCounter;
import idv.code.spliterator.WordCounterSpliterator;
import org.junit.Test;

import java.util.Spliterator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Java8InActionCh07Test {
    @Test
    public void testCh07001PerformanceUseIterate() {
        System.out.println("Processor num:" + Runtime.getRuntime().availableProcessors());
        long n = 10_000_000L;
        System.out.println("Sequential sum done in: " + measureSumPerf(this::sequentialSum, n) + " msecs");
        System.out.println("Iterative sum done in: " + measureSumPerf(this::iterativeSum, n) + " msecs");
        System.out.println("Parallel sum done in: " + measureSumPerf(this::parallelSum, n) + " msecs");
    }

    //it’s important that work to be done in parallel on another core takes longer than the time required to transfer the data from one core to another.
    @Test
    public void testCh07001PerformanceUseLongStream() {
        System.out.println("Processor num:" + Runtime.getRuntime().availableProcessors());
        long n = 10_000_000L;
        // 1.LongStream.rangeClosed works on primitive long numbers directly so there’s no boxing and unboxing overhead.
        // 2.LongStream.rangeClosed produces ranges of numbers, which can be easily split into independent chunks. For example,
        // the range 1–20 can be split into 1–5, 6–10, 11–15, and 16–20.
        System.out.println("Sequential range sum done in: " + measureSumPerf(this::sequentialRangedSum, n) + " msecs");
        System.out.println("Parallel range sum done in: " + measureSumPerf(this::parallelRangedSum, n) + " msecs");
    }

    private long parallelSum(long n) {
        long sum = Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
        return sum;
    }

    private long parallelRangedSum(long n) {
        long sum = LongStream.rangeClosed(1, n)
                .parallel()
                .reduce(0L, Long::sum);
        return sum;
    }

    private long sequentialSum(long n) {
        long sum = Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .reduce(0L, Long::sum);
        return sum;
    }

    private long sequentialRangedSum(long n) {
        long sum = LongStream.rangeClosed(1, n)
                .reduce(0L, Long::sum);
        return sum;
    }

    private long iterativeSum(long n) {
        long sum = 0;
        for (long i = 1L; i <= n; i++) {
            sum = sum + i;
        }
        return sum;
    }

    public long measureSumPerf(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + sum);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }

    @Test
    public void testCh07002() {
        long n = 10_000_000L;
        System.out.println("forkJoin range sum done in: " + measureSumPerf(this::forkJoinSum, n) + " msecs");
    }

    private long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return new ForkJoinPool().invoke(task);
    }

    final String SENTENCE = " Nel mezzo del cammin di nostra vita mi ritrovai in una selva oscura ché la dritta via era smarrita ";

    @Test
    public void testCh07003() {
        //Iterator
        System.out.println("Found " + countWordsIteratively(SENTENCE) + " words");

        //functional style
        Stream<Character> stream = IntStream.range(0, SENTENCE.length()).mapToObj(SENTENCE::charAt);
        System.out.println("Found " + countWords(stream) + " words");

        //parallel
        Spliterator<Character> spliterator = new WordCounterSpliterator(SENTENCE);
        Stream<Character> parallelStream = StreamSupport.stream(spliterator, true); //創建一個parallel Stream, use spliterator
        System.out.println("Found " + countWords(parallelStream) + " words");

    }

    private int countWordsIteratively(String s) {
        int counter = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) counter++;
                lastSpace = false;
            }
        }
        return counter;
    }

    private int countWords(Stream<Character> stream) {
        WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
                WordCounter::accumulate,
                WordCounter::combine);
        return wordCounter.getCounter();
    }

}
