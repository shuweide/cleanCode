package idv.code.concurrent;

import java.util.Random;
import java.util.function.Consumer;

import org.junit.Test;

public class ProducerConsumerTest {
    @Test
    public void testWaitAndNotify() throws InterruptedException {
        final ProducerConsumer flowControl = new ProducerConsumer();

        // Create consumer thread
        Thread consumer = new Thread(() ->
        {
            try {
                flowControl.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        consumer.start();

        // Sleep for 1 sec
        Thread.sleep(1000);

        // Create producer thread
        Thread producer = new Thread(() ->
        {
            flowControl.produce("helloworld");
        });
        producer.start();

        consumer.join();
        producer.join();
    }

    //Join這個動詞在流程同步的時候常常會使用，它代表的是等待別人工作的完成；而相對的詞是Fork，代表的是把工作發包給別的thread開始做
    @Test
    public void testJoin() throws InterruptedException {
        // Create workder thread
        Thread worker = new Thread(() ->
        {
            try {
                System.out.println("worker start");
                Thread.sleep(1000);
                System.out.println("worker complete");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        worker.start();
        System.out.println("master wait");
        worker.join();
        System.out.println("master complete");
    }

    @Test
    public void testUseBlockingQueue() throws InterruptedException {
        final ProducerConsumer producerConsumer = new ProducerConsumer();

        // Create consumer thread
        Thread consumer = new Thread(() ->
        {
            Random random = new Random();
            try {
                while (true) {
                    producerConsumer.consumeUseBlockingQueue();
                    Thread.sleep(random.nextInt(100));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        consumer.start();

        // Create producer thread
        Thread producer = new Thread(() ->
        {
            Random random = new Random();
            try {
                int counter = 0;
                while (true) {
                    producerConsumer.produceUseBlockingQueue("message" + counter++);
                    Thread.sleep(random.nextInt(500));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        producer.start();

        producer.join(1000);
        consumer.join(1000);
    }
}
