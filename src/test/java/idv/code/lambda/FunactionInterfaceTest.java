package idv.code.lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FunactionInterfaceTest {

    private List<String> list = Arrays.asList("aaa", "bbb", "ccc", "");

    @Test
    public void testPredicate() {
        List<String> nonEmpty = list.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        System.out.println(nonEmpty);
    }

    @Test
    public void testConsumer(){
        list.forEach(string -> System.out.println(string + ".test"));
    }

    @Test
    public void testFunction(){
        List<Integer> listLength = list.stream().map(string -> string.length()).collect(Collectors.toList());
        System.out.println(listLength);
    }
}
