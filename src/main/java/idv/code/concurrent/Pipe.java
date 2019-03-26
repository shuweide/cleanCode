package idv.code.concurrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

public class Pipe {
    private PipedInputStream in;
    private PipedOutputStream out;

    public Pipe() throws IOException {
        in = new PipedInputStream(4096);
        out = new PipedOutputStream(in);
    }

    public String readAll() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }

    public void writeAll(String string){
        try (PrintStream printStream = new PrintStream(out)) {
            printStream.print(string);
        }
    }
}
