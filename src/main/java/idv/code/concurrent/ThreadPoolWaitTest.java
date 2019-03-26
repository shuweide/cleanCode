package idv.code.concurrent;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolWaitTest {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 30,
            30, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), new ThreadPoolExecutor.CallerRunsPolicy());

    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("qs:%d, ac:%d, ps:%d, ct:%s\n", executor.getQueue().size(), executor.getActiveCount(), executor.getPoolSize(), Thread.currentThread().getName());
            executor.getActiveCount();
        };

        for (int i = 0; i < 1000; i++) {
            executor.execute(runnable);
        }

        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);
    }
}
