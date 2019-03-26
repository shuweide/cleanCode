package idv.code.lambda;

import idv.code.StreamForker;
import idv.code.dto.Dish;
import idv.code.dto.DishType;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * Created by weide on 2017-07-17.
 */
public class StreamTest {

    List<Dish> menu = Arrays.asList(
            new Dish("pork", false, 800, DishType.MEAT),
            new Dish("beef", false, 700, DishType.MEAT),
            new Dish("chicken", false, 400, DishType.MEAT),
            new Dish("french fries", true, 530, DishType.OTHER),
            new Dish("rice", true, 350, DishType.OTHER),
            new Dish("season fruit", true, 120, DishType.OTHER),
            new Dish("pizza", true, 550, DishType.OTHER),
            new Dish("prawns", false, 300, DishType.FISH),
            new Dish("salmon", false, 450, DishType.FISH)
    );

    @Test
    public void testThreeHighCalDishNames() {
        List<String> threeHighCaloricDishNames = menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(toList());
        System.out.println(threeHighCaloricDishNames);
    }

    @Test
    public void testThreeHighCalDishNamesPrint() {
        List<String> threeHighCaloricDishNames = menu.stream()
                .filter(dish -> {
                    System.out.println(dish.getName());
                    return dish.getCalories() > 300;
                })
                .map(dish -> {
                    System.out.println(dish.getName());
                    return dish.getName();
                })
                .limit(3)
                .collect(toList());
        System.out.println(threeHighCaloricDishNames);
    }

    @Test
    public void testStreamSkip() {
        menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .skip(2)
                .forEach(dish -> System.out.println(dish.getName()));
    }

    @Test
    public void testFirstTwoMeatDish() {
        List<Dish> firstTwoMeatDish = menu.stream()
                .filter(dish -> dish.getDishType() == DishType.MEAT)
                .limit(2)
                .collect(toList());

        firstTwoMeatDish.stream().map(Dish::getName).forEach(System.out::println);
    }

    List<String> words = Arrays.asList("Hello", "World");

    @Test
    public void testWordsSpilt() {
        //flatMap
        words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .forEach(System.out::println);
    }

    /*
    Given two lists of numbers, how would you return all pairs of numbers? For example, given a list [1, 2, 3]
    and a list [3, 4] you should return [(1, 3), (1, 4), (2, 3), (2, 4), (3, 3), (3, 4)]. For simplicity,
     you can represent a pair as an array with two elements
     */
    @Test
    public void testCh52Question2() {
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<int[]> pairs = numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .map(j -> new int[]{i, j}))
                .collect(toList());

        pairs.stream().map(ints -> Arrays.toString(ints)).forEach(System.out::println);
    }

    /*
    How would you extend the previous example to return only pairs whose sum is divisible by 3?
     For example, (2, 4) and (3, 3) are valid.
     */
    @Test
    public void testCh52Question3() {
        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);
        List<int[]> pairs = numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .filter(j -> (i + j) % 3 == 0)
                        .map(j -> new int[]{i, j}))
                .collect(toList());

        pairs.stream().map(ints -> Arrays.toString(ints)).forEach(System.out::println);
    }

    @Test
    public void testFindAny() {
        menu.stream()
                .filter(Dish::isVegetarian)
                .findAny()
                .ifPresent(dish -> System.out.println(dish.getName()));
    }

    @Test
    public void testReduce() {
        List<Integer> numbers = Arrays.asList(5, 2, 3, 9);
        int sum = numbers.stream().reduce(0, Integer::sum);
        System.out.println(numbers + " sum " + sum);

        numbers.stream().reduce(Integer::sum).ifPresent(System.out::println);
        numbers.stream().reduce(Integer::max).ifPresent(System.out::println);
        numbers.stream().reduce(Integer::min).ifPresent(System.out::println);
    }

    @Test
    public void testCh0506() {
        //map 接收一個 input value 返回一個 output value
        //flatMap 接收一個 input value 返回 0~N個 output value

        //1.
        Stream<int[]> pythagoreanTriples =
                IntStream.rangeClosed(1, 100).boxed()
                        .flatMap(a ->
                                IntStream.rangeClosed(a, 100)
                                        .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                                        .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                        );

        pythagoreanTriples.limit(5).forEach(t -> System.out.println(Arrays.toString(t)));

        //2.
        Stream<double[]> pythagoreanTriples2 =
                IntStream.rangeClosed(1, 100).boxed()
                        .flatMap(a ->
                                IntStream.rangeClosed(a, 100)
                                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                                        .filter(triple -> triple[2] % 1 == 0)
                        );

        pythagoreanTriples2.limit(5).forEach(t -> System.out.println(Arrays.toString(t)));

    }

    @Test
    public void testCh05073() throws URISyntaxException {
        URL url = this.getClass().getResource("/ch0507.txt");
        System.out.print(url);
        try (Stream<String> lines = Files.lines(Paths.get(url.toURI()), Charset.defaultCharset())) {
            lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCh05074Fibonacci() {
        String fibonacciStr = Stream.iterate(new long[]{0, 1}, t -> new long[]{t[1], t[0] + t[1]})
                .limit(20)
                .map(t -> String.valueOf(t[0]))
                .collect(joining(", "));
        System.out.println(fibonacciStr);
    }

    @Test
    public void testStreamFork() {
        Stream<Dish> menuStream = menu.stream();

        //many terminal operate
        StreamForker.Results results = new StreamForker<Dish>(menuStream)
                .fork("shortMenu", s -> s.map(Dish::getName)
                        .collect(joining(", ")))
                .fork("totalCalories", s -> s.mapToInt(Dish::getCalories).sum())
                .fork("mostCaloricDish", s -> s.reduce((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)
                        .get())
                .fork("dishesByType", s -> s.collect(groupingBy(Dish::getDishType)))
                .getResults();

        String shortMenu = results.get("shortMenu");
        int totalCalories = results.get("totalCalories");
        Dish mostCaloricDish = results.get("mostCaloricDish");
        Map<DishType, List<Dish>> dishesByType = results.get("dishesByType");

        System.out.println("Short menu: " + shortMenu);
        System.out.println("Total calories: " + totalCalories);
        System.out.println("Most caloric dish: " + mostCaloricDish);
        System.out.println("Dishes by type: " + dishesByType);
    }

    @Test
    public void testList(){
        double[] doubles = new double[]{1,2,3,4,0,3,0,24,1};

        System.out.println(Arrays.toString(doubles));

        AtomicInteger atomicInteger = new AtomicInteger(-1);
        int[] indexes = Arrays.stream(doubles)
                .filter(i -> atomicInteger.getAndIncrement() >= 0 && i == 0D)
                .mapToInt(i -> atomicInteger.get())
                .toArray();

        System.out.println(Arrays.toString(indexes));
    }

}
