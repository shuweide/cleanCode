package idv.code.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.logging.Logger;

public class DaytimeUDPServer {

    private static final int PORT = 13;
    private static final Logger audit = Logger.getLogger("requests");

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            while (true) {
                DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
                socket.receive(request);

                String daytime = LocalDateTime.now().toString();
                byte[] data = daytime.getBytes(StandardCharsets.US_ASCII);
                DatagramPacket response = new DatagramPacket(data, data.length, request.getAddress(), request.getPort());
                socket.send(response);
                audit.info(daytime + " " + request.getAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
