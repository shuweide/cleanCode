package idv.code.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class HelloKryo {
    static public void main (String[] args) throws Exception {
        Kryo kryo = new Kryo();
        kryo.register(SomeClass.class);
        kryo.register(int[].class);

        SomeClass object = new SomeClass();
        object.value = "Hello Kryo!";
        object.value2 = new int[]{3,4,2,4,5};

        Output output = new Output(new FileOutputStream("file.bin"));
        kryo.writeObject(output, object);
        output.close();

        Input input = new Input(new FileInputStream("file.bin"));
        SomeClass object2 = kryo.readObject(input, SomeClass.class);
        input.close();

        System.out.println(object2.value);
    }
    static public class SomeClass {
        String value;
        int[] value2;
    }
}
