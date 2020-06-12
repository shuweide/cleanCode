package idv.code.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReadWriteLockDemoTest {
    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        final ReadWriteLockDemo rwd = new ReadWriteLockDemo();

        es.execute(rwd::cacheRead);
        es.execute(rwd::cacheRead);
        es.execute(rwd::cacheRead);
        es.execute(rwd::cacheWrite);
        es.execute(rwd::cacheWrite);
        es.execute(rwd::cacheRead);
        es.execute(rwd::cacheRead);
        es.execute(rwd::cacheWrite);

        es.shutdown();
    }
}
