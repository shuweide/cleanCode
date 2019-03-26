package idv.code.fundation;

import org.junit.Test;

public class NumberTest {
    @Test
    public void testDouble() {
        double inf = 1.0 / 0.0;
        double inf2 = Double.MAX_VALUE * 1.1;
        double neginf = 1.0 / -0.0;
        double neginf2 = -Double.MAX_VALUE * 1.1;
        double negzero = -1.0 / inf;
        double nan = 0.0 / 0.0;

        System.out.printf("%f %f %f %f %f %f", inf, inf2, neginf, neginf2, negzero, nan);
    }

    @Test
    public void testInteger() {
        int overFlow = Integer.MAX_VALUE + 1;
        int negOverFlow = -Integer.MAX_VALUE - 2;

        System.out.printf("%d %d", overFlow, negOverFlow);
    }
}
