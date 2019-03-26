package idv.code.jvm.ch5;

public abstract class Passenger {
    abstract void out();

    public static void main(String[] args) {
        Passenger taiwanese = new Taiwanese();
        Passenger american = new American();

        long current = System.currentTimeMillis();
        for (int i = 1; i <= 2_000_000_000; i++) {
            if (i % 1_000_000_000 == 0) {
                long temp = System.currentTimeMillis();
                System.out.println(i / 1_000_000_000 + ":" + (temp - current));
                current = temp;
            }
            Passenger other = (i > 1_000_000_000) ? american : taiwanese;
            other.out();
        }
    }
}
