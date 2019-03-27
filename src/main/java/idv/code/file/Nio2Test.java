package idv.code.file;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.jar.Attributes;

public class Nio2Test {
    public static void main(String[] args) {
        File inputFile = new File("src/main/resources/input.txt");
        try (InputStream in = new FileInputStream(inputFile)) {
            Files.copy(in, Paths.get("out/output.txt"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main2(String[] args) {
        Path source, target;
        Attributes attr;
        Charset cs = Sta
    }
}
