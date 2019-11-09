package idv.code.udp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

public class UDPTimeClient {
    public static void main(String[] args) {
        InetAddress host;
        int port = 0;
        try {
            host = InetAddress.getByName(args[0]);
            port = Integer.parseInt(args[1]);
        } catch (UnknownHostException | RuntimeException e) {
            System.out.println("Usage: java UDPTimeClient host port");
            return;
        }

        UDPPoke poker = new UDPPoke(host, port);
        byte[] response = poker.poke();
        if (response == null) {
            System.out.println("No response within allotted time");
            return;
        } else if (response.length != 4) {
            System.out.println("Unrecognized response format");
            return;
        }

        //time 通訊協定的年代為1900
        //Java 的 Date 類別為1970

        long differenceBetweenEpochs = 2208988800L; //約為70年的秒數

        long secondsSince1900 = 0;
        for (int i = 0; i < 4; i++) {
            secondsSince1900 = (secondsSince1900 << 8 | (response[i] & 0x000000FF));
        }

        long secondsSince1970 = secondsSince1900 - differenceBetweenEpochs;
        long msSince1970 = secondsSince1970 * 1000;

        System.out.println(new Date(msSince1970));
        System.out.println(LocalDateTime.ofEpochSecond(secondsSince1970, 0, ZoneOffset.of("+08:00")));
    }
}
