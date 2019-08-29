package idv.code.socket;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingDaytimeServer {

    //-Djava.util.logging.config.file=/out/logging.properties
    private static final Logger auditLogger = Logger.getLogger("requests");
    private static final Logger errorLogger = Logger.getLogger("errors");
    private static final int PORT = 13;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(50);

        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                try {
                    Socket conn = server.accept();
                    Callable<Void> task = new DayTimeTask(conn);
                    pool.submit(task);
                } catch (IOException ex) {
                    errorLogger.log(Level.SEVERE, "accept error", ex);
                } catch (RuntimeException ex) {
                    errorLogger.log(Level.SEVERE, "unexpected error " + ex.getMessage(), ex);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class DayTimeTask implements Callable<Void> {

        private Socket conn;

        DayTimeTask(Socket conn) {
            this.conn = conn;
        }

        @Override
        public Void call() {
            try {
                LocalDateTime now = LocalDateTime.now();
                auditLogger.info(now + " " + conn.getRemoteSocketAddress());
                Writer out = new OutputStreamWriter(conn.getOutputStream());
                out.write(now.toString() + "\r\n");
                out.flush();
            } catch (IOException ignored) {
            } finally {
                try {
                    conn.close();
                } catch (IOException ignored) {
                }
            }
            return null;
        }
    }
}
