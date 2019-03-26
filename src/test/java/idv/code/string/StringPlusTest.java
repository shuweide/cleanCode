package idv.code.string;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

public class StringPlusTest {
    @Test
    public void testStringPlus() {
        LocalDateTime before = LocalDateTime.now();
        String result = "";
        for (int i = 0; i < 1e5; i++) {
            result += "s";
        }
        System.out.println("testStringPlus:" + ChronoUnit.MILLIS.between(before, LocalDateTime.now()));
    }

    @Test
    public void testStringBuilder() {
        LocalDateTime before = LocalDateTime.now();
        StringBuilder result = new StringBuilder((int) 1e5);
        for (int i = 0; i < 1e5; i++) {
            result.append("s");
        }
        System.out.println("testStringBuilder:" + ChronoUnit.MILLIS.between(before, LocalDateTime.now()));
    }
}
