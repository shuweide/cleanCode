package idv.code.socket;

import java.io.*;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestProcessor implements Runnable {

    public static final Logger logger = Logger.getLogger(RequestProcessor.class.getCanonicalName());

    private File rootDirectory;
    private String indexFileName;
    private Socket conn;

    public RequestProcessor(File rootDirectory, String indexFileName, Socket conn) {

        if (!rootDirectory.isDirectory()) {
            throw new IllegalArgumentException("rootDirectory must be a directory, not a file");
        }
        try {
            this.rootDirectory = rootDirectory.getCanonicalFile();
        } catch (IOException ignored) {
        }

        if (indexFileName != null) {
            this.indexFileName = indexFileName;
        }
        this.conn = conn;
    }

    @Override
    public void run() {
        String root = rootDirectory.getPath();
        try {
            OutputStream raw = new BufferedOutputStream(conn.getOutputStream());
            Writer out = new OutputStreamWriter(raw);
            Reader in = new InputStreamReader(new BufferedInputStream(conn.getInputStream()));

            StringBuilder requestLine = new StringBuilder();
            while (true) {
                int c = in.read();
                if (c == '\r' || c == '\n') break;
                requestLine.append((char) c);
            }

            String get = requestLine.toString();

            logger.info(conn.getRemoteSocketAddress() + " " + get);
            String[] tokens = get.split("\\s+");
            String method = tokens[0];
            String version = "";
            if (tokens.length > 2) {
                version = tokens[2];
            }
            if (method.equals("GET")) {
                String fileName = tokens[1];
                if (fileName.endsWith("/")) fileName += indexFileName;
                String contentType = URLConnection.getFileNameMap().getContentTypeFor(fileName);

                File theFile = new File(rootDirectory, fileName.substring(1, fileName.length()));
                if (theFile.canRead() && theFile.getCanonicalPath().startsWith(root)) {
                    byte[] theData = Files.readAllBytes(theFile.toPath());
                    if (version.startsWith("HTTP/")) {
                        sendHeader(out, "HTTP/1.0 200 OK", contentType, theData.length);
                    }
                    raw.write(theData);
                    raw.flush();
                } else {
                    String body = "<HTML>\r\n" +
                            "<HEAD><TITLE>File Not Found</TITLE>\r\n" +
                            "<HEAD>\r\n" +
                            "<BODY>" +
                            "<H1>HTTP Error 404: File Not Found</H1>\r\n" +
                            "</BODY></HTML>\r\n";
                    if (version.startsWith("HTTP/")) {
                        sendHeader(out, "HTTP/1.0 404 File Not Found",
                                "text/html; charset=utf-8", body.length());
                    }
                    out.write(body);
                    out.flush();
                }
            } else { //not GET
                String body = "<HTML>\r\n" +
                        "<HEAD><TITLE>Not Implemented</TITLE>\r\n" +
                        "<HEAD>\r\n" +
                        "<BODY>" +
                        "<H1>HTTP Error 501: Not Implemented</H1>\r\n" +
                        "</BODY></HTML>\r\n";
                if (version.startsWith("HTTP/")) {
                    sendHeader(out, "HTTP/1.0 501 Not Implemented",
                            "text/html; charset=utf-8", body.length());
                }
                out.write(body);
                out.flush();
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error talking to " + conn.getRemoteSocketAddress());
        } finally {
            try {
                conn.close();
            } catch (IOException ignored) {
            }
        }
    }

    private void sendHeader(Writer out, String responseCode, String contentType, int length) throws IOException {
        out.write(responseCode + "\r\n");
        LocalDateTime now = LocalDateTime.now();
        out.write("Date: " + now + "\r\n");
        out.write("Server: JHTTP 2.0\r\n");
        out.write("Content-length: " + length + "\r\n");
        out.write("Content-type: " + contentType + "\r\n\r\n");
        out.flush();
    }
}
