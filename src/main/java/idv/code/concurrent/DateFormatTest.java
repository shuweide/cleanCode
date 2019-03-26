package idv.code.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class DateFormatTest {
    public static void main(String[] args) throws Exception {
        ExecutorService threadPool = Executors.newCachedThreadPool(); // 创建无大小限制的线程池

        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            DateFormatTask task = new DateFormatTask();
            Future<?> future = threadPool.submit(task); // 将任务提交到线程池

            futures.add(future);
        }

        threadPool.shutdown();

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (ExecutionException ex) { // 运行时如果出现异常则进入 catch 块
                System.err.println("执行时出现异常：" + ex.getMessage());
            }
        }

    }

    static class DateFormatTask implements Callable<Void> {

        @Override
        public Void call() throws Exception {
            String str = DateFormatWrapper.format(
                    DateFormatWrapper.parse("2017-07-17 16:54:54"));
            System.out.printf("Thread(%s) -> %s\n", Thread.currentThread().getName(), str);

            return null;
        }

    }
}
