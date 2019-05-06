package idv.code.network;

import java.io.*;
import java.net.URL;

public class URLTest {

    public static void main(String[] args) {
        InputStream in = null;
        try {
            URL url = new URL("https://test.shuwei.info/index.html?user=test#toc");

            System.out.println(url);
            System.out.println(url.getProtocol());
            System.out.println(url.getHost());
            System.out.println(url.getPort());
            System.out.println(url.getDefaultPort());
            System.out.println(url.getFile());
            System.out.println(url.getPath());
            System.out.println(url.getRef());
            System.out.println(url.getQuery());
            System.out.println(url.getAuthority());

            in = url.openStream();
            in = new BufferedInputStream(in);
            Reader reader = new InputStreamReader(in, "UTF-8");

            int c;
            while ((c = reader.read()) != -1) {
                System.out.print((char) c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
