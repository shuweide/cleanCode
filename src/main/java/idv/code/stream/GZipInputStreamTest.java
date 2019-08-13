package idv.code.stream;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipInputStreamTest {
    public static void main(String[] args) {

        //Bytes String
        String s = "TEST";

        byte[] bytes = new BigInteger(s, 16).toByteArray();
        uncompressGZipToFile(bytes);
        compressFileToGzipHexString();
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

    private static String compressFileToGzipHexString() {

        String output = "";
        try (
                Stream<String> stream = Files.lines(Paths.get("out/detail.json"), StandardCharsets.UTF_8);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                GZIPOutputStream gos = new GZIPOutputStream(out);
        ) {
            StringBuilder sb = new StringBuilder();
            stream.forEach(sb::append);

            System.out.println(sb.toString());

            gos.write(sb.toString().getBytes(StandardCharsets.UTF_8));
            gos.close();

            output = bytesToHex(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (OutputStream out = new BufferedOutputStream(new FileOutputStream("out/detail2.json"))) {
            out.write(output.getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(output);
        return output;
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
