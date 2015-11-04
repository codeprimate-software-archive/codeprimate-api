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

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;

import org.codeprimate.io.IOUtils;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;

/**
 * The SerializableObjectHttpMessageConverterTest class is a test suite of test cases testing the contract
 * and functionality of the SerializableObjectHttpMessageConverter class.
 *
 * @author John J. Blum
 * @see org.codeprimate.ext.spring.http.converter.SerializableObjectHttpMessageConverter
 * @see org.jmock.Mockery
 * @see org.junit.Test
 * @since 1.1.0
 */
public class SerializableObjectHttpMessageConverterTest {

  private Mockery mockContext;

  @Before
  public void setUp() {
    mockContext = new Mockery();
  }

  @After
  public void tearDown() {
    mockContext.assertIsSatisfied();
    mockContext = null;
  }

  @Test
  public void testCreateSerializableObjectHttpMessageConverter() {
    SerializableObjectHttpMessageConverter converter = new SerializableObjectHttpMessageConverter();

    assertNotNull(converter);
    assertTrue(converter.getSupportedMediaTypes().contains(MediaType.APPLICATION_OCTET_STREAM));
    assertTrue(converter.getSupportedMediaTypes().contains(MediaType.ALL));
  }

  @Test
  public void testSupport() {
    SerializableObjectHttpMessageConverter converter = new SerializableObjectHttpMessageConverter();

    assertTrue(converter.supports(Boolean.class));
    assertTrue(converter.supports(Calendar.class));
    assertTrue(converter.supports(Character.class));
    assertTrue(converter.supports(Integer.class));
    assertTrue(converter.supports(Double.class));
    assertTrue(converter.supports(String.class));
    assertTrue(converter.supports(Serializable.class));
  }

  @Test
  public void testDoesNotSupport() {
    SerializableObjectHttpMessageConverter converter = new SerializableObjectHttpMessageConverter();

    assertFalse(converter.supports(Object.class));
    assertFalse(converter.supports(null));
  }

  @Test
  public void testReadInternal() throws IOException {
    final String expectedHttpInputMessageBody = "Expected content of the HTTP input message body!";

    final HttpInputMessage mockHttpInputMessage = mockContext.mock(HttpInputMessage.class,
      "testReadInternal.HttpInputMessage");

    mockContext.checking(new Expectations() {{
      oneOf(mockHttpInputMessage).getBody();
      will(returnValue(new ByteArrayInputStream(IOUtils.serializeObject(expectedHttpInputMessageBody))));
    }});

    SerializableObjectHttpMessageConverter converter = new SerializableObjectHttpMessageConverter();

    Serializable obj = converter.readInternal(String.class, mockHttpInputMessage);

    assertTrue(obj instanceof String);
    assertEquals(expectedHttpInputMessageBody, obj);
  }

  @Test
  public void testSetContentLength() {
    byte[] bytes = { (byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE };

    final HttpHeaders httpHeaders = new HttpHeaders();

    final HttpOutputMessage mockHttpOutputMessage = mockContext.mock(HttpOutputMessage.class,
      "testSetContentLength.HttpOutputMessage");

    mockContext.checking(new Expectations() {{
      oneOf(mockHttpOutputMessage).getHeaders();
      will(returnValue(httpHeaders));
    }});

    SerializableObjectHttpMessageConverter converter = new SerializableObjectHttpMessageConverter();

    converter.setContentLength(mockHttpOutputMessage, bytes);

    assertEquals(bytes.length, httpHeaders.getContentLength());
  }

  @Test
  public void testWriteInternal() throws IOException {
    String expectedHttpOutputMessageBodyContent = "Expected content of the HTTP output message body!";

    byte[] expectedHttpOutputMessageBody = IOUtils.serializeObject(expectedHttpOutputMessageBodyContent);

    final ByteArrayOutputStream out = new ByteArrayOutputStream(expectedHttpOutputMessageBody.length);

    final HttpHeaders httpHeaders = new HttpHeaders();

    final HttpOutputMessage mockHttpOutputMessage = mockContext.mock(HttpOutputMessage.class,
      "testWriteInternal.HttpOutputMessage");

    mockContext.checking(new Expectations() {{
      oneOf(mockHttpOutputMessage).getHeaders();
      will(returnValue(httpHeaders));
      oneOf(mockHttpOutputMessage).getBody();
      will(returnValue(out));
    }});

    SerializableObjectHttpMessageConverter converter = new SerializableObjectHttpMessageConverter();

    converter.writeInternal(expectedHttpOutputMessageBodyContent, mockHttpOutputMessage);

    byte[] actualHttpOutputMessageBody = out.toByteArray();

    assertEquals(expectedHttpOutputMessageBody.length, httpHeaders.getContentLength());
    assertEquals(expectedHttpOutputMessageBody.length, actualHttpOutputMessageBody.length);

    for (int index = 0; index < actualHttpOutputMessageBody.length; index++) {
      assertEquals(expectedHttpOutputMessageBody[index], actualHttpOutputMessageBody[index]);
    }
  }

}
