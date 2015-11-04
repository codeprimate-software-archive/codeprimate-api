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

package org.codeprimate.io;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * The IOUtilsTest class is a test suite of test cases testing the contract and functionality of the IOUtils class.
 *
 * @author John J. Blum
 * @see org.codeprimate.io.IOUtils
 * @see org.jmock.Mockery
 * @see org.junit.Test
 */
@RunWith(MockitoJUnitRunner.class)
public class IOUtilsTest {

  @Mock
  private Closeable mockCloseable;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  /**
   * Gets a fully-qualified path anchored at root.
   *
   * @param pathElements a String array containing the elements of the path.
   * @return a fully-qualified pathname as a String value.
   */
  protected String toPathname(String... pathElements) {
    if (pathElements != null) {
      StringBuilder buffer = new StringBuilder();

      for (String pathElement : pathElements) {
        buffer.append(File.separator);
        buffer.append(pathElement);
      }

      return buffer.toString();
    }

    return null;
  }

  @Test
  public void appendToPath() {
    assertEquals(null, FileSystemUtils.appendToPath(null, (String[]) null));
    assertEquals(File.separator, FileSystemUtils.appendToPath(null));
    assertEquals(File.separator, FileSystemUtils.appendToPath(""));
    assertEquals(File.separator, FileSystemUtils.appendToPath(" "));
    assertEquals(File.separator, FileSystemUtils.appendToPath(File.separator));
    assertEquals(toPathname("bin", "a.out"), FileSystemUtils.appendToPath(null, "bin", "a.out"));
    assertEquals(toPathname("bin", "a.out"), FileSystemUtils.appendToPath(File.separator, "bin", "a.out"));
    assertEquals(toPathname("usr", "local", "bin", "a.out"), FileSystemUtils.appendToPath(
      toPathname("usr", "local"), "bin", "a.out"));
  }

  @Test
  public void close() throws IOException {
    assertThat(IOUtils.close(mockCloseable), is(true));
    verify(mockCloseable, times(1)).close();
  }

  @Test
  @SuppressWarnings("all")
  public void closeIgnoresIOException() throws IOException {
    doThrow(new IOException("test")).when(mockCloseable).close();
    assertThat(IOUtils.close(mockCloseable), is(false));
    verify(mockCloseable, times(1)).close();
  }

  @Test
  public void createPath() {
    assertEquals("", FileSystemUtils.createPath());
    assertEquals("/path/to/file.test".replace("/", File.separator), FileSystemUtils.createPath("path", "to", "file.test"));
    assertEquals("/path/to/a/directory".replace("/", File.separator), FileSystemUtils.createPath("path", "to", "a", "directory"));
  }

  @Test
  public void createPathWithUserDefinedSeparator() {
    assertEquals("", FileSystemUtils.createPath(new String[0], "-"));
    assertEquals("-path-to-file.ext".replace("/", File.separator), FileSystemUtils.createPath(new String[] {
      "path",
      "to",
      "file.ext"
    }, "-"));
    assertEquals("-path-to-a-directory", FileSystemUtils.createPath(new String[] { "path", "to", "a", "directory" }, "-"));
  }

  @Test
  public void getFilename() {
    assertNull(FileSystemUtils.getFilename(null));
    assertEquals("", FileSystemUtils.getFilename(""));
    assertEquals("  ", FileSystemUtils.getFilename("  "));
    assertEquals("", FileSystemUtils.getFilename(File.separator));
    assertEquals("a.ext", FileSystemUtils.getFilename("a.ext"));
    assertEquals("b.ext", FileSystemUtils.getFilename(toPathname("b.ext")));
    assertEquals("c.ext", FileSystemUtils.getFilename(toPathname("path", "to", "c.ext")));
    assertEquals("filename.ext", FileSystemUtils.getFilename(toPathname("export", "path", "to", "some", "filename.ext")));
    assertEquals("", FileSystemUtils.getFilename(toPathname("path", "to", "a", "directory") + File.separator));
  }

  @Test
  public void isExistingPathname() {
    assertTrue(FileUtils.exists(System.getProperty("java.home")));
    assertTrue(FileUtils.exists(System.getProperty("user.home")));
    assertFalse(FileUtils.exists("/path/to/non_existing/directory/"));
    assertFalse(FileUtils.exists("/path/to/non_existing/file.ext"));
  }

  @Test
  public void objectSerialization() throws IOException, ClassNotFoundException {
    final Calendar now = Calendar.getInstance();

    assertNotNull(now);

    final byte[] nowBytes = IOUtils.serializeObject(now);

    assertNotNull(nowBytes);
    assertTrue(nowBytes.length != 0);

    final Object nowObj = IOUtils.deserializeObject(nowBytes);

    assertTrue(nowObj instanceof Calendar);
    assertEquals(now.getTimeInMillis(), ((Calendar) nowObj).getTimeInMillis());
  }

  @Test
  public void objectSerializationWithClassLoader() throws IOException, ClassNotFoundException {
    final BigDecimal pi = new BigDecimal(Math.PI);

    final byte[] piBytes = IOUtils.serializeObject(pi);

    assertNotNull(piBytes);
    assertTrue(piBytes.length != 0);

    final Object piObj = IOUtils.deserializeObject(piBytes, IOUtilsTest.class.getClassLoader());

    assertTrue(piObj instanceof BigDecimal);
    assertEquals(pi, piObj);
  }

  @Test
  public void toByteArray() throws IOException {
    final byte[] expected = new byte[] { (byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE };

    final byte[] actual = IOUtils.toByteArray(new ByteArrayInputStream(expected));

    assertNotNull(actual);
    assertEquals(expected.length, actual.length);

    for (int index = 0; index < actual.length; index++) {
      assertEquals(expected[index], actual[index]);
    }
  }

  @Test
  @SuppressWarnings("all")
  public void toByteArrayThrowsIOException() throws IOException {
    InputStream mockInputStream = mock(InputStream.class, "testToByteArrayThrowsIOException.MockInputStream");

    when(mockInputStream.read(any(byte[].class))).thenThrow(new IOException("test"));

    expectedException.expect(IOException.class);
    expectedException.expectCause(is(nullValue(Throwable.class)));
    expectedException.expectMessage("test");

    IOUtils.toByteArray(mockInputStream);

    verify(mockInputStream, times(1)).read(any(byte[].class));
    verify(mockInputStream, times(1)).close();
  }

  @Test
  public void toByteArrayWithNull() throws IOException {
    expectedException.expect(NullPointerException.class);
    expectedException.expectCause(is(nullValue(Throwable.class)));
    expectedException.expectMessage(is(equalTo("The input stream to read bytes from cannot be null!")));
    IOUtils.toByteArray(null);
  }

  @Test
  public void tryGetCanonicalFileElseGetAbsoluteFile() throws Exception {
    final MockFile file = (MockFile) FileSystemUtils.tryGetCanonicalFileElseGetAbsoluteFile(
      new MockFile("/path/to/non_existing/file.test", null));

    assertNotNull(file);
    assertFalse(file.exists());
    assertTrue(file.isGetCanonicalFileCalled());
    assertFalse(file.isGetAbsoluteFileCalled());
  }

  @Test
  public void tryGetCanonicalFileElseGetAbsoluteFileHandlesIOException() throws Exception {
    final MockFile file = (MockFile) FileSystemUtils.tryGetCanonicalFileElseGetAbsoluteFile(
      new MockFile(System.getProperty("user.home"), new IOException("test")));

    assertNotNull(file);
    assertTrue(file.exists());
    assertTrue(file.isGetCanonicalFileCalled());
    assertTrue(file.isGetAbsoluteFileCalled());
  }

  @Test
  public void verifyPathnameExists() throws FileNotFoundException {
    assertEquals(System.getProperty("java.io.tmpdir"), FileUtils.assertExists(
      System.getProperty("java.io.tmpdir")));
  }

  @Test(expected = FileNotFoundException.class)
  public void verifyPathnameExistsWithNonExistingPathname() throws FileNotFoundException {
    try {
      FileUtils.assertExists("/path/to/non_existing/file.test");
    }
    catch (FileNotFoundException expected) {
      assertEquals("Pathname (/path/to/non_existing/file.test) could not be found!", expected.getMessage());
      throw expected;
    }
  }

  protected static final class MockFile extends File {

    private boolean isGetAbsoluteFileCalled = false;
    private boolean isGetCanonicalFileCalled = false;

    private final IOException e;

    public MockFile(final String pathname, IOException e) {
      super(pathname);
      this.e = e;
    }

    @Override
    @SuppressWarnings("all")
    public File getAbsoluteFile() {
      isGetAbsoluteFileCalled = true;
      return this;
    }

    protected boolean isGetAbsoluteFileCalled() {
      return isGetAbsoluteFileCalled;
    }

    @Override
    @SuppressWarnings("all")
    public File getCanonicalFile() throws IOException {
      isGetCanonicalFileCalled = true;

      if (e != null) {
        throw e;
      }

      return this;
    }

    protected boolean isGetCanonicalFileCalled() {
      return isGetCanonicalFileCalled;
    }
  }

}
