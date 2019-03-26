package idv.code.loader;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ClassLoaderTest {
    @Test
    public void classLoaderTest() throws Exception {
        List list = (List) new MyClassLoader().loadClass("idv.code.loader.MyList").newInstance();
        System.out.println("list.size = " + list.size() + "\nClassLoader:" + list.getClass().getClassLoader());
    }

    class MyClassLoader extends ClassLoader {
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            Path path = Paths.get("D:\\workspace\\clean-code\\out\\test\\classes\\" + name.replaceAll("\\.", "\\\\") + ".class");
            byte[] classData = null;
            try {
                classData = Files.readAllBytes(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return defineClass(name, classData, 0, classData.length);
        }
    }
}
