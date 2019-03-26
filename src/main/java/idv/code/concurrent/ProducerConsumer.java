package idv.code.concurrent;

import java.util.concurrent.LinkedBlockingQueue;

public class ProducerConsumer {
    private Object lock = new Object();
    private String message = null;

    public void produce(String message) {
        System.out.println("produce message: " + message);
        synchronized (lock) {
            this.message = message;
            lock.notify();
        }
    }

    public void consume() throws InterruptedException {
        System.out.println("wait for message");
        synchronized (lock) {
            lock.wait();
            System.out.println("consume message: " + message);
        }
    }

    private LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(5);

    public void produceUseBlockingQueue(String message) throws InterruptedException {
        System.out.println("produce message: " + message);
        queue.put(message);
    }

    public void consumeUseBlockingQueue() throws InterruptedException {
        System.out.println("consume message: " + queue.take());
    }
}