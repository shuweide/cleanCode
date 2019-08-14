package idv.code.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class LowPortScanner {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        for (int i = 1; i <= 1024; i++) {
            try {
                Socket socket = new Socket();
                SocketAddress address = new InetSocketAddress(host, i);
                socket.connect(address, 10);
                System.out.printf("Connected to %s on port %d from port %d of %s \n",
                        socket.getInetAddress(), socket.getPort(), socket.getLocalPort(),
                        socket.getLocalAddress());
                socket.close();
            } catch (UnknownHostException e) {
                System.err.println(e);
            } catch (IOException ignored) {
            }
        }
    }
}
