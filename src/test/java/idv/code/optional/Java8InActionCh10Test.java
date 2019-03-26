package idv.code.optional;

import idv.code.utils.OptionalUtility;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Java8InActionCh10Test {

    class Person {
        private Optional<Car> car = Optional.empty();

        private Car oldCar;

        public Optional<Car> getCar() {
            return car;
        }

        public Car getOldCar() {
            return oldCar;
        }

        //假如有序列化需求
        public Optional<Car> getCarOptional() {
            return Optional.ofNullable(oldCar);
        }
    }

    class Car {
        private Optional<Insurance> insurance = Optional.empty();

        private Insurance oldInsurance;

        public Optional<Insurance> getInsurance() {
            return insurance;
        }

        public Insurance getOldInsurance() {
            return oldInsurance;
        }
    }

    class Insurance {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    @Test
    public void testCh10003() {

        //取得連續欄位值 deep doubts
        Person person = new Person();
        System.out.println(getOldCarInsuranceName(person));
        System.out.println(getCarInsuranceName(Optional.ofNullable(person)));

        //filter
        Insurance insurance = new Insurance();

        //Old
        if (insurance != null && "Test".equals(insurance.getName())) {
            System.out.println(insurance.getName() + "ok");
        }

        //Optional
        Optional<Insurance> optInsurance = Optional.ofNullable(insurance);
        optInsurance.filter(insurance1 -> "Test".equals(insurance1.getName())).ifPresent(x -> System.out.println(x.getName() + "ok"));
    }

    private String getOldCarInsuranceName(Person person) {
        if (person != null) {
            Car car = person.getOldCar();
            if (car != null) {
                Insurance insurance = car.getOldInsurance();
                if (insurance != null) {
                    return insurance.getName();
                }
            }
        }
        return "Unknown";
    }

    private String getCarInsuranceName(Optional<Person> person) {
        return person.flatMap(Person::getCar).flatMap(Car::getInsurance).map(Insurance::getName).orElse("Unknown");
    }

    class Properties {
        private Map<String, String> propertys = new HashMap<>();

        public void setProperty(String key, String value) {
            propertys.put(key, value);
        }

        public String getProperty(String key) {
            return propertys.get(key);
        }
    }

    @Test
    public void testCh10004() {

        Properties props = new Properties();
        props.setProperty("a", "5");
        props.setProperty("b", "true");
        props.setProperty("c", "-3");

        Assert.assertEquals(5, readDuration_Old(props, "a"));
        Assert.assertEquals(0, readDuration_Old(props, "b"));
        Assert.assertEquals(0, readDuration_Old(props, "c"));
        Assert.assertEquals(0, readDuration_Old(props, "d"));

        Assert.assertEquals(5, readDuration_New(props, "a"));
        Assert.assertEquals(0, readDuration_New(props, "b"));
        Assert.assertEquals(0, readDuration_New(props, "c"));
        Assert.assertEquals(0, readDuration_New(props, "d"));

    }

    private int readDuration_Old(Properties props, String name) {
        String value = props.getProperty(name);
        if (value != null) {
            try {
                int i = Integer.parseInt(value);
                if (i > 0)
                    return i;
            } catch (NumberFormatException nfe) {
            }
        }
        return 0;
    }

    private int readDuration_New(Properties props, String name) {
        return Optional.ofNullable(props.getProperty(name))
                .flatMap(OptionalUtility::stringToInt)
                .filter(i -> i > 0)
                .orElse(0);
    }
}
