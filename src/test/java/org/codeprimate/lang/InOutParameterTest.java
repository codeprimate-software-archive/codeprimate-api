package org.codeprimate.lang;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * The InOutParameterTest class is a test suite with test cases to test the contract and functionality of the
 * InOutParameter class.
 *
 * @author John J. Blum
 * @see org.codeprimate.lang.InOutParameter
 * @see org.junit.Test
 * @since 1.0.0
 */
public class InOutParameterTest {

  @Test
  public void testEquals() {
    assertEquals(new InOutParameter<Object>(null), new InOutParameter<Object>(null));
    assertEquals(new InOutParameter<Object>(null), null);
    assertEquals(new InOutParameter<Object>("test"), new InOutParameter<Object>("test"));
    assertEquals(new InOutParameter<Object>("test"), "test");
    assertEquals(new InOutParameter<Object>(Math.PI), new InOutParameter<Object>(Math.PI));
    assertEquals(new InOutParameter<Object>(Math.PI), Math.PI);
    assertEquals(new InOutParameter<Object>(true), new InOutParameter<Object>(true));
    assertEquals(new InOutParameter<Object>(true), true);
  }

  @Test
  public void testNotEqual() {
    assertFalse(new InOutParameter<Object>("null").equals(new InOutParameter<Object>(null)));
    assertFalse(new InOutParameter<Object>(Math.PI).equals(3.14159d));
    assertFalse(new InOutParameter<Object>("test").equals("TEST"));
  }

  @Test
  public void testHashCode() {
    assertEquals(0, new InOutParameter<Object>(null).hashCode());
    assertEquals("test".hashCode(), new InOutParameter<Object>("test").hashCode());
    assertEquals(new Double(Math.PI).hashCode(), new InOutParameter<Object>(Math.PI).hashCode());
    assertEquals(Boolean.TRUE.hashCode(), new InOutParameter<Object>(true).hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("null", new InOutParameter<Object>(null).toString());
    assertEquals("null", new InOutParameter<Object>("null").toString());
    assertEquals("test", new InOutParameter<Object>("test").toString());
    assertEquals(String.valueOf(Math.PI), new InOutParameter<Object>(Math.PI).toString());
    assertEquals("true", new InOutParameter<Object>(Boolean.TRUE).toString());
  }

}
