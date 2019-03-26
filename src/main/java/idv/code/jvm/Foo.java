package idv.code.jvm;

public class Foo {

    public Foo() {
        System.out.println("Foo Create!");
    }

    public static void main(String[] args) {
        boolean flag = true;
        if (flag) System.out.println("Hello, Java!");
        if (flag == true) System.out.println("Hello, JVM!");
    }
}
