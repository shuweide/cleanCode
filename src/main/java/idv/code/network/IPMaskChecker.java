package idv.code.network;

import org.springframework.security.web.util.matcher.IpAddressMatcher;

import java.net.UnknownHostException;
import java.time.Duration;
import java.time.Instant;

public class IPMaskChecker {
    public static void main(String[] args) {

        Instant start = Instant.now();

        System.out.println(matches("199.83.135.255", "199.83.128.0/21"));
        System.out.println(matches("199.143.63.255", "199.143.32.0/19"));
        System.out.println(matches("149.126.79.255", "149.126.72.0/21"));
        System.out.println(matches("103.28.251.255", "103.28.248.0/22"));
        System.out.println(matches("45.64.67.255", "45.64.64.0/22"));

        Duration d = Duration.between(start, Instant.now());
        System.out.println(d.getNano() / 1000000);

    }

    private static boolean matches(String ip, String subnet) {
        IpAddressMatcher ipAddressMatcher = new IpAddressMatcher(subnet);
        return ipAddressMatcher.matches(ip);
    }
}
