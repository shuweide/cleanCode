package idv.code.concurrent;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

// CountDownLatch和CyclicBarrier都能够实现线程之间的等待，只不过它们侧重点不同
// CountDownLatch一般用于某个线程A等待若干个其他线程执行完任务之后，它才执行
public class CountDownLatchTest {
    final CountDownLatch latch = new CountDownLatch(2);

    @Test
    public void testStandard() {
        Runnable runnable = () ->
        {
            try {
                System.out.println(Thread.currentThread().getName() + "正在執行");
                Thread.sleep(3000);
                System.out.println(Thread.currentThread().getName() + "執行完畢");
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable runnable10Seconds = () ->
        {
            try {
                System.out.println(Thread.currentThread().getName() + "正在執行");
                Thread.sleep(10000);
                System.out.println(Thread.currentThread().getName() + "執行完畢");
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        try {
            System.out.println("等待兩個Thread執行完畢");
            new Thread(runnable).start();
            new Thread(runnable).start();
            new Thread(runnable10Seconds).start();
            latch.await();
            System.out.println("兩個Thread都已執行完畢");
            System.out.println("continue main thread");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
