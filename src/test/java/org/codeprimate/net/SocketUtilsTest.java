package org.codeprimate.net;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The SocketUtilsTest class is a test suite of test cases testing the contract and functionality of the SocketUtils
 * utility class.
 *
 * @author John J. Blum
 * @see org.codeprimate.net.SocketUtils
 * @see org.jmock.Mockery
 * @see org.junit.Test
 * @since 1.0.0
 */
public class SocketUtilsTest {

  private Mockery mockContext;

  @Before
  public void setup() {
    mockContext = new Mockery() {{
      setImposteriser(ClassImposteriser.INSTANCE);
    }};
  }

  @After
  public void tearDown() {
    mockContext.assertIsSatisfied();
  }

  @Test
  public void testCloseSocket() throws IOException {
    final Socket mockSocket = mockContext.mock(Socket.class, "closeSocketTest");

    mockContext.checking(new Expectations() {{
      oneOf(mockSocket).close();
    }});

    assertTrue(SocketUtils.close(mockSocket));
  }

  @Test
  public void testCloseSocketThrowsIOException() throws IOException {
    final Socket mockSocket = mockContext.mock(Socket.class, "closeSocketThrowsIOExceptionTest");

    mockContext.checking(new Expectations() {{
      oneOf(mockSocket).close();
      will(throwException(new IOException("test")));
    }});

    try {
      assertFalse(SocketUtils.close(mockSocket));
    }
    catch (Throwable t) {
      fail("Calling close on a Socket using SocketUtils threw an unexpected Throwable (" + t + ")!");
    }
  }

  @Test
  public void testCloseSocketWithNull() {
    assertFalse(SocketUtils.close((Socket) null));
  }

  @Test
  public void testCloseServerSocket() throws IOException {
    final ServerSocket mockServerSocket = mockContext.mock(ServerSocket.class, "closeServerSocketTest");

    mockContext.checking(new Expectations() {{
      oneOf(mockServerSocket).close();
    }});

    assertTrue(SocketUtils.close(mockServerSocket));
  }

  @Test
  public void testCloseServerSocketThrowsIOException() throws IOException {
    final ServerSocket mockServerSocket = mockContext.mock(ServerSocket.class, "closeServerSocketThrowsIOExceptionTest");

    mockContext.checking(new Expectations() {{
      oneOf(mockServerSocket).close();
      will(throwException(new IOException("test")));
    }});

    try {
      assertFalse(SocketUtils.close(mockServerSocket));
    }
    catch (Throwable t) {
      fail("Calling close on a ServerSocket using SocketUtils threw an unexpected Throwable (" + t + ")!");
    }
  }

  @Test
  public void testCloseServerSocketWithNull() {
    assertFalse(SocketUtils.close((ServerSocket) null));
  }

}
