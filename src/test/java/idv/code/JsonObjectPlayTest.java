package idv.code;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonObjectPlayTest {

    @Test
    public void testGetJsonObject() {
        String expectName = "shuwei";
        String expectAddress = "home";

        JsonObjectPlay jsonObjectPlay = new JsonObjectPlay();
        JsonObject request = jsonObjectPlay.getAndMakeRequestMsg(expectName, expectAddress);
        System.out.println(request);
        Customer customer = new Gson().fromJson(request, Customer.class);
        System.out.println(customer);
        Assert.assertEquals(expectName, customer.getName());
        Assert.assertEquals(expectAddress, customer.getAddress());
    }
}
