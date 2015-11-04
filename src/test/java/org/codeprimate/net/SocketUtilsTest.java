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

package org.codeprimate.net;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
@RunWith(MockitoJUnitRunner.class)
public class SocketUtilsTest {

  @Mock
  private Socket mockSocket;

  @Mock
  private ServerSocket mockServerSocket;

  @Test
  public void closeSocketIsSuccessful() throws IOException {
    assertThat(SocketUtils.close(mockSocket), is(true));
    verify(mockSocket, times(1)).close();
  }

  @Test
  public void closeSocketIgnoresIOException() throws IOException {
    doThrow(new IOException("test")).when(mockSocket).close();
    assertThat(SocketUtils.close(mockSocket), is(false));
    verify(mockSocket, times(1)).close();
  }

  @Test
  public void closeSocketWithNull() {
    assertThat(SocketUtils.close((Socket) null), is(false));
  }

  @Test
  public void closeServerSocketIsSuccessful() throws IOException {
    assertThat(SocketUtils.close(mockServerSocket), is(true));
    verify(mockServerSocket, times(1)).close();
  }

  @Test
  public void testCloseServerSocketThrowsIOException() throws IOException {
    doThrow(new IOException("test")).when(mockServerSocket).close();
    assertThat(SocketUtils.close(mockServerSocket), is(false));
    verify(mockServerSocket, times(1)).close();
  }

  @Test
  public void testCloseServerSocketWithNull() {
    assertThat(SocketUtils.close((ServerSocket) null), is(false));
  }

}
