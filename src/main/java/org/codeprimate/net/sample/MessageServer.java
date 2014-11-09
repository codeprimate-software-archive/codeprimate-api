package org.codeprimate.net.sample;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.Socket;

import org.codeprimate.net.AbstractClientServerSupport;
import org.codeprimate.net.SocketUtils;

public class MessageServer extends AbstractClientServerSupport {

  public static void main(final String... args) throws Exception {
    parseCommandLineArguments(args);

    startServer("Message Server", new ClientRequestHandler() {
      public Runnable service(final Socket clientSocket) {
        return createMessageClientRunnable(clientSocket);
      }
    });

    waitForUserInput("exit", "Please enter 'exit' to stop the Message Server.");
    System.out.println("Exiting...");
    setRunning(false);
  }

  private static Runnable createMessageClientRunnable(final Socket clientSocket) {
    return new Runnable() {
      public void run() {
        try {
          DataInput in = getInputStream(clientSocket);
          DataOutput out = getOutputStream(clientSocket);

          boolean reading = true;

          while (reading) {
            try {
              final int payloadSize = in.readInt();

              if (isDebug()) {
                System.out.printf("Reading (%1$d) bytes from client (%2$s)...%n", payloadSize,
                  clientSocket.getInetAddress().toString());
              }

              in.readFully(new byte[payloadSize]);

              if (isDebug()) {
                System.out.println("Sending ack...");
              }

              out.writeByte(1);
            }
            catch (IOException ignore) {
              reading = false;
            }
          }
        }
        catch (IOException e) {
          throw new RuntimeException(e);
        }
        finally {
          SocketUtils.close(clientSocket);
        }
      }
    };
  }

}
