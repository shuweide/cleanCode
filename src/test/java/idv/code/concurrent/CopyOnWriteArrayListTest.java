package idv.code.concurrent;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Test;



public class CopyOnWriteArrayListTest {
    @Test
    public void testContains(){
        List<Integer> list = new CopyOnWriteArrayList();
        list.add(1);
        assertEquals(true, list.contains(1));
    }
}
