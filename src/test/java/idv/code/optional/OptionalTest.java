package idv.code.optional;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class OptionalTest {
    @Test
    public void testNotNull() {
        String name = "shuwei";
        Optional<String> optName = Optional.of(name);
        assertEquals("shuwei", optName.get());
    }

    @Test(expected = NullPointerException.class)
    public void testNull() {
        String name = null;
        Optional<String> optName = Optional.of(name);
    }

    @Test(expected = NoSuchElementException.class)
    public void testNullable() {
        String name = null;
        Optional<String> optName = Optional.ofNullable(name);
        assertNull(optName.get());
    }

    @Test
    public void testIsPresent() {
        String name = null;
        Optional<String> optName = Optional.ofNullable(name);
        assertFalse(optName.isPresent());
    }

    @Test(expected = NoSuchElementException.class)
    public void testEmpty() {
        Optional<String> optName = Optional.empty();
        optName.get();
    }

    @Test
    public void testOrElse() {
        String name = null;
        Optional<String> optName = Optional.ofNullable(name);
        assertEquals("default", optName.orElse("default"));
    }

    @Test
    public void testOrElseGet() {
        String name = null;
        Optional<String> optName = Optional.ofNullable(name);
        assertEquals("default", optName.orElseGet(() -> "default"));
    }

    @Test(expected = Exception.class)
    public void testOrElseThrow() throws Exception {
        String name = null;
        Optional<String> optName = Optional.ofNullable(name);
        optName.orElseThrow(() -> new Exception("Error"));
    }
}
