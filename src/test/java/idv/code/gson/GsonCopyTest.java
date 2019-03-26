package idv.code.gson;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

import com.google.gson.*;

public class GsonCopyTest {

    private final long TEST_TIMES = 1_000_000L;
    private Gson gson = new Gson();
    
    @Test
    public void testStringCopy() {

        LocalDateTime before = LocalDateTime.now();

        for (int i = 0; i < TEST_TIMES; i++) {
            TestObject testObject = new TestObject();
            JsonObject jsonObject = gson.fromJson(gson.toJson(testObject), JsonObject.class);
        }

        LocalDateTime after = LocalDateTime.now();

        System.out.println("testStringCopy:" + ChronoUnit.MILLIS.between(before, after));

    }

    @Test
    public void testObjectCopy() {

        LocalDateTime before = LocalDateTime.now();

        for (int i = 0; i < TEST_TIMES; i++) {
            TestObject testObject = new TestObject();
            JsonObject jsonObject = gson.toJsonTree(testObject).getAsJsonObject();
        }

        LocalDateTime after = LocalDateTime.now();

        System.out.println("testObjectCopy:" + ChronoUnit.MILLIS.between(before, after));
        
    }

    private class TestObject {
        private int field001;
        private long field002;
        private String field003;
        private String field004;
        private String field005;
        private String field006;
        private String field007;
        private String field008;
        private String field009;
        private String field010;
        private String field011;
        private LocalDateTime time;

        public TestObject() {
            field001 = 1000;
            field002 = 1000L;
            field003 = "field003";
            field004 = "field004";
            field005 = "field005";
            field006 = "field006";
            field007 = "field007";
            field008 = "field008";
            field009 = "field009";
            field010 = "field010";
            field011 = "field011";
            time = LocalDateTime.now();
        }
    }
}
