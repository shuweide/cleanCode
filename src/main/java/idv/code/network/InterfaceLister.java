package idv.code.network;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.stream.Collectors;

public class InterfaceLister {
    public static void main(String[] args) {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                System.out.println(ni);
                System.out.println(ni.getInterfaceAddresses().stream().map(ifa -> ifa.getAddress().getHostAddress())
                        .collect(Collectors.joining(";")));
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }
}
