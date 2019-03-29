package idv.code.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class BBCSearchGetTest {
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://www.bbc.co.uk/search");
        String rowData = "q=java";
        String encodedData = URLEncoder.encode(rowData, "ASCII");
        String contentType = "application/x-www-form-urlencoded";

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", contentType);
        conn.setRequestProperty("Content-Length", String.valueOf(encodedData.length()));
        conn.setDoOutput(true);

        OutputStream os = conn.getOutputStream();
        os.write(encodedData.getBytes());

        int response = conn.getResponseCode();
        if (response == HttpURLConnection.HTTP_MOVED_PERM
                || response == HttpURLConnection.HTTP_MOVED_TEMP) {
            System.out.println("Moved to: " + conn.getHeaderField("Location"));
        } else {
            try (InputStream in = conn.getInputStream()) {
                Files.copy(in, Paths.get("out/bbc.txt"), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}
