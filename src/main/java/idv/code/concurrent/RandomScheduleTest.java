package idv.code.concurrent;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class RandomScheduleTest {

    static ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(4);


    public static void main(String[] args) throws InterruptedException {
        RandomScheduledExecutor random = new RandomScheduledExecutor(executorService);
        new Thread(() -> random.execute(() -> {
            System.out.println(LocalDateTime.now());
        }, 1000, TimeUnit.MILLISECONDS)).start();

        executorService.schedule(random::cancel, 60, TimeUnit.SECONDS);
    }

    static class RandomScheduledExecutor {

        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        private ScheduledExecutorService executor;
        private volatile boolean up = false;

        RandomScheduledExecutor(ScheduledExecutorService executor) {
            this.executor = executor;
        }

        public void execute(Runnable command,
                             long minWait,
                             TimeUnit unit) {

            System.out.println(LocalDateTime.now());
            lock.lock();
            up = true;
            try {
                while (up) {
                    try {
                        condition.await(minWait, unit);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    executor.execute(command);
                }
            } finally {
                lock.unlock();
            }

        }

        public void cancel() {
            up = false;
        }
    }
}
