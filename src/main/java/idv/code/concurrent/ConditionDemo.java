package idv.code.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {
    private ReentrantLock lock = new ReentrantLock();
    private Condition cd1 = lock.newCondition();
    private Condition cd2 = lock.newCondition();
    private Condition cd3 = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        ConditionDemo demo = new ConditionDemo();
        ExecutorService es = Executors.newCachedThreadPool();
        Thread1 tr1 = demo.new Thread1();
        Thread2 tr2 = demo.new Thread2();
        Thread3 tr3 = demo.new Thread3();
        es.execute(tr2);
        es.execute(tr1);
        es.execute(tr3);
        es.execute(tr3);
        es.execute(tr3);
        es.execute(tr3);
        es.execute(tr3);
        Thread.sleep(2000);
        signalAll(demo);
        es.shutdown();
    }

    public static void signalAll(ConditionDemo demo) throws InterruptedException {
        demo.lock.lock();
        demo.cd1.signal();
        demo.lock.unlock();
        Thread.sleep(2000);
        demo.lock.lock();
        demo.cd2.signal();
        demo.lock.unlock();
        Thread.sleep(2000);
        demo.lock.lock();
        demo.cd3.signal();
        demo.cd3.signal();
        demo.cd3.signal();
        demo.cd3.signal();
        demo.cd3.signal();
        demo.lock.unlock();
        Thread.sleep(2000);
    }
    public class Thread1 implements Runnable {
        public Thread1() {
        }
        @Override
        public void run() {
            try {
                Thread.currentThread().setName(Thread1.class.getSimpleName());
                System.out.printf("%s started %d \n", Thread.currentThread().getName(), Thread.currentThread().getId());
                lock.lock();
                cd1.await();
                System.out.printf("%s waked %d\n", Thread.currentThread().getName(),  Thread.currentThread().getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
    public class Thread2 implements Runnable {
        public Thread2() {
        }
        @Override
        public void run() {
            try {
                Thread.currentThread().setName(Thread2.class.getSimpleName());
                System.out.printf("%s started %d\n", Thread.currentThread().getName(), Thread.currentThread().getId());
                lock.lock();
                cd2.await();
                System.out.printf("%s waked %d\n", Thread.currentThread().getName(), Thread.currentThread().getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
    public class Thread3 implements Runnable {
        public Thread3() {
        }
        @Override
        public void run() {
            try {
                Thread.currentThread().setName(Thread3.class.getSimpleName());
                System.out.printf("%s started %d\n", Thread.currentThread().getName(), Thread.currentThread().getId());
                lock.lock();
                cd3.await();
                System.out.printf("%s waked %d\n", Thread.currentThread().getName(), Thread.currentThread().getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
