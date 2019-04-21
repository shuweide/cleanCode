package idv.code.network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class InetAddressTest {
    public static void main(String[] args) {
        testInetAddress("127.0.0.1");
//        testInetAddress("192.168.254.32");
//        testInetAddress("www.oreilly.com");
//        testInetAddress("224.0.2.1");
        testInetAddress("::");
//        testInetAddress("::1");
    }

    private static void testInetAddress(String hostName) {
        try {
            System.out.println(hostName);
            InetAddress address = InetAddress.getByName(hostName);
            byte[] bArr = address.getAddress(); //unsignedByte
            int[] iArr = new int[bArr.length];
            for (int i = 0; i < bArr.length; i++) {
                iArr[i] = bArr[i] < 0 ? bArr[i] + 256 : bArr[i];
            }
            System.out.println(String.format("hostname: %s, canonical: %s, address: %s, host address: %s, version: %d",
                    address.getHostName(), address.getCanonicalHostName(), Arrays.toString(iArr),
                    address.getHostAddress(), getVersion(address)));

            System.out.println(String.format("an:%b lb:%b ll:%b sl:%b m:%b mcg:%b mcnl:%b mcll:%b mcsl:%b mcol:%b",
                    address.isAnyLocalAddress(), address.isLoopbackAddress(),
                    address.isLinkLocalAddress(), address.isSiteLocalAddress(), address.isMulticastAddress(),
                    address.isMCGlobal(), address.isMCNodeLocal(), address.isMCLinkLocal(), address.isMCSiteLocal(),
                    address.isMCOrgLocal()));
            System.out.println();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static int getVersion(InetAddress inetAddress) {
        byte[] address = inetAddress.getAddress();
        if (address.length == 4) return 4;
        else if (address.length == 16) return 6;
        else return -1;
    }
}
