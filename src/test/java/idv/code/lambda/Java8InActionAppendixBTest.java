package idv.code.lambda;

import org.junit.Test;

public class Java8InActionAppendixBTest {

    @Test
    public void testUnSigned() {
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Integer.MAX_VALUE);
        int overSignInt = Integer.parseUnsignedInt("3147483649");
        System.out.println(overSignInt);
        System.out.println(Integer.toUnsignedLong(overSignInt));
    }

    @Test
    public void testFinite() {
        double d = 1 / 3;
        double d2 = 1 / 0.0;
        System.out.println(d);
        System.out.println(Double.isFinite(d));
        System.out.println(Double.isInfinite(d));
        System.out.println(Double.isInfinite(d2));
    }
}
