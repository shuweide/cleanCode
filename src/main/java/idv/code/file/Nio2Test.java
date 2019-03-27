package idv.code.file;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.regex.Pattern;

public class Nio2Test {
    public static void main(String[] args) throws IOException {
        File inputFile = new File("src/main/resources/input.txt");
        try (InputStream in = new FileInputStream(inputFile)) {
            Files.copy(in, Paths.get("out/output.txt"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("testPaths");
        testPaths();
        System.out.println("testDirectoryStream");
        testDirectoryStream();
        System.out.println("testFilesFind");
        testFilesFind();
    }

    private static void testPaths() {
        Path path = Paths.get("out/output.txt");
        try (BufferedWriter writer =
                     Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.WRITE)) {
            writer.write("Hello Paths");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testDirectoryStream() {
        try (DirectoryStream<Path> stream =
                     Files.newDirectoryStream(Paths.get("src/main/java/idv/code/file"), "*.java")) {
            for (Path path : stream) {
                System.out.println(path + ": " + Files.size(path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testFilesFind() throws IOException {
        final Pattern isJava = Pattern.compile(".*\\.java$");
        final Path homeDir = Paths.get("src/main/java/");
        Files.find(homeDir, 255, ((path, basicFileAttributes) ->
                isJava.matcher(path.toString()).find()))
                .forEach(path -> System.out.println(path.normalize()));
    }
}
