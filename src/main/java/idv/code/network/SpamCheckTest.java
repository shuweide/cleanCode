package idv.code.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SpamCheckTest {
    private static final String BLACKHOLE = "sbl.spamhaus.org";

    public static void main(String[] args) {
        for (String arg : args) {
            if (isSpammer(arg)) {
                System.out.println(arg + "is a known spammer.");
            } else {
                System.out.println(arg + " appears legitimate.");
            }
        }
    }

    private static boolean isSpammer(String arg) {
        try {
            InetAddress address = InetAddress.getByName(arg);
            byte[] quad = address.getAddress();
            String query = BLACKHOLE;
            for (byte octec : quad) {
                int unsignedByte = octec < 0 ? octec + 256 : octec;
                query = unsignedByte + "." + query;
            }
            InetAddress.getByName(query);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }

}
