package idv.code.stream;

import java.io.*;
import java.util.zip.GZIPInputStream;

public class GZipInputStreamTest {
    public static void main(String[] args) {
        byte[] bytes = new byte[]{33, 33, 44};
        uncompressGZipToFile(bytes);
    }

    private static void uncompressGZipToFile(byte[] bytes) {
        try (InputStream in = new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(bytes)));
             OutputStream out = new BufferedOutputStream(new FileOutputStream("out/detail.json"));
        ) {
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
