package idv.code.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class HttpTest {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://www.google.com");
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get("out/output.txt"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
