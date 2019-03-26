package idv.code.concurrent;

import java.util.concurrent.CompletableFuture;

import org.junit.Test;

//https://popcornylu.gitbooks.io/java_multithread/content/async/cfuture.html
public class CompletableFutureTest {
    @Test
    public void testCompletable() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("Hello");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        future.get();
        System.out.println("World!");
    }

    @Test
    public void testListenable() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("Hello");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).whenComplete((result, throwable) -> {
            System.out.println("World!");
        });
    }

    @Test
    public void testComposible() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> sleep(1000)).thenRunAsync(() -> sleep(1000))
                .thenRunAsync(() -> sleep(1000)).whenComplete((r, ex) -> System.out.println("done"));
        future.get();
    }

    private void sleep(long time) {
        try {
            System.out.printf("sleep for %d milli\n", time);
            Thread.sleep(time);
            System.out.printf("wake up\n");
        } catch (InterruptedException e) {}
    }
    
    
}
