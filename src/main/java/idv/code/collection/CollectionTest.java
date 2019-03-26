package idv.code.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class CollectionTest {

    public static void main(String... args) throws InterruptedException {

        System.out.print("Queue\n");

        for (int i = 0; i < 100; i++) {
            testQueue();
        }

        System.out.print("\nMap\n");

        for (int i = 0; i < 100; i++) {
            testMap();
        }
    }

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    private static void testMap() throws InterruptedException {
        Map<Integer, Seat> seatMap = new ConcurrentHashMap<>();
        IntStream.range(0, 1000000).forEach(i ->
                seatMap.put(i, new Seat(i))
        );

        ExecutorService executorService = Executors.newCachedThreadPool();

        List<Future> future2s = new ArrayList<>();

        atomicInteger.set(0);

        long startTime = System.nanoTime();

        for (int i = 0; i < 100; i++) {
            future2s.add(executorService.submit(() -> {
                seatMap.remove(atomicInteger.getAndIncrement());
            }));
        }

        executorService.shutdown();
        executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);

        long endTime = System.nanoTime();

        System.out.printf("execute msec = %d, map size= %d \n", (endTime - startTime) / 1_000_000L, seatMap.size());
    }

    private static void testQueue() throws InterruptedException {
        Queue<Seat> seatQueue = new LinkedBlockingQueue<>();
        IntStream.range(0, 1000000).forEach(i ->
                seatQueue.offer(new Seat(i))
        );

        ExecutorService executorService = Executors.newCachedThreadPool();

        List<Future> future2s = new ArrayList<>();

        atomicInteger.set(0);

        long startTime = System.nanoTime();

        for (int i = 0; i < 100; i++) {
            Seat seat = new Seat(atomicInteger.getAndIncrement());
            future2s.add(executorService.submit(() -> {
                seatQueue.remove(seat);
            }));
        }

        executorService.shutdown();
        executorService.awaitTermination(1000, TimeUnit.MILLISECONDS);

        long endTime = System.nanoTime();

        System.out.printf("execute msec = %d, queue size= %d \n", (endTime - startTime) / 1_000_000L, seatQueue.size());
    }

    private static class Seat {
        private int number;

        Seat(int number) {
            this.number = number;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Seat) {
                return this.number == ((Seat) obj).number;
            } else {
                return false;
            }
        }
    }
}
