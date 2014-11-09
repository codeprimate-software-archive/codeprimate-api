package org.codeprimate.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The SocketUtils class is an abstract utility class for performing operations on Sockets and ServerSockets.
 * 
 * @author John J. Blum
 * @see java.net.ServerSocket
 * @see java.net.Socket
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class SocketUtils {

  /**
   * Closes the specified Socket silently ignoring any IOException, guarding against null Socket references.
   * 
   * @param socket the Socket to close.
   * @return boolean value indicating whether the Socket was successfully closed.  If the Socket object reference
   * is null, then this method will return false.
   * @see java.net.Socket#close()
   */
  public static boolean close(final Socket socket) {
    if (socket != null) {
      try {
        socket.close();
        return true;
      }
      catch (IOException ignore) {
      }
    }

    return false;
  }

  /**
   * Closes the specified ServerSocket silently ignoring any IOException, guarding against null ServerSocket references.
   * 
   * @param serverSocket the ServerSocket to close.
   * @return boolean value indicating whether the ServerSocket was successfully closed.  If the ServerSocket Object
   * reference is null, then this method will return false.
   * @see java.net.ServerSocket#close()
   */
  public static boolean close(final ServerSocket serverSocket) {
    if (serverSocket != null) {
      try {
        serverSocket.close();
        return true;
      }
      catch (IOException ignore) {
      }
    }

    return false;
  }

}
