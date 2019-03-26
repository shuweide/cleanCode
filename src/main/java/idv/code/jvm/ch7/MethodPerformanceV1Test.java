package idv.code.jvm.ch7;

import java.lang.reflect.Method;

public class MethodPerformanceV1Test {

    //與直接調用約差3倍

    public static void target(int i) {
        //Empty
    }

    public static void main(String[] args) throws Exception {
        Class<?> klass = Class.forName("idv.code.jvm.ch7.MethodPerformanceV1Test");
        Method method = klass.getMethod("target", int.class);

        long current = System.currentTimeMillis();

        for (int i = 1; i < 2_000_000_000; i++) {

            if (i % 100_000_000 == 0) {
                long temp = System.currentTimeMillis();
                System.out.println(temp - current);
                current = temp;
            }
            method.invoke(null, 128);
        }
    }
}
