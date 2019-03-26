package idv.code.standard;

import org.junit.Test;

public class ShiftTest {

    @Test
    public void testRightShift(){
        int i = 99999;
        int ir1 = i >> 1;
        int ir2 = i >> 2;
        int ir3 = i >> 3;
        int ir4 = i >> 4;
        System.out.println("original = " + i);
        System.out.println("Shift Right 1 = " + ir1);
        System.out.println("Shift Right 2 = " + ir2);
        System.out.println("Shift Right 3 = " + ir3);
        System.out.println("Shift Right 4 = " + ir4);
    }
}
