/*
 * Copyright 2014-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codeprimate.net.sample;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.net.Socket;

import org.codeprimate.net.AbstractClientServerSupport;
import org.codeprimate.net.SocketUtils;

/**
 * The EchoServer, along with the EchoClient, are used to test communications between two disparate nodes on network
 * using TCP/IP.
 * 
 * @author John J. Blum
 * @see org.codeprimate.net.AbstractClientServerSupport
 * @since 1.0.0
 */
public class EchoServer extends AbstractClientServerSupport {

  public static void main(final String... args) throws Exception {
    parseCommandLineArguments(args);

    startServer("Echo Server", new ClientRequestHandler() {
      public Runnable service(final Socket clientSocket) {
        return createEchoClientRunnable(clientSocket);
      }
    });

    waitForUserInput("exit", "Please enter 'exit' to stop the Echo Sever.");
    System.out.println("Exiting...");
    setRunning(false);
  }

  private static Runnable createEchoClientRunnable(final Socket clientSocket) {
    assert !(clientSocket == null || clientSocket.isClosed()) : "The Echo Client connection cannot be null or closed!";

    return new Runnable() {
      public void run() {
        System.out.printf("Receiving echo requests from client (%1$s) in Thread (%2$s)...%n",
          clientSocket.getInetAddress().toString(), Thread.currentThread().getName());

        try {
          DataInput in = getInputStream(clientSocket);
          DataOutput out = getOutputStream(clientSocket);

          boolean running = true;

          while (running) {
          //while (!clientSocket.isClosed() && clientSocket.isConnected()) {
            try {
              String echoMessage = in.readLine();
              System.out.printf("%1$s: \"%2$s\"%n", clientSocket.getInetAddress().toString(), echoMessage);
              out.writeBytes(echoMessage + "\n");
            }
            catch (IOException e) {
              running = false;
              if (isDebug()) {
                System.err.printf("Failed to read message from echo client (%1$s): %2$s%n",
                  clientSocket.getInetAddress().toString(), e.getMessage());
              }
            }
          }
        }
        catch (IOException e) {
          e.printStackTrace(System.err);
        }
        finally {
          SocketUtils.close(clientSocket);
          System.out.printf("Echo client (%1$s) connection closed. Thread (%2$s) exiting.%n", clientSocket.getInetAddress().toString(),
            Thread.currentThread().getName());
        }
      }
    };
  }

}
