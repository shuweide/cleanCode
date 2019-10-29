package idv.code.jvm;

public class Sub extends Super {

    Sub(){
    }

    public static void main(String[] args) {
        new Sub().m();
    }

    public void m(){
        System.out.println("Sub!!!");
    }
}
