package idv.code.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class ChargenClient {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Usage: java ChargenClient host");
            return;
        }

        SocketAddress address = new InetSocketAddress(args[0], 19);

        try (SocketChannel client = SocketChannel.open(address);
             WritableByteChannel out = Channels.newChannel(System.out);
        ) {
            client.configureBlocking(false); //set nio
            ByteBuffer buffer = ByteBuffer.allocate(74);

//            while (client.read(buffer) != -1) {
//                buffer.flip();
//                out.write(buffer);
//                buffer.clear();
//            }
            //NIO mode
            while (true) {
                int n = client.read(buffer);
                if (n > 0) {
                    buffer.flip();
                    out.write(buffer);
                    buffer.clear();
                } else if (n == -1) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
