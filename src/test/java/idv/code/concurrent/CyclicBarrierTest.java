package idv.code.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

// CyclicBarrier一般用於一組線程互相等待至某個狀態，然後這一組線程再同時執行；
// 另外，CountDownLatch是不能夠重用的，而CyclicBarrier是可以重用的。
public class CyclicBarrierTest {

    @Test
    public void testStandard() {
        CyclicBarrier barrier = new CyclicBarrier(4);

        Runnable runnable = () ->
        {
            System.out.println("Thread" + Thread.currentThread().getName() + "正在寫入數據..");
            try {
                Thread.sleep(3000); //以睡眠來模擬寫入數據操作
                System.out.println("Thread" + Thread.currentThread().getName() + "寫入數據完畢,等待其他任務");
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("所以線程執行完畢，繼續處理其他任務");
        };

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            threads.add(new Thread(runnable));
        }

        threads.forEach(thread -> thread.start());
        threads.forEach(thread ->
        {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testBarrierAction() {

        CyclicBarrier barrier = new CyclicBarrier(4, () -> System.out.println("all Thread is Done"));

        Runnable runnable = () ->
        {
            System.out.println("線程" + Thread.currentThread().getName() + "正在寫入數據...");
            try {
                Thread.sleep(3000); //以睡眠來模擬寫入數據操作
                System.out.println("線程" + Thread.currentThread().getName() + "寫入數據完畢，等待其他線程寫入完畢");
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("所有線程寫入完畢，繼續處理其他任務...");
        };

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            threads.add(new Thread(runnable));
        }

        threads.forEach(thread -> thread.start());
        threads.forEach(thread ->
        {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testBarrierAwait() {

        CyclicBarrier barrier = new CyclicBarrier(4, () -> System.out.println("all Thread is Done"));

        Runnable runnable = () ->
        {
            System.out.println("線程" + Thread.currentThread().getName() + "正在寫入數據...");
            try {
                Thread.sleep(3000); //以睡眠來模擬寫入數據操作
                System.out.println("線程" + Thread.currentThread().getName() + "寫入數據完畢，等待其他線程寫入完畢");
                barrier.await(1000, TimeUnit.MILLISECONDS); //指定此Thread要等待其他Thread多久時間
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            System.out.println("所有線程寫入完畢，繼續處理其他任務...");
        };

        Runnable runnable10Second = () ->
        {
            System.out.println("線程" + Thread.currentThread().getName() + "正在寫入數據...");
            try {
                Thread.sleep(10000); //以睡眠來模擬寫入數據操作
                System.out.println("線程" + Thread.currentThread().getName() + "寫入數據完畢，等待其他線程寫入完畢");
                barrier.await();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("所有線程寫入完畢，繼續處理其他任務...");
        };

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            threads.add(new Thread(runnable));
        }
        threads.add(new Thread(runnable10Second));
        threads.stream().forEach(thread -> thread.start());
        threads.forEach(thread ->
        {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
