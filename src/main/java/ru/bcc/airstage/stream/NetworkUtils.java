package ru.bcc.airstage.stream;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Date: 08.05.13
 * Time: 18:51
 *
 * @author Artem Prigoda
 */
public final class NetworkUtils {

    /**
     * Get ip address in the local network
     *
     * @return ip address (not 127.0.0.1)
     */
    public static String ipAddress() {
        try {
            String host = "";
            Enumeration<NetworkInterface> eths = NetworkInterface.getNetworkInterfaces();
            Enumeration<InetAddress> addresses = null;
            while (eths.hasMoreElements()) {
                NetworkInterface eth = eths.nextElement();
                addresses = eth.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (address instanceof Inet6Address || address.getHostAddress().equals("127.0.0.1")) {
                        continue;
                    }
                    host = address.getHostAddress();
                    break;
                }
            }
            if (host == null) {
                throw new IllegalStateException("Unable find locate correct ip address in " +
                        Collections.list(addresses));
            }
            return host;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
