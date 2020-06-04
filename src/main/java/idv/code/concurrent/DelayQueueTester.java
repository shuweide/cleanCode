package idv.code.concurrent;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueTester {
    private static DelayQueue<DelayTask> delayQueue = new DelayQueue<>();
    static class DelayTask implements Delayed {
        private final long delay;
        private final long expire;
        private final String msg;
        private final long now;

        DelayTask(long delay, String msg) {
            this.delay = delay;
            this.msg = msg;
            this.now = Instant.now().toEpochMilli();
            this.expire = now + delay;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expire - Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (o == this) // compare zero ONLY if same object
                return 0;
            return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
        }
        @Override
        public String toString() {
            return "DelayTask{" +
                    "delay=" + delay +
                    ", expire=" + expire +
                    ", msg='" + msg + '\'' +
                    ", now=" + now +
                    '}';
        }
    }

    public static void main(String[] args) {
        initConsumer();
        try {
            // 等待消费者初始化完毕
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        delayQueue.add(new DelayTask(1000, "Task1"));
        delayQueue.add(new DelayTask(2000, "Task2"));
        delayQueue.add(new DelayTask(3000, "Task3"));
        delayQueue.add(new DelayTask(4000, "Task4"));
        delayQueue.add(new DelayTask(5000, "Task5"));
    }

    private static void initConsumer() {
        Runnable task = () -> {
            while (true) {
                try {
                    System.out.println("take task time-" + LocalDateTime.now());
                    System.out.println(delayQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread consumer = new Thread(task);
        consumer.start();
    }
}