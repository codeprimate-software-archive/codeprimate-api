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

package org.codeprimate.ext.spring.http.converter;

import java.io.IOException;
import java.io.Serializable;

import org.codeprimate.io.IOUtils;
import org.codeprimate.lang.ObjectUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

/**
 * The SerializableObjectHttpMessageConverter class is a Spring HttpMessageConverter for converting bytes streams
 * to/from Serializable Objects.
 *
 * @author John J. Blum
 * @see java.io.Serializable
 * @see org.springframework.http.HttpInputMessage
 * @see org.springframework.http.HttpMessage
 * @see org.springframework.http.HttpOutputMessage
 * @see org.springframework.http.MediaType
 * @see org.springframework.http.converter.AbstractHttpMessageConverter
 * @since 1.1.0
 */
@SuppressWarnings("unused")
public class SerializableObjectHttpMessageConverter extends AbstractHttpMessageConverter<Serializable> {

  /**
   * Constructs an instance of the SerializableObjectHttpMessageConverter class initialized to support octet streams
   * and all MediaTypes.
   *
   * @see org.springframework.http.converter.AbstractHttpMessageConverter(org.springframework.http.MediaType...)
   * @see org.springframework.http.MediaType#ALL
   * @see org.springframework.http.MediaType#APPLICATION_OCTET_STREAM
   */
  public SerializableObjectHttpMessageConverter() {
    super(MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL);
  }

  /*
  @Override
  public boolean canRead(final Class<?> clazz, final MediaType mediaType) {
    return canRead(mediaType);
  }
  */

  /**
   * Determines whether an object of the specified Class type is supported by this HttpMessageConverter.
   *
   * @param type the Class type being evaluated for support.
   * @return a boolean value indicating whether objects of the specified Class type are supported
   * by this HttpMessageConverter.
   * @see java.lang.Class
   */
  @Override
  protected boolean supports(final Class<?> type) {
    if (logger.isTraceEnabled()) {
      logger.trace(String.format("%1$s.supports(%2$s)", getClass().getName(), type.getName()),
        new Throwable());
    }

    return (type != null && Serializable.class.isAssignableFrom(type));
  }

  /**
   * Reads the contents of the HTTP message body as a byte stream expecting to convert the bytes into an object
   * of the specified Class type using Java Serialization.
   *
   * @param type the Class type of the serialized object (bytes).
   * @param inputMessage the HTTP message containing the content (bytes) of the serialized object.
   * @return the HTTP message body content (bytes) de-serialized into an object of the specified Class type.
   * @throws IOException if an I/O error occurs while reading the byte stream and/or de-serialization of the object.
   * @throws HttpMessageNotReadableException if the HTTP message body contents could not be read.
   * @see java.io.Serializable
   * @see org.codeprimate.io.IOUtils#deserializeObject(byte[])
   * @see org.codeprimate.io.IOUtils#toByteArray(java.io.InputStream)
   * @see org.springframework.http.HttpInputMessage
   */
  @Override
  protected Serializable readInternal(final Class<? extends Serializable> type, final HttpInputMessage inputMessage)
    throws IOException, HttpMessageNotReadableException
  {
    try {
      return type.cast(IOUtils.deserializeObject(IOUtils.toByteArray(inputMessage.getBody()),
        ObjectUtils.defaultIfNull(type.getClassLoader(), Thread.currentThread().getContextClassLoader(),
          getClass().getClassLoader())));
    }
    catch (ClassNotFoundException e) {
      throw new HttpMessageNotReadableException(String.format(
        "Unable to convert the HTTP message body into an Object of type (%1$s)", type.getName()), e);
    }
  }

  /**
   * Determines and set the HTTP message 'Content-Length' Header to size of the serialized object
   * as the number of bytes.
   *
   * @param message the HttpMessage on which to set the 'Content-Length' Header.
   * @param messageBody a byte array containing the content of the HTTP message body for writing.
   * @see org.springframework.http.HttpMessage#getHeaders()
   * @see org.springframework.http.HttpHeaders#setContentLength(long)
   */
  protected void setContentLength(final HttpMessage message, final byte[] messageBody) {
    message.getHeaders().setContentLength(messageBody.length);
  }

  /**
   * Writes the contents of the HTTP message body by serializing the given Serializable object
   * using Java Serialization.
   *
   * @param serializableObject the Java Serializable object to serialize and write as a stream of bytes
   * to the HTTP message body.
   * @param outputMessage the HTTP message used for output.
   * @throws IOException if an I/O error occurs during serialization and/or while writing the stream of bytes
   * to the HTTP message body.
   * @throws HttpMessageNotWritableException if the HTTP message body could not be written.
   * @see #setContentLength(org.springframework.http.HttpMessage, byte[])
   * @see java.io.Serializable
   * @see org.codeprimate.io.IOUtils#serializeObject(Object)
   * @see org.springframework.http.HttpOutputMessage
   * @see org.springframework.util.StreamUtils#copy(byte[], java.io.OutputStream)
   */
  @Override
  protected void writeInternal(final Serializable serializableObject, final HttpOutputMessage outputMessage)
    throws IOException, HttpMessageNotWritableException
  {
    byte[] messageBody = IOUtils.serializeObject(serializableObject);
    setContentLength(outputMessage, messageBody);
    StreamUtils.copy(messageBody, outputMessage.getBody());
  }

}
