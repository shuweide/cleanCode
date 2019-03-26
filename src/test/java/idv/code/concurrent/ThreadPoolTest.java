package idv.code.concurrent;

import java.util.Random;

import org.junit.Test;

public class ThreadPoolTest {
    @Test
    public void testStandard() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(5);
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int fi = i;
            threadPool.execute(() ->
            {
                try {
                    Thread.sleep(random.nextInt(1000));
                    System.out.printf(Thread.currentThread().getName() + ": task %d complete\n", fi);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(3000);
        threadPool.shutdown();
    }
}
