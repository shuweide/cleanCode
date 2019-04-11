package idv.code.gson;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class anonymousClassToJsonTest {
    public static void main(String[] args) {
        List<Foo> fooList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Foo foo = new Foo() {{
                setF1("Test");
            }};
            fooList.add(foo);
        }
        System.out.println(new Gson().toJson(fooList));
        System.out.println(fooList.stream().map(Foo::getF1).collect(Collectors.joining(",")));
    }
}
