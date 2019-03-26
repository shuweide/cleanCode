package idv.code.concurrent;

import java.io.IOException;

import org.junit.Test;

public class PipeTest {

    @Test
    public void testStandard() throws IOException {
        Pipe pipe = new Pipe();

        // produce a message
        new Thread(() ->
        {
            pipe.writeAll("hello pipe!!");
        }).start();

        // consume the message
        new Thread(() ->
        {
            try {
                System.out.println(pipe.readAll());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
