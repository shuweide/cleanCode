package idv.code.stream;

import java.io.*;

public class streamWriterAndReaderTest {
    public static void main(String[] args) {
        //test writer file use big5 encoding
        try (OutputStream outputStream = new FileOutputStream("out/big5file");
             OutputStreamWriter writer = new OutputStreamWriter(outputStream, "BIG5")) {
            writer.write("測試" + writer.getEncoding() + "編碼的輸出");
            writer.write("嗨嗨嗨");
            writer.write("測試一下");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //test reader file use big5 encoding
        try (Reader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream("out/big5file"), "BIG5")
                , 1024)
        ) {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                sb.append("U+").append(Integer.toHexString(c)).append("-")
                        .append(Character.toChars(c))
                        .append(System.lineSeparator());
            }
            System.out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
