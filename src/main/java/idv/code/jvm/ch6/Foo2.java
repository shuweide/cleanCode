package idv.code.jvm.ch6;

public class Foo2 implements AutoCloseable {

    private final String name;

    public Foo2(String name) {
        this.name = name;
    }

    @Override
    public void close() {
        throw new RuntimeException(name);
    }

    public static void main(String[] args) {
        try (Foo2 foo0 = new Foo2("Foo0"); // try-with-resources
             Foo2 foo1 = new Foo2("Foo1");
             Foo2 foo2 = new Foo2("Foo2")) {
            throw new RuntimeException("Initial");
        }
    }

}
