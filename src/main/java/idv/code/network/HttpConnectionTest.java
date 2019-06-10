package idv.code.network;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectionTest {
    public static void main(String[] args) {
        try {
            URL u = new URL("http://www.google.com.tw");
            HttpURLConnection uc = (HttpURLConnection) u.openConnection();
            int code = uc.getResponseCode();
            String response = uc.getResponseMessage();
            System.out.println("HTTP/1.X " + code + " " + response);
            System.out.println(uc.getHeaderField(0));
            for (int i = 1; ; i++) {
                String header = uc.getHeaderField(i);
                String key = uc.getHeaderFieldKey(i);
                if (header == null || key == null) {
                    break;
                }
                System.out.println(key + ":" + header);
            }
            System.out.println();
            try (InputStream in = new BufferedInputStream(uc.getInputStream())) {
                Reader r = new InputStreamReader(in, "Big5");
                int c;
                while ((c = r.read()) != -1) {
                    System.out.print((char) c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
