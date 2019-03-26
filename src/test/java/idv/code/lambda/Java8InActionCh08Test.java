package idv.code.lambda;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static java.util.stream.Collectors.toList;

public class Java8InActionCh08Test {

    //[Ch 08 02 001 Strategy pattern]
    @FunctionalInterface
    public interface ValidationStrategy {
        boolean execute(String s);
    }

    public class IsAllLowerCase implements ValidationStrategy {

        @Override
        public boolean execute(String s) {
            return s.matches("[a-z]+");
        }
    }

    public class IsNumeric implements ValidationStrategy {

        @Override
        public boolean execute(String s) {
            return s.matches("\\d+");
        }
    }

    public class Validator {
        private final ValidationStrategy strategy;

        public Validator(ValidationStrategy strategy) {
            this.strategy = strategy;
        }

        public boolean validate(String s) {
            return strategy.execute(s);
        }
    }

    @Test
    public void testCh080201_Strategy() {
        //old
        Validator numericValidator = new Validator(new IsNumeric());
        boolean b1 = numericValidator.validate("aaaa");
        Validator lowerCaseValidator = new Validator(new IsAllLowerCase());
        boolean b2 = lowerCaseValidator.validate("bbbb");

        //Lambdas
        Validator numericValidator2 = new Validator(s -> s.matches("\\d+"));
        boolean b3 = numericValidator2.validate("aaaa");
        Validator lowerCaseValidator2 = new Validator(s -> s.matches("[a-z]+"));
        boolean b4 = lowerCaseValidator2.validate("bbbb");
    }

    //[Ch 08 02 003 Observer pattern]
    @FunctionalInterface
    interface Observer {
        void notify(String tweet);
    }

    class NYTimes implements Observer {

        @Override
        public void notify(String tweet) {
            if (tweet != null && tweet.contains("money")) {
                System.out.println("Breaking news in NY! " + tweet);
            }
        }
    }

    class Guardian implements Observer {

        @Override
        public void notify(String tweet) {
            if (tweet != null && tweet.contains("queen")) {
                System.out.println("Yet another news in London... " + tweet);
            }
        }
    }

    class LeMonde implements Observer {

        @Override
        public void notify(String tweet) {
            if (tweet != null && tweet.contains("wine")) {
                System.out.println("Today cheese, wine and news! " + tweet);
            }
        }
    }

    interface Subject {
        void registerObserver(Observer o);

        void notifyObserver(String tweet);
    }

    class Feed implements Subject {

        private final List<Observer> observers = new ArrayList<>();

        @Override
        public void registerObserver(Observer o) {
            this.observers.add(o);
        }

        @Override
        public void notifyObserver(String tweet) {
            observers.forEach(o -> o.notify(tweet));
        }
    }

    @Test
    public void testCh080203_ObServer() {
        //Old
        Feed feed = new Feed();
        feed.registerObserver(new NYTimes());
        feed.registerObserver(new Guardian());
        feed.registerObserver(new LeMonde());

        //Lambdas
        feed.registerObserver((String tweet) -> {
            if (tweet != null && tweet.toLowerCase().contains("java")) {
                System.out.println("Java world news... " + tweet);
            }
        });

        feed.notifyObserver("The queen said her favourite book is Java 8 in Action!");
    }

    //[Ch 08 02 004 Chain of responsibility pattern]

    abstract class ProcessingObject<T> {
        protected ProcessingObject<T> successor;

        public void setSuccessor(ProcessingObject<T> successor) {
            this.successor = successor;
        }

        public T handle(T input) {
            T r = handleWork(input);
            if (successor != null) {
                return successor.handle(r);
            }
            return r;
        }

        abstract protected T handleWork(T input);
    }

    class HeaderTextProcessing extends ProcessingObject<String> {

        @Override
        protected String handleWork(String text) {
            return "From Raoul, Mario and Alan: " + text;
        }
    }

    class SpellCheckerProcessing extends ProcessingObject<String> {

        @Override
        protected String handleWork(String text) {
            return text.replaceAll("labda", "lambda");
        }
    }

    @Test
    public void testCh080204_Chain() {
        //Old
        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();

        p1.setSuccessor(p2);

        String result = p1.handle("Aren't labdas really sexy?!!");
        System.out.println(result);

        //Lambdas

        UnaryOperator<String> headerProcessing = (String text) -> {
            System.out.println("execute headerProcessing!");
            return "From Raoul, Mario and Alan: " + text;
        };

        UnaryOperator<String> spellCheckerProcessing = (String text) -> {
            System.out.println("execute spellCheckerProcessing!");
            return text.replaceAll("labda", "lambda");
        };

        Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);
        String result2 = pipeline.apply("Aren't labdas really sexy?!!");
        System.out.println(result2);
    }

    class Point {
        private final int x;
        private final int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

    }

    @Test
    @Ignore
    public void testDebugging() {
        List<Point> points = Arrays.asList(new Point(10, 3), null);
        points.stream().map(point -> point.getX()).forEach(System.out::println);
    }

    @Test
    public void testPeek() {
        List<Integer> numbers = Arrays.asList(2, 3, 4, 5);

        numbers.stream()
                .peek(x -> System.out.println("from stream:" + x))
                .map(x -> x + 17)
                .peek(x -> System.out.println("after map:" + x))
                .filter(x -> x % 2 == 0)
                .peek(x -> System.out.println("after filter:" + x))
                .limit(3)
                .peek(x -> System.out.println("after limit:" + x))
                .collect(toList());

    }

}
