package idv.code.jvm.ch7;

import java.lang.reflect.Method;

public class MethodPerformanceV2Test {

    //雖然緩存了 int 及 object[]，由於for內無法判斷arg是否會被更改，因此無法逃逸，跟直接調用一樣差了3倍

    public static void target(int i) {
        //Empty
    }

    public static void main(String[] args) throws Exception {
        Class<?> klass = Class.forName("idv.code.jvm.ch7.MethodPerformanceV2Test");
        Method method = klass.getMethod("target", int.class);

        Object[] arg = new Object[1];
        arg[0] = 128;

        long current = System.currentTimeMillis();

        for (int i = 1; i < 2_000_000_000; i++) {
            if (i % 100_000_000 == 0) {
                long temp = System.currentTimeMillis();
                System.out.println(temp - current);
                current = temp;
            }
            method.invoke(null, arg);
        }
    }
}
