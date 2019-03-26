package idv.code.regex;

import org.junit.Test;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * http://www.runoob.com/java/java-regular-expressions.html
 */
public class RegExTest {

    private final String PATTERN_01 = "this is text";
    // \s代表空格, +代表1~多個
    private final String PATTERN_02 = "this\\s+is\\s+text";
    // ^代表開頭 \d代表數字 \.代表dot ()代表子pattern ?代表0~1個
    private final String PATTERN_03 = "^\\d+(\\.\\d+)?";

    @Test
    public void testPattern() {
        String[] strs = new String[]{"this is text", "this text", "this      is       text",
                "3", "1.222", "332.1", "2222rrr.333"};

        System.out.println("Pattern :" + PATTERN_01);
        Arrays.stream(strs).filter(str -> Pattern.matches(PATTERN_01, str)).forEach(System.out::println);
        System.out.println("Pattern :" + PATTERN_02);
        Arrays.stream(strs).filter(str -> Pattern.matches(PATTERN_02, str)).forEach(System.out::println);
        System.out.println("Pattern :" + PATTERN_03);
        Arrays.stream(strs).filter(str -> Pattern.matches(PATTERN_03, str)).forEach(System.out::println);
    }

    private final String URL_PATTERN_01 = "https?://";
    // [.]代表匹配任. .*代表任何\r\n以外的字符0到多次
    private final String URL_PATTERN_02 = "/index[.].*";
    // \w代表[A-Za-z0-9_]
    private final String URL_PATTERN_03 = "([.]\\w+)?([?#].*)?";
    // $代表結尾
    private final String URL_PATTERN_04 = "/$";
    private final String URL_PATTERN_ALL = URL_PATTERN_01 + "|" + URL_PATTERN_02 + "|"
            + URL_PATTERN_03 + "|" + URL_PATTERN_04;

    @Test
    public void testUrlPattern() {
        String[] strs = new String[]{".html", "http://", "https://",
                ".html?a=9", ".asp#abc", "987/", "/index.html",
                "https://www.google.com/pictures/example?a=2&b=1", "/"};
        System.out.println("Pattern :" + URL_PATTERN_01);
        Arrays.stream(strs).filter(str -> Pattern.matches(URL_PATTERN_01, str)).forEach(System.out::println);

        System.out.println("Pattern :" + URL_PATTERN_02);
        Arrays.stream(strs).filter(str -> Pattern.matches(URL_PATTERN_02, str)).forEach(System.out::println);

        System.out.println("Pattern :" + URL_PATTERN_03);
        Arrays.stream(strs).filter(str -> Pattern.matches(URL_PATTERN_03, str)).forEach(System.out::println);

        System.out.println("Pattern :" + URL_PATTERN_04);
        Arrays.stream(strs).filter(str -> Pattern.matches(URL_PATTERN_04, str)).forEach(System.out::println);

        String url = "https://www.google.com/";
        System.out.println("Replace Pattern :" + URL_PATTERN_ALL);
        url = url.replaceAll(URL_PATTERN_ALL, "");
        System.out.println(url);
    }

}
