package idv.code.stream;

import jakarta.xml.bind.DatatypeConverter;

public class InstanceCallbackDigestUserInterface {
    private String fileName;
    private byte[] digest;

    public InstanceCallbackDigestUserInterface(String fileName) {
        this.fileName = fileName;
    }

    public void calculateDigest() {
        InstanceCallbackDigest cb = new InstanceCallbackDigest(fileName, this);
        Thread thread = new Thread(cb);
        thread.start();
    }

    void receiveDigest(byte[] digest) {
        this.digest = digest;
        System.out.println(this);
    }

    @Override
    public String toString() {
        String result = fileName + ": ";
        if (digest != null) {
            result += DatatypeConverter.printHexBinary(digest);
        } else {
            result += "digest not available.";
        }
        return result;
    }

    public static void main(String[] args) {
        InstanceCallbackDigestUserInterface d =
                new InstanceCallbackDigestUserInterface("out/big5file");
        d.calculateDigest();
    }
}
