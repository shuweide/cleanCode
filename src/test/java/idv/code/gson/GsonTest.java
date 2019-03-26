package idv.code.gson;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * just try https://github.com/google/gson/blob/master/UserGuide.md
 */
public class GsonTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testPrimitive() {
        // Serialization
        Gson gson = new Gson();
        String oneJson = gson.toJson(1);
        String oneArrayJson = gson.toJson(new int[]{1, 1, 1, 1, 1});
        logger.info(oneJson);
        logger.info(oneArrayJson);

        //Deserialization
        int one = gson.fromJson(oneJson, int.class);
        int[] oneArray = gson.fromJson(oneArrayJson, int[].class);
    }

    //Fields corresponding to the outer classes in inner classes, anonymous classes, and local classes are ignored and not included in serialization or deserialization
    private class Employee {
        private long id = 1L;
        private String name = "shuwei";
        private String title = "develop";
        private String address = null; //null field
        private transient String password = "password"; //transient
        private volatile long seq = 30L;

        public String getPassword() {
            return password;
        }

    }

    @Test
    public void testObject() {

        //Serialization
        Employee employee = new Employee();
        Gson gson = new Gson();
        String employeeJson = gson.toJson(employee);
        logger.info(employeeJson);

        //Deserialization
        Employee employee2 = gson.fromJson(employeeJson, Employee.class);
    }

    private class A {
        private String a = "A";
        private B b = new B();
    }

    class B {
        private String b = "B";
    }

    private class InstanceCreatorForB implements InstanceCreator<B> {

        @Override
        public B createInstance(Type type) {
            return new B();
        }
    }

    @Test
    public void testNestedClasses() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(B.class, new InstanceCreatorForB());
        Gson gson = gsonBuilder.create();

        A a = new A();
        String aJson = gson.toJson(a);
        logger.info(aJson);

        A a2 = gson.fromJson(aJson, A.class);
        logger.info(a2.b.b);
    }

    @Test
    public void testCollections() {
        Gson gson = new Gson();
        Collection<Integer> ints = Arrays.asList(1, 2, 3, 4, 5);

        //Serialization
        String intsJson = gson.toJson(ints);
        logger.info(intsJson);

        //Deserialization
        Type collectionType = new TypeToken<Collection<Integer>>() {
        }.getType(); //the Collection must be of a specific, generic type
        Collection<Integer> ints2 = gson.fromJson(intsJson, collectionType);
        ints2.stream().forEach(i -> logger.info(String.valueOf(i)));

    }

    private class Foo<T> {
        private T value;

        Foo(T value) {
            this.value = value;
        }
    }

    //if the object is of a generic type, then the Generic type information is lost because of Java DishType Erasure.
    @Test
    public void testGenericType() {
        Gson gson = new Gson();
        Foo<String> foo = new Foo<>("Test");
        String fooJson = gson.toJson(foo);
        logger.info(fooJson);

        Type fooType = new TypeToken<Foo<String>>() {
        }.getType();
        Foo<String> foo2 = gson.fromJson(fooJson, fooType);
        logger.info(foo2.value);
    }

    private class Event {
        private String name;
        private String source;

        private Event(String name, String source) {
            this.name = name;
            this.source = source;
        }

        @Override
        public String toString() {
            return String.format("(name=%s, source=%s)", name, source);
        }
    }

    @Test
    public void testCollectionWithObject() {
        Gson gson = new Gson();
        Collection collection = new ArrayList();
        collection.add("hello");
        collection.add(5);
        collection.add(new Event("GREETINGS", "guest"));

        String collectionJson = gson.toJson(collection);
        logger.info("Using Gson.toJson() on a raw collection: " + collectionJson);

        JsonParser parser = new JsonParser();
        JsonArray array = parser.parse(collectionJson).getAsJsonArray();
        String message = gson.fromJson(array.get(0), String.class);
        int number = gson.fromJson(array.get(1), int.class);
        Event event = gson.fromJson(array.get(2), Event.class);
        System.out.printf("Using Gson.fromJson() to get: %s, %d, %s", message, number, event);
    }

    @Test
    public void testNullObject() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Event event = new Event(null, null);
        String eventJson = gson.toJson(event);
        logger.info(eventJson);
    }

    @Test
    public void testExclusion() {
        //Serialization
        Employee employee = new Employee();
        Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.VOLATILE, Modifier.TRANSIENT, Modifier.STATIC).create();
        String employeeJson = gson.toJson(employee);
        logger.info(employeeJson);

        //Deserialization
        Employee employee2 = gson.fromJson(employeeJson, Employee.class);
    }

    class ExposeClass {
        @Expose
        private long seq = 1000L;
        @Expose
        private String name = "shuwei";
        @Detail
        private String addr = "addr";
    }

    @Test
    public void testExpose() {
        ExposeClass exposeClass = new ExposeClass();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String exposeJson = gson.toJson(exposeClass);
        logger.info(exposeJson);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface Detail {
    }

    class MyExclusionStrategy implements ExclusionStrategy {
        private final Class<?> typeToSkip;

        private MyExclusionStrategy(Class<?> typeToSkip) {
            this.typeToSkip = typeToSkip;
        }

        public boolean shouldSkipClass(Class<?> clazz) {
            return (clazz == typeToSkip);
        }

        public boolean shouldSkipField(FieldAttributes f) {
            return f.getAnnotation(Detail.class) != null;
        }
    }

    @Test
    public void testExclusionStrategy() {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new MyExclusionStrategy(long.class))
                .serializeNulls().create();

        ExposeClass exposeClass = new ExposeClass();
        String exposeClassJson = gson.toJson(exposeClass);
        logger.info(exposeClassJson);
    }

    class SomeObject {
        @SerializedName("customNaming")
        private String someField;
        private long someOtherField;

        public SomeObject(String someField, long someOtherField) {
            this.someField = someField;
            this.someOtherField = someOtherField;
        }
    }

    @Test
    public void testNamingPolicy() {
        SomeObject someObject = new SomeObject("first", 1000L);
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        String someObjectJson = gson.toJson(someObject);
        logger.info(someObjectJson);

        SomeObject someObject2 = gson.fromJson(someObjectJson, SomeObject.class);
        logger.info(someObject2.someField);
        logger.info(String.valueOf(someObject2.someOtherField));
    }

    interface Animal {
    }

    class Pig implements Animal {
        private double weight = 99.99;
    }

    @Test
    public void testInterfaceToJson() {
        Animal animal = new Pig();
        logger.info(new Gson().toJson(animal));
    }

}
