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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;

import org.codeprimate.lang.ObjectUtils;

/**
 * The IOUtils class is abstract utility class encapsulating reusable Input/Output (IO) operations.
 * 
 * @author John J. Blum
 * @see java.io.Closeable
 * @see java.io.InputStream
 * @see java.io.OutputStream
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class IOUtils {

  public static final int BUFFER_SIZE = 8192;

  /**
   * Invokes the close method on any class instance implementing the Closeable interface, such as InputStreams
   * and OutputStreams.  Note, this method silently ignores the possible IOException resulting from the close
   * invocation.
   * 
   * @param obj an Object implementing the Closeable interface who's close method will be invoked.
   * @return a boolean value indicating whether the Closeable object was successfully closed.
   * @see java.io.Closeable#close()
   */
  public static boolean close(final Closeable obj) {
    if (obj != null) {
      try {
        obj.close();
        return true;
      }
      catch (IOException ignore) {
      }
    }

    return false;
  }

  public static void copy(final InputStream in, final OutputStream out) throws IOException {
    byte[] buffer = new byte[BUFFER_SIZE];

    for (int length = in.read(buffer); length > 0; length = in.read(buffer)) {
      out.write(buffer, 0, length);
      out.flush();
    }
  }

  /**
   * Utility method for de-serializing a byte array back into an Object.
   * 
   * @param objBytes an array of bytes constituting the serialized form of the Object.
   * @return a Serializable Object from the array of bytes.
   * @throws ClassNotFoundException if the Class type of the serialized Object cannot be resolved.
   * @throws IOException if an I/O error occurs during the de-serialization process.
   * @see #deserializeObject(byte[], ClassLoader)
   * @see #serializeObject(Object)
   * @see java.io.ByteArrayInputStream
   * @see java.io.ObjectInputStream
   * @see java.io.Serializable
   */
  public static Object deserializeObject(final byte[] objBytes) throws IOException, ClassNotFoundException {
    ObjectInputStream objIn = null;

    try {
      objIn = new ObjectInputStream(new ByteArrayInputStream(objBytes));
      return objIn.readObject();
    }
    finally {
      close(objIn);
    }
  }

  /**
   * Utility method for de-serializing a byte array back into an Object who's Class type is resolved by the specified
   * ClassLoader.
   * 
   * @param objBytes an array of bytes constituting the serialized form of the Object.
   * @param loader the ClassLoader used to resolve the Class type of the serialized Object.
   * @return a Serializable Object from the array of bytes.
   * @throws ClassNotFoundException if the Class type of the serialized Object cannot be resolved by the specified
   * ClassLoader.
   * @throws IOException if an I/O error occurs while de-serializing the Object from the array of bytes.
   * @see #deserializeObject(byte[])
   * @see #serializeObject(Object)
   * @see IOUtils.ClassLoaderObjectInputStream
   * @see java.lang.ClassLoader
   * @see java.io.ByteArrayInputStream
   * @see java.io.ObjectInputStream
   * @see java.io.Serializable
   */
  public static Object deserializeObject(final byte[] objBytes, final ClassLoader loader)
    throws IOException, ClassNotFoundException
  {
    ObjectInputStream objIn = null;

    try {
      objIn = new ClassLoaderObjectInputStream(new ByteArrayInputStream(objBytes), loader);
      return objIn.readObject();
    }
    finally {
      close(objIn);
    }
  }

  /**
   * Utility method for serializing a Serializable object into a byte array.
   * 
   * @param obj the Serializable Object to serialize into an array of bytes.
   * @return a byte array of the serialized Object.
   * @throws IOException if an I/O error occurs during the serialization process.
   * @see #deserializeObject(byte[])
   * @see java.io.ByteArrayOutputStream
   * @see java.io.ObjectOutputStream
   * @see java.io.Serializable
   */
  public static byte[] serializeObject(final Object obj) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    ObjectOutputStream objOut = null;

    try {
      objOut = new ObjectOutputStream(out);
      objOut.writeObject(obj);
      objOut.flush();

      return out.toByteArray();
    }
    finally {
      close(objOut);
    }
  }

  /**
   * Reads the contents of the specified InputStream into a byte array.
   * 
   * @param in the InputStream to read content from.
   * @return a byte array containing the content of the specified InputStream.
   * @throws IOException if an I/O error occurs while reading the InputStream.
   * @see java.io.ByteArrayOutputStream
   * @see java.io.InputStream
   */
  public static byte[] toByteArray(final InputStream in) throws IOException {
    assert in != null : "The input stream to read bytes from cannot be null!";

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    byte[] buffer = new byte[BUFFER_SIZE];
    int bytesRead;

    try {
      while ((bytesRead = in.read(buffer)) != -1) {
        out.write(buffer, 0, bytesRead);
        out.flush();
      }
    }
    finally {
      close(in);
      close(out);
    }

    return out.toByteArray();
  }

  /**
   * The ClassLoaderObjectInputStream class is a ObjectInputStream implementation that resolves the Class type of
   * the Object being de-serialized with the specified ClassLoader.
   * 
   * @see java.lang.ClassLoader
   * @see java.lang.Thread#getContextClassLoader()
   * @see java.io.ObjectInputStream
   */
  protected static class ClassLoaderObjectInputStream extends ObjectInputStream {

    private final ClassLoader loader;

    public ClassLoaderObjectInputStream(final InputStream in, final ClassLoader loader) throws IOException {
      super(in);
      this.loader = ObjectUtils.defaultIfNull(loader, Thread.currentThread().getContextClassLoader());
    }

    protected ClassLoader getClassLoader() {
      return loader;
    }

    @Override
    protected Class<?> resolveClass(final ObjectStreamClass descriptor) throws IOException, ClassNotFoundException {
      return Class.forName(descriptor.getName(), false, getClassLoader());
    }
  }

}
