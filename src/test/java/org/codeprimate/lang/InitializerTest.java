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

package org.codeprimate.lang;

import static org.junit.Assert.*;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The InitializerTest class is a test suite of test cases testing the contract and functionality of the Initializer
 * utility class.
 *
 * @author John J. Blum
 * @see org.codeprimate.lang.Initializer
 * @see org.jmock.Mockery
 * @see org.junit.Test
 * @since 1.0.0
 */
public class InitializerTest {

  private Mockery mockContext;

  @Before
  public void setUp() {
    mockContext = new Mockery();
    mockContext.setImposteriser(ClassImposteriser.INSTANCE);
  }

  @After
  public void tearDown() {
    mockContext.assertIsSatisfied();
    mockContext = null;
  }

  @Test
  public void testInitWithInitableObject() {
    final Initable initableObject = mockContext.mock(Initable.class, "testInitWithInitableObject.Initable");

    mockContext.checking(new Expectations() {{
      oneOf(initableObject).init();
    }});

    assertTrue(Initializer.init(initableObject));
  }

  @Test
  public void testInitWithNonInitiableObject() {
    assertFalse(Initializer.init(new Object()));
  }

}
