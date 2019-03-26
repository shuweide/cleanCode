package idv.code.regex;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RegexTest {
    public static void main(String[] args) {
        String pStr = "\\d";
        String text = "Apollo 13";
        checkAndPrintPatternMatch(pStr, text);

        pStr = "[a..zA..Z]";
        checkAndPrintPatternMatch(pStr, text);

        pStr = "([a..jA..J]*)";
        checkAndPrintPatternMatch(pStr, text);

        text = "abacab";
        pStr = "a....b";
        checkAndPrintPatternMatch(pStr, text);

        pStr = "\\d";
        Pattern pattern = Pattern.compile(pStr);

        String[] inputs = {"Cat", "Dog", "Ice-9", "99 Luftballoons"};
        List<String> containDigits = Arrays.asList(inputs).stream().filter(pattern.asPredicate()).collect(Collectors.toList());
        System.out.println(containDigits);
    }

    private static void checkAndPrintPatternMatch(String pStr, String text) {
        Pattern pattern = Pattern.compile(pStr);
        Matcher matcher = pattern.matcher(text);
        System.out.print(pStr + " matches " + text + "? " + matcher.find());
        System.out.println(" ; match: " + matcher.group());
    }
}
