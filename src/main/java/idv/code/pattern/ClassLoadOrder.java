package idv.code.pattern;

public class ClassLoadOrder {
    public static class Inner{
        static {
            System.out.println("Inner Statuc Field");
        }
        public final static ClassLoadOrder testInstance = new ClassLoadOrder(3);
    }

    public static ClassLoadOrder getInstance(){
        return Inner.testInstance;
    }

    public ClassLoadOrder(int i) {
        System.out.println("Test " + i +" Construct! ");
    }

    static {
        System.out.println("Static Field");
    }

    public static ClassLoadOrder testOut = new ClassLoadOrder(1);

    public static void main(String args[]){
        ClassLoadOrder t = new ClassLoadOrder(2);
        ClassLoadOrder.getInstance();
    }
}
