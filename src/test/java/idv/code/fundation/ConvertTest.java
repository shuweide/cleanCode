package idv.code.fundation;

import org.junit.Test;

public class ConvertTest {
    @Test
    public void testPrimitiveArrayConvert() {
        int[] ints = new int[]{1, 2, 3, 4, 5,};
//        Integer[] integers = (Integer[])ints;
        Object object = ints;
    }
}
