package idv.code.collector;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.IDENTITY_FINISH;

//T , A , R
public class ToListCollector<T> implements Collector<T, List<T>, List<T>> {

    //A
    @Override
    public Supplier<List<T>> supplier() {
        return ArrayList::new;
    }

    //A, T
    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return List::add;
    }

    //A
    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    //A, R
    @Override
    public Function<List<T>, List<T>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, CONCURRENT));
    }
}
