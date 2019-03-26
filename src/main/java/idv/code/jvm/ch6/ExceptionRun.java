package idv.code.jvm.ch6;

public class ExceptionRun {

    public static void main(String[] args) {
        try{
            exception();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void exception() throws Exception {
        throw new Exception("ex");
    }
}
