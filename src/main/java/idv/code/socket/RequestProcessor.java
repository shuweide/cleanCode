package idv.code.socket;

import java.io.File;
import java.net.Socket;

public class RequestProcessor implements Runnable {
    private final File rootDirectory;
    private final String indexFile;
    private final Socket request;

    public RequestProcessor(File rootDirectory, String indexFile, Socket request) {
        this.rootDirectory = rootDirectory;
        this.indexFile = indexFile;
        this.request = request;
    }

    @Override
    public void run() {
        System.out.println("run!");
    }
}
