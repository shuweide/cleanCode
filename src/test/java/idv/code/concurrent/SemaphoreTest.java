package idv.code.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.junit.Test;

//Semaphore其实和锁有点类似，它一般用于控制对某组资源的访问权限。
public class SemaphoreTest {
    
    private int num = 0;

    @Test
    public void testStandard() {
        Semaphore semaphore = new Semaphore(5);

        Runnable runnable = () ->
        {
            int no = ++num;
            try {
                semaphore.acquire();
                System.out.println("工人" + no + "占用一個機器來生產...");
                Thread.sleep(2000);
                System.out.println("工人" + no + "釋放出機器");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
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
}
