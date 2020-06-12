package idv.code.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    private ReadWriteLock lk = new ReentrantReadWriteLock();
    private int cacheData = 0;
    public static final Logger logger = LoggerFactory.getLogger(ReadWriteLockDemo.class.getCanonicalName());

    public void cacheRead() {
        try {
            lk.readLock().lock();
            logger.info(String.format("%s get readLock, cache: %d",
                    Thread.currentThread().getName(), cacheData));
            Thread.sleep(1000);
            logger.info(String.format("%s read done, cache: %d",
                    Thread.currentThread().getName(), cacheData));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lk.readLock().unlock();
        }
    }

    public void cacheWrite() {
        try {
            lk.writeLock().lock();
            logger.info(String.format("%s get write lock, cache: %d",
                    Thread.currentThread().getName(), cacheData));
            // 模拟写入线程花费一定时间写入数据。
            Thread.sleep(1000);
            cacheData++;
            logger.info(String.format("%s write done, cache: %d",
                    Thread.currentThread().getName(), cacheData));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lk.writeLock().unlock();
        }
    }
}
