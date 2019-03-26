package idv.code.list;

import static org.junit.Assert.assertEquals;

import java.util.*;

import org.junit.Test;


public class ArrayListTest {
    @Test
    public void testArrayListAddNull() {
        List<Object> list = new ArrayList();
        list.add(null);
        assertEquals(1, list.size());
    }
}
