package idv.code;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonObjectPlay {
    
    public JsonObject getAndMakeRequestMsg(String name, String address){
        JsonObject requestMsg;
        
        Customer customer = new Customer();
        customer.setAddress(address);
        customer.setName(name);
        Gson gson = new Gson();
        String json = gson.toJson(customer);  
        
        JsonParser jsonParser = new JsonParser();
        requestMsg = jsonParser.parse(json).getAsJsonObject();
        return requestMsg;
    }
    
}
