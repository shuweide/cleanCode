package idv.code.jvm.ch7;

import java.lang.reflect.Method;

public class MethodInflationTest {

    public static void target(int i) {
        new Exception("#" + i).printStackTrace();
    }

    public static void main(String[] args) throws Exception {
        Class<?> klass = Class.forName("idv.code.jvm.ch7.MethodInflationTest");
        Method method = klass.getMethod("target", int.class);
        for (int i = 0; i < 20; i++) {
            method.invoke(null, i);
        }
    }
}
