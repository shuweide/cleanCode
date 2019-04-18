package idv.code.stream;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GZipAllFiles {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(4);

        for (String filename : new String[]{"out"}) {
            File f = new File(filename);
            if (f.exists()) {
                if (f.isDirectory()) {
                    File[] files = f.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        if (!files[i].isDirectory()) {
                            Runnable task = new GZipRunnable(files[i]);
                            pool.submit(task);
                        }
                    }
                } else {
                    Runnable task = new GZipRunnable(f);
                    pool.submit(task);
                }
            }
        }

        pool.shutdown();
    }
}
