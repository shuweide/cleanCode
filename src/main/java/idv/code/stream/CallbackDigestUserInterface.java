package idv.code.stream;

import javax.xml.bind.DatatypeConverter;

public class CallbackDigestUserInterface {

    public static void receiveDigest(byte[] digest, String name) {
        StringBuilder result = new StringBuilder(name);
        result.append(": ");
        result.append(DatatypeConverter.printHexBinary(digest));
        System.out.println(result);
    }

    public static void main(String[] args) {
        CallbackDigest cb = new CallbackDigest("out/big5file");
        Thread t = new Thread(cb);
        t.start();
    }
}
