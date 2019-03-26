package idv.code.jvm.ch8;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleTest {
    public static void bar(Object object) {
        new Exception().printStackTrace();
    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(void.class, Object.class);
        MethodHandle methodHandle = lookup.findStatic(MethodHandleTest.class, "bar", methodType);
        methodHandle.invokeExact(new Object());
    }
}
