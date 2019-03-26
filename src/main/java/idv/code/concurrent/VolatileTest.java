package idv.code.concurrent;

public class VolatileTest {
    private static volatile boolean running = true;

    public static class AnotherThread extends Thread {

        @Override
        public void run() {
            System.out.println("AnotherThread is running");

            while (running) {
            }

            System.out.println("AnotherThread is stoped");
        }

    }

    public static void main(String[] args) throws Exception {
        new AnotherThread().start();

        Thread.sleep(1000);
        running = false;  // 1 秒之后想停止 AnotherThread
    }
}
