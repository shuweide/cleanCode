package idv.code.stream;

import java.io.IOException;
import java.io.OutputStream;

public class outputStreamTest {
    public static void main(String[] args) throws IOException {
        generateCharacters(System.out, 30);
    }

    private static void generateCharacters(OutputStream out, int printLines) throws IOException {

        int firstPrintableCharacter = 33;
        int numberOfPrintableCharacters = 94;
        int numberOfCharactersPerLine = 72;
        int start = firstPrintableCharacter;
        int numberOfPrinted = 0;
        byte[] line = new byte[numberOfCharactersPerLine + 2];

        while (true) {
            for (int i = start; i < start + numberOfCharactersPerLine; i++) {
                line[i - start] = (byte) ((i - firstPrintableCharacter)
                        % numberOfPrintableCharacters + firstPrintableCharacter);
            }
            line[72] = (byte) '\r'; //CR
            line[73] = (byte) '\n'; //LF
            out.write(line);
            if (++numberOfPrinted > printLines) {
                break;
            }
            start = ((start + 1) - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter;
        }
    }
}
