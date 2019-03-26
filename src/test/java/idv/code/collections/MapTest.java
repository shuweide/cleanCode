package idv.code.collections;

import org.junit.Test;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapTest {

    /**
     * result run done
     * key:1 map.get(key):null mapSize:9
     * key:2 map.get(key):null mapSize:8
     * key:3 map.get(key):null mapSize:7
     * key:4 map.get(key):null mapSize:6
     * key:5 map.get(key):null mapSize:5
     * key:6 map.get(key):null mapSize:4
     * key:7 map.get(key):null mapSize:3
     * key:8 map.get(key):null mapSize:2
     * key:9 map.get(key):null mapSize:1
     * key:10 map.get(key):null mapSize:0
     */
    @Test
    public void testKeySetForEach_remove() {

        Map<String, String> map = Stream.iterate(1L, i -> i + 1)
                .limit(10)
                .map(String::valueOf)
                .collect(Collectors.toConcurrentMap(i -> i, i -> i.concat("v")));

        map.keySet().forEach(key -> {
            map.remove(key);
            System.out.println(String.format("key:%s map.get(key):%s mapSize:%d", key, map.get(key), map.size()));
        });
    }

    /**
     * result run 20 times stop
     * key:1 map.get(key):1v mapSize:11
     * key:2 map.get(key):2v mapSize:12
     * key:3 map.get(key):3v mapSize:13
     * key:4 map.get(key):4v mapSize:14
     * key:4add map.get(key):4 mapSize:15
     * key:5 map.get(key):5v mapSize:16
     * key:5add map.get(key):5 mapSize:17
     * key:5addadd map.get(key):5add mapSize:18
     * key:6 map.get(key):6v mapSize:19
     * key:3add map.get(key):3 mapSize:20
     * key:1add map.get(key):1 mapSize:21
     * key:7 map.get(key):7v mapSize:22
     * key:2add map.get(key):2 mapSize:23
     * key:8 map.get(key):8v mapSize:24
     * key:9 map.get(key):9v mapSize:25
     * key:4addadd map.get(key):4add mapSize:26
     * key:4addaddadd map.get(key):4addadd mapSize:27
     * key:4addaddaddadd map.get(key):4addaddadd mapSize:28
     * key:10 map.get(key):10v mapSize:29
     * key:2addadd map.get(key):2add mapSize:30
     */
    @Test
    public void testKeySetForEach_add() {

        Map<String, String> map = Stream.iterate(1L, i -> i + 1)
                .limit(10)
                .map(String::valueOf)
                .collect(Collectors.toConcurrentMap(i -> i, i -> i.concat("v")));

        map.keySet().forEach(key -> {
            map.put(key.concat("add"), key);
            System.out.println(String.format("key:%s map.get(key):%s mapSize:%d", key, map.get(key), map.size()));
        });
    }

    /**
     * 跟KeySet相同，但map foreach有可能ConcurrentModificationException
     */
    @Test
    public void testMapForEach_remove() {

        Map<String, String> map = Stream.iterate(1L, i -> i + 1)
                .limit(10)
                .map(String::valueOf)
                .collect(Collectors.toConcurrentMap(i -> i, i -> i.concat("v")));

        map.forEach((key, value) -> {
            map.remove(key);
            System.out.println(String.format("key:%s value:%s map.get(key):%s mapSize:%d", key, value, map.get(key), map.size()));
        });
    }

    @Test
    public void testMapForEach_add() {

        Map<String, String> map = Stream.iterate(1L, i -> i + 1)
                .limit(10)
                .map(String::valueOf)
                .collect(Collectors.toConcurrentMap(i -> i, i -> i.concat("v")));

        map.forEach((key, value) -> {
            map.put(key.concat("add"), key);
            System.out.println(String.format("key:%s value:%s map.get(key):%s mapSize:%d", key, value, map.get(key), map.size()));
        });
    }
}
