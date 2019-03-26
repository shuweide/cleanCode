package idv.code.jvm.ch7;

import java.lang.reflect.Method;

public class MethodPerformanceV4Test {

    //-Djava.lang.Integer.IntegerCache.high=128
    //-Dsun.reflect.noInflation=true

    public static void target(int i) {
        //Empty
    }

    public static void main(String[] args) throws Exception {
        Class<?> klass = Class.forName("idv.code.jvm.ch7.MethodPerformanceV4Test");
        Method method = klass.getMethod("target", int.class);
        method.setAccessible(true); // 關閉權限檢查
        polluteProfile();

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

    public static void polluteProfile() throws Exception {
        Method method1 = MethodPerformanceV4Test.class.getMethod("target1", int.class);
        Method method2 = MethodPerformanceV4Test.class.getMethod("target2", int.class);
        for (int i = 0; i < 2000; i++) {
            method1.invoke(null, 0);
            method2.invoke(null, 0);
        }
    }

    public static void target1(int i) {
    }

    public static void target2(int i) {
    }
}
