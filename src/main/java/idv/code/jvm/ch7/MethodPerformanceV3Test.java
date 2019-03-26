package idv.code.jvm.ch7;

import java.lang.reflect.Method;

public class MethodPerformanceV3Test {

    //-Djava.lang.Integer.IntegerCache.high=128
    //-Dsun.reflect.noInflation=true

    public static void target(int i) {
        //Empty
    }

    public static void main(String[] args) throws Exception {
        Class<?> klass = Class.forName("idv.code.jvm.ch7.MethodPerformanceV3Test");
        Method method = klass.getMethod("target", int.class);
        method.setAccessible(true); // 關閉權限檢查

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
