package org.codeprimate.net.sample;

import java.io.DataInput;
import java.io.DataOutput;
import java.net.Socket;
import java.util.Scanner;

import org.codeprimate.net.AbstractClientServerSupport;
import org.codeprimate.net.SocketUtils;

/**
 * @author John J. Blum
 * @see org.codeprimate.net.AbstractClientServerSupport
 * @since 1.0.0
 */
public class EchoClient extends AbstractClientServerSupport {

  public static void main(final String... args) throws Exception {
    parseCommandLineArguments(args);

    assert (getServerAddress() != null && getServerAddress().isReachable(5000)) :
      formatMessage("The server address ({0}) cannot be null and must be reachable!", getServerAddress());

    Socket clientSocket = openSocket(getServerAddress(), getPort(), true);

    assert (clientSocket != null && clientSocket.isConnected()) :
      formatMessage("Failed to connect to Echo Server @ ({0})) on port ({1,number,integer})!",
        getServerAddress(), getPort());

    System.out.printf("Connected to Echo Server @ (%1$s) on port (%2$d)...%n", getServerAddress().toString(), getPort());

    DataInput in = getInputStream(clientSocket);
    DataOutput out = getOutputStream(clientSocket);

    Scanner userIn = new Scanner(System.in);

    String line;

    try {
      while (!"exit".equalsIgnoreCase(line = userIn.nextLine().trim())) {
        System.out.printf("Client says... \"%1$s\"%n", line);
        out.writeBytes(line + "\n");
        System.out.printf("Server says... \"%1$s\"%n", in.readLine());
      }
    }
    finally {
      SocketUtils.close(clientSocket);
    }
  }

}
