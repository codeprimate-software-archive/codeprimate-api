package org.codeprimate.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.codeprimate.util.CollectionUtils;

/**
 * The NetworkUtils class is an abstract utility class for working with IP addresses and Network Interfaces, etc.
 *
 * @author John J. Blum
 * @see java.net.InetAddress
 * @see java.net.NetworkInterface
 * @since 1.0.0
 */
public abstract class NetworkUtils {

  public static Iterable<NetworkInterface> getActiveNetworkInterfaces() throws SocketException {
    List<NetworkInterface> activeNetworkInterfaces = new ArrayList<NetworkInterface>();

    for (NetworkInterface networkInterface : CollectionUtils.iterable(NetworkInterface.getNetworkInterfaces())) {
      if (networkInterface.isUp()) {
        activeNetworkInterfaces.add(networkInterface);
      }
    }

    return Collections.unmodifiableList(activeNetworkInterfaces);
  }

  public static Iterable<InetAddress> getInetAddresses(final Iterable<NetworkInterface> networkInterfaces) {
    List<InetAddress> ipAddresses = new ArrayList<InetAddress>();

    for (NetworkInterface networkInterface : networkInterfaces) {
      for (InetAddress inetAddress : CollectionUtils.iterable(networkInterface.getInetAddresses())) {
        ipAddresses.add(inetAddress);
      }
    }

    return Collections.unmodifiableList(ipAddresses);
  }

  public static void main(final String[] args) throws Exception {
    for (InetAddress inetAddress : getInetAddresses(getActiveNetworkInterfaces())) {
      System.out.printf("%1$s%n", inetAddress);
    }
  }

}
