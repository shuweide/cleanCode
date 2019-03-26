package idv.code.lambda;

import org.junit.Test;

import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Java8InActionCh14Test {

    //柯里化是一種將具備2個參數(比如x,y)的函數f轉化為使用一個參數的函數g,並且這個函數的返回值也是一個函數,他會變成新函數的一個參數.
    //即 f(x,y) = (g(x))(y)
    //他表示一種將一個帶有n個位元組參數的函數轉換成n個一元函數練的方法
    //14.1
    @Test
    public void currying() {
        DoubleUnaryOperator convertCtoF = curriedConverter(9.0 / 5, 32);
        DoubleUnaryOperator convertUSDtoGBP = curriedConverter(0.6, 0);
        DoubleUnaryOperator convertKmtoMi = curriedConverter(0.6214, 0);

        System.out.println(String.format("1000USD = %fGBP", convertUSDtoGBP.applyAsDouble(1000)));
        System.out.println(String.format("攝氏10度 = 華氏%f度", convertCtoF.applyAsDouble(10)));

    }

    //單位轉換的泛用方法,不過太過廣泛了
    private double coverter(double input, double factor, double baseline) {
        return input * factor + baseline;
    }

    //定義工廠方法
    private DoubleUnaryOperator curriedConverter(double factor, double baseline) {
        return (x) -> x * factor + baseline;
    }

    //14.3.1
    @Test(expected = IllegalStateException.class)
    public void primes() {
        primes(numbers());
    }

    private IntStream numbers() {
        return IntStream.iterate(2, n -> n + 1);
    }

    private int head(IntStream numbers) {
        return numbers.findFirst().getAsInt();
    }

    private IntStream tail(IntStream numbers) {
        return numbers.skip(1);
    }

    private IntStream primes(IntStream numbers) {
        int head = head(numbers);
        //numbers會在head時就結束Stream的生命週期了,所以tail無法再使用，因為head內執行了terminal operation
        return IntStream.concat(IntStream.of(head), primes(tail(numbers).filter(n -> n % head != 0)));
    }

    //14.3.2
    //利用高階函數特性做延遲執行，在真正創建時，才會做動作。
    @Test
    public void lazyPrimes() {
        LazyList<Integer> numbers = from(2);
        int two = numbers.head();
        int three = numbers.tail().head();
        int four = numbers.tail().tail().head();

        System.out.println(two + " " + three + " " + four);

        numbers = from(2);
        two = primes(numbers).head();
        three = primes(numbers).tail().head();
        int five = primes(numbers).tail().tail().head();

        System.out.println(two + " " + three + " " + five);

        //print all run forever
//        printAll(primes(from(2)));
    }

    interface MyList<T> {
        T head();

        MyList<T> tail();

        MyList<T> filter(Predicate<T> p);

        default boolean isEmpty() {
            return true;
        }
    }

    class LazyList<T> implements MyList<T> {

        final T head;
        //真正執行時才會使用,所以會延遲執行
        final Supplier<MyList<T>> tail;

        public LazyList(T head, Supplier<MyList<T>> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public MyList<T> filter(Predicate<T> p) {
            return isEmpty() ?
                    this :
                    p.test(head()) ?
                            new LazyList<>(head(), () -> tail().filter(p)) :
                            tail().filter(p);
        }

        @Override
        public T head() {
            return head;
        }

        @Override
        public MyList<T> tail() {
            return tail.get();
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }

    private LazyList<Integer> from(int n) {
        return new LazyList<>(n, () -> from(n + 1)); //tail是傳入函數而不是傳入變數!!!!
    }

    private MyList<Integer> primes(MyList<Integer> numbers) {
        return new LazyList<>(
                numbers.head(),
                () -> primes(
                        numbers.tail()
                                .filter(n -> n % numbers.head() != 0)
                )
        );
    }

    private <T> void printAll(MyList<T> list) {
        while (!list.isEmpty()) {
            System.out.println(list.head());
            list = list.tail();
        }
    }

    //14.4 利用jdk8 lambdas 產生類似 Pattern matching的樣子
    @Test
    public void patternMatching() {
        Expr e = new BinOp("+", new Number(5), new Number(1000));
        int result = evaluate(e);
        System.out.println(result);
    }

    class Expr {
    }

    class Number extends Expr {
        int val;

        public Number(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "" + val;
        }
    }

    class BinOp extends Expr {
        String opname;
        Expr left, right;

        public BinOp(String opname, Expr left, Expr right) {
            this.opname = opname;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "(" + left + " " + opname + " " + right + ")";
        }
    }

    private interface TriFunction<S, T, U, R> {
        R apply(S s, T t, U u);
    }

    private <T> T patternMatchExpr(Expr e,
                                   TriFunction<String, Expr, Expr, T> binopcase,
                                   Function<Integer, T> numcase, Supplier<T> defaultcase) {

        if (e instanceof BinOp) {
            return binopcase.apply(((BinOp) e).opname, ((BinOp) e).left, ((BinOp) e).right);
        } else if (e instanceof Number) {
            return numcase.apply(((Number) e).val);
        } else {
            return defaultcase.get();
        }
    }

    private Integer evaluate(Expr e) {
        Function<Integer, Integer> numcase = val -> val;
        Supplier<Integer> defaultcase = () -> 0;
        TriFunction<String, Expr, Expr, Integer> binopcase =
                (opname, left, right) -> {
                    if ("+".equals(opname)) {
                        if (left instanceof Number && right instanceof Number) {
                            return ((Number) left).val + ((Number) right).val;
                        }
                        if (right instanceof Number && left instanceof BinOp) {
                            return ((Number) right).val + evaluate((BinOp) left);
                        }
                        if (left instanceof Number && right instanceof BinOp) {
                            return ((Number) left).val + evaluate((BinOp) right);
                        }
                        if (left instanceof BinOp && right instanceof BinOp) {
                            return evaluate((BinOp) left) + evaluate((BinOp) right);
                        }
                    }
                    if ("*".equals(opname)) {
                        if (left instanceof Number && right instanceof Number) {
                            return ((Number) left).val * ((Number) right).val;
                        }
                        if (right instanceof Number && left instanceof BinOp) {
                            return ((Number) right).val * evaluate((BinOp) left);
                        }
                        if (left instanceof Number && right instanceof BinOp) {
                            return ((Number) left).val * evaluate((BinOp) right);
                        }
                        if (left instanceof BinOp && right instanceof BinOp) {
                            return evaluate((BinOp) left) * evaluate((BinOp) right);
                        }
                    }
                    return defaultcase.get();
                };

        return patternMatchExpr(e, binopcase, numcase, defaultcase);
    }

    @Test
    public void testPrimitiveTypeBoxed() {
        Integer i1 = 3;
        Integer i2 = 3;
        System.out.println(String.format("i1==i2 %b", i1 == i2));

        Long l1 = 3L;
        Long l2 = 3L;
        System.out.println(String.format("l1==l2 %b", l1 == l2));

        Float f1 = 1.23F;
        Float f2 = 1.23F;
        System.out.println(String.format("f1==f2 %b", f1 == f2));

        Double d1 = 4.3333D;
        Double d2 = 4.3333D;
        System.out.println(String.format("d1==d2 %b", d1 == d2));

    }
}
