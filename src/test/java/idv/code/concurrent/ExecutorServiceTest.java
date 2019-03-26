package idv.code.concurrent;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceTest {
    @Test
    public void testRunnable() throws InterruptedException {

        System.out.println("Thread Pool Runnable");

        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        List<AccumRunnable> tasks = new ArrayList<>(10);

        for (int i = 0; i < 10; i++) {
            AccumRunnable task = new AccumRunnable(i * 10 + 1, (i + 1) * 10);
            tasks.add(task);

            threadPool.execute(task);
        }
        threadPool.shutdown();

        threadPool.awaitTermination(1, TimeUnit.HOURS);

        int total = 0;
        for (AccumRunnable task : tasks) {
            total += task.getResult();
        }

        System.out.println("Total: " + total);
    }

    static final class AccumRunnable implements Runnable {

        private final int begin;
        private final int end;

        private int result;

        public AccumRunnable(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        public void run() {
            result = 0;
            try {
                for (int i = begin; i <= end; i++) {
                    result += i;
                    Thread.sleep(100);
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace(System.err);
            }
            System.out.printf("(%s) - 运行结束，结果为 %d\n",
                    Thread.currentThread().getName(), result);
        }

        public int getResult() {
            return result;
        }
    }

    @Test
    public void testCallable() throws ExecutionException, InterruptedException {
        System.out.println("使用线程池运行 Callable 任务：");

        ExecutorService threadPool = Executors.newFixedThreadPool(5); // 创建大小固定为 5 的线程池
        List<Future<Integer>> futures = new ArrayList<>(10);

        for (int i = 0; i < 10; i++) {
            AccumCallable task = new AccumCallable(i * 10 + 1, (i + 1) * 10);
            Future<Integer> future = threadPool.submit(task); // 提交任务
            futures.add(future);
        }
        threadPool.shutdown(); // 向线程池发送关闭的指令，等到已经提交的任务都执行完毕之后，线程池会关闭

        int total = 0;
        for (Future<Integer> future : futures) {
            total += future.get(); // 阻塞，直到任务结束，返回任务的结果
        }

        System.out.println("Total: " + total);
    }

    static final class AccumCallable implements Callable<Integer> {

        private final int begin;
        private final int end;

        public AccumCallable(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        public Integer call() throws Exception {
            int result = 0;
            for (int i = begin; i <= end; i++) {
                result += i;
                Thread.sleep(100);
            }
            System.out.printf("(%s) - 运行结束，结果为 %d\n",
                    Thread.currentThread().getName(), result);

            return result;
        }

    }

    @Test
    public void testInvokeAll() throws InterruptedException, ExecutionException {
        System.out.println("使用线程池 invokeAll 运行一组 Callable 任务：");

        ExecutorService threadPool = Executors.newFixedThreadPool(5); // 创建大小固定为 5 的线程池

        List<AccumCallable> tasks = new ArrayList<>(10); // tasks 为一组任务
        for (int i = 0; i < 10; i++) {
            tasks.add(new AccumCallable(i * 10 + 1, (i + 1) * 10));
        }

        List<Future<Integer>> futures = threadPool.invokeAll(tasks); // 阻塞，直到所有任务都运行完毕

        int total = 0;
        for (Future<Integer> future : futures) {
            total += future.get(); // 返回任务的结果
        }

        System.out.println("Total: " + total);

        threadPool.shutdown(); // 向线程池发送关闭的指令
    }

    @Test
    public void testInvokeAny() throws InterruptedException, ExecutionException {
        System.out.println("使用线程池 invokeAny 运行一组 Callable 任务：");

        ExecutorService threadPool = Executors.newFixedThreadPool(5); // 创建大小固定为 5 的线程池

        List<AccumCallable> tasks = new ArrayList<>(10); // tasks 为一组任务
        for (int i = 0; i < 10; i++) {
            tasks.add(new AccumCallable(i * 10 + 1, (i + 1) * 10));
        }

        int total = threadPool.invokeAny(tasks);
        System.out.println("Total: " + total);

        threadPool.shutdown(); // 向线程池发送关闭的指令
    }
}
