package idv.code.lambda;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleFunction;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class LambdasTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testMethodReference() {
        List<String> strings = Arrays.asList("a", "b", "A", "B");
        strings.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
        logger.info("strings");
        strings.forEach(System.out::println);

        List<String> strings2 = Arrays.asList("a", "b", "A", "B");
        strings2.sort(String::compareToIgnoreCase); //Method Reference
        logger.info("strings2");
        strings2.forEach(System.out::println);
    }

    private interface Fruit {
    }

    class Apple implements Fruit {
        private double weight;

        public Apple(Double weight) {
            this.weight = weight;
        }
    }

    class Banana implements Fruit {
        private double weight;

        public Banana(Double weight) {
            this.weight = weight;
        }
    }

    @Test
    public void testConstructorReference() {
        //1
        Function<Double, Apple> supplier = Apple::new;
        System.out.println(supplier.apply(100D));
        System.out.println(supplier.apply(20.3D));
        //2
        Map<String, Function<Double, Fruit>> map = new HashMap<>();
        map.put("apple", Apple::new);
        map.put("banana", Banana::new);

        Apple apple = (Apple) map.get("apple").apply(555.55D);
        logger.info(String.format("%s,%f", apple, apple.weight));
    }

    @Test
    public void testComposingFunction() {
        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> gf = f.andThen(g); //g(f(x))
        int gfResult = gf.apply(1);
        assertEquals(4, gfResult);
        System.out.println(gfResult);

        Function<Integer, Integer> fg = f.compose(g); //f(g(x))
        int fgResult = fg.apply(1);
        System.out.println(fgResult);
        assertEquals(3, fgResult);
    }

    @Test
    public void testIntegrate() {
        DoubleFunction<Double> f = x -> x + 10;
        System.out.println(integrate(f,3,10));
    }

    private double integrate(DoubleFunction<Double> f, double a, double b) {
        return (f.apply(a) + f.apply(b)) * (b - a) / 2.0D;
    }


}
