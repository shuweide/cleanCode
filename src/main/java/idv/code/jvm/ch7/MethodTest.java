package idv.code.jvm.ch7;

import java.lang.reflect.Method;

public class MethodTest {

    public static void target(int i) {
        new Exception("#" + i).printStackTrace();
    }

    public static void main(String[] args) throws Exception {
        Class<?> klass = Class.forName("idv.code.jvm.ch7.MethodTest");
        Method method = klass.getMethod("target", int.class);
        method.invoke(null, 0);
    }
}
