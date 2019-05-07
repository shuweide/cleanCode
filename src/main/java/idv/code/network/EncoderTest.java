package idv.code.network;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class EncoderTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        printEncode("This string has spaces");
        printEncode("This*string*has*asterisks");
        printEncode("This%string%has%percent%signs");
        printEncode("This+string+has+pluses");
        printEncode("This/string/has/slashes");
        printEncode("This\"string\"has\"quote\"marks");
        printEncode("This:string:has:colons");
        printEncode("This~string~has~tildes");
        printEncode("This(string)has(parentheses)");
        printEncode("This.string.has.periods");
        printEncode("This=string=has=equals=signs");
        printEncode("This&string&has&ampersands");
        printEncode("這個字串有中文字");

        printDecode("This+string+has+spaces");
        printDecode("%E9%80%99%E5%80%8B%E5%AD%97%E4%B8%B2%E6%9C%89%E4%B8%AD%E6%96%87%E5%AD%97");
    }

    private static void printDecode(String s) throws UnsupportedEncodingException {
        System.out.println(URLDecoder.decode(s, "UTF-8"));
    }

    private static void printEncode(String string) throws UnsupportedEncodingException {
        System.out.println(URLEncoder.encode(string, "UTF-8"));
    }
}
