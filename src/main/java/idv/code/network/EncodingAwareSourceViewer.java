package idv.code.network;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class EncodingAwareSourceViewer {
    public static void main(String[] args) {
        String encoding = "ISO-8859-1";
        try {
            URL u = new URL("http://www.google.com.tw");
            URLConnection uc = u.openConnection();
            String contentType = uc.getContentType();
            int encodingStart = contentType.indexOf("charset=");
            if (encodingStart != -1) {
                encoding = contentType.substring(encodingStart + 8);
            }
            InputStream in = new BufferedInputStream(uc.getInputStream());
            Reader r = new InputStreamReader(in, encoding);
            int c;
            while ((c = r.read()) != -1) {
                System.out.print((char) c);
            }
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
