package idv.code.lambda;

import idv.code.collector.PrimeNumbersCollector;
import idv.code.collector.ToListCollector;
import idv.code.dto.Dish;
import idv.code.dto.DishType;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static idv.code.lambda.JavaInAction8Ch06Test.CaloricLevel.*;
import static java.util.stream.Collectors.partitioningBy;

public class JavaInAction8Ch06Test {

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
    public void test06002() {
        //060021

        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
        menu.stream().collect(Collectors.maxBy(dishCaloriesComparator)).ifPresent(System.out::println);

        //060022
        int totalCalories = menu.stream().collect(Collectors.summingInt(Dish::getCalories));
        double avgCalories = menu.stream().collect(Collectors.averagingInt(Dish::getCalories));

        System.out.println("totalCalories:" + totalCalories);
        System.out.println("avgCalories:" + avgCalories);

        //Statistics
        IntSummaryStatistics menuStatistics = menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println(menuStatistics);

        //060023
        String shortMenu = menu.stream().map(Dish::getName).collect(Collectors.joining(", "));
        System.out.println("shortMenu:" + shortMenu);

    }

    public enum CaloricLevel {DIET, NORMAL, FAT}

    @Test
    public void test06003() {

        //060031 DishType, CaloricLevel, List
        System.out.println("========060031========");
        Map<DishType, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel =
                menu.stream().collect(
                        Collectors.groupingBy(Dish::getDishType, Collectors.groupingBy(dish -> {
                                    if (dish.getCalories() <= 400) return DIET;
                                    else if (dish.getCalories() <= 700) return NORMAL;
                                    else return FAT;
                                })
                        )
                );

        System.out.println(dishesByTypeCaloricLevel);

        //060032
        System.out.println("=========060032========");
        Map<DishType, Long> typesCount = menu.stream().collect(Collectors.groupingBy(Dish::getDishType, Collectors.counting()));
        System.out.println(typesCount);

        Map<DishType, Dish> mostCaloricByType = menu.stream()
                .collect(Collectors.groupingBy(Dish::getDishType,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)), Optional::get
                        )));
        System.out.println(mostCaloricByType);

        //mapping
        Map<DishType, Set<CaloricLevel>> caloricLevelsByType =
                menu.stream().collect(
                        Collectors.groupingBy(Dish::getDishType, Collectors.mapping(
                                dish -> {
                                    if (dish.getCalories() <= 400) return DIET;
                                    else if (dish.getCalories() <= 700) return NORMAL;
                                    else return FAT;
                                }, Collectors.toSet()
                        ))
                );
        System.out.println(caloricLevelsByType);

    }

    @Test
    public void test06004() {
        //exercise 1
        Map<Boolean, Map<Boolean, List<Dish>>> exercise01 = menu.stream().collect(partitioningBy(Dish::isVegetarian,
                partitioningBy(d -> d.getCalories() > 500)));
        exercise01.entrySet().forEach(System.out::println);
    }

    private static  <T> Collection<List<T>> partition(List<T> list, int size) {
        final AtomicInteger counter = new AtomicInteger(0);

        return list.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / size)).values();
    }

    @Test
    public void testSizePartition(){
        Collection<List<Dish>> dishss = partition(menu, 2);
        dishss.forEach(System.out::println);
    }

    @Test
    public void test06005() {
        List<Dish> dishes = menu.stream().filter(Dish::isVegetarian).collect(new ToListCollector<>());
        System.out.println(dishes);
    }

    //質數（Prime number），又稱素數，指在大於1的自然數中，除了1和該數自身外，無法被其他自然數整除的數（也可定義為只有1與該數本身兩個因數的數）。大於1的自然數若不是質數，則稱之為合數。
    //自然數，可以是指正整數( 1, 2, 3, 4, ...)，亦可以是非負整數( 0, 1, 2, 3, 4, ...)。在數論中通常用前者，而集合論和計算機科學中多數使用後者。

    @Test
    public void test06006() {

        int maxNum = 100;

        //findPrime 1
        Map<Boolean, List<Integer>> numeralSeparateByPrimes = IntStream
                .rangeClosed(2, maxNum).boxed()
                .collect(partitioningBy(candidate -> isPrime(candidate)));

        numeralSeparateByPrimes.entrySet().forEach(System.out::println);

        System.out.println("============use PrimeNumbersCollector=============");

        Map<Boolean, List<Integer>> numeralSeparateByPrimes2 = IntStream
                .rangeClosed(2, maxNum).boxed()
                .collect(new PrimeNumbersCollector());

        numeralSeparateByPrimes2.entrySet().forEach(System.out::println);
    }

    private boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot).noneMatch(i -> candidate % i == 0);
    }

}
