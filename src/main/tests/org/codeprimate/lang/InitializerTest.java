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
