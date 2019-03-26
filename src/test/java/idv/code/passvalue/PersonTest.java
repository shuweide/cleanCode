package idv.code.passvalue;

import org.junit.Test;

public class PersonTest {
    @Test
    public void testHouse(){
        Person person = new Person();
        
        person.setName("shuwei");
        person.setCash(10000L);
        
        person.update();
    }
}
