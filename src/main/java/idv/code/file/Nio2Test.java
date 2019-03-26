package idv.code.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Nio2Test {
    public static void main(String[] args) {
        File inputFile = new File("input.txt");
        try (InputStream in = new FileInputStream(inputFile)) {
            Files.copy(in, Paths.get("output.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
