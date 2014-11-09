package org.codeprimate.lang;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * The ObjectUtilsTest class is a test suite of test cases for testing the contract and functionality of the ObjectUtils
 * class.
 *
 * @author John J. Blum
 * @see org.codeprimate.lang.ObjectUtils
 * @see org.junit.Test
 * @since 1.0.0
 */
public class ObjectUtilsTest {

  @Test
  public void testDefaultIfNull() {
    assertNull(ObjectUtils.defaultIfNull());
    assertNull(ObjectUtils.defaultIfNull((Object[]) null));
    assertNull(ObjectUtils.defaultIfNull(null, null));
    assertNull(ObjectUtils.defaultIfNull(null, null, null));
    assertEquals("test", ObjectUtils.defaultIfNull(null, null, "test"));
    assertEquals("test", ObjectUtils.defaultIfNull("test", null));
    assertEquals("test", ObjectUtils.defaultIfNull("test"));
    assertEquals("test", ObjectUtils.defaultIfNull("test", "mock", "assert"));
    assertEquals("null", ObjectUtils.defaultIfNull(null, "null", null));
    assertEquals("null", ObjectUtils.defaultIfNull("null", "test", null));
  }

  @Test
  public void testEqualsWithUnequalObjects() {
    assertFalse(ObjectUtils.nullSafeEquals(null, null));
    assertFalse(ObjectUtils.nullSafeEquals(null, "null"));
    assertFalse(ObjectUtils.nullSafeEquals("nil", null));
    assertFalse(ObjectUtils.nullSafeEquals("nil", "null"));
    assertFalse(ObjectUtils.nullSafeEquals("true", true));
    assertFalse(ObjectUtils.nullSafeEquals(false, true));
    assertFalse(ObjectUtils.nullSafeEquals('c', 'C'));
    assertFalse(ObjectUtils.nullSafeEquals(0.0d, -0.0d));
    assertFalse(ObjectUtils.nullSafeEquals(3.14159d, Math.PI));
    assertFalse(ObjectUtils.nullSafeEquals(Integer.MIN_VALUE, Integer.MAX_VALUE));
    assertFalse(ObjectUtils.nullSafeEquals("test", "TEST"));
  }

  @Test
  public void testEqualsWithEqualObjects() {
    assertTrue(ObjectUtils.nullSafeEquals(true, Boolean.TRUE));
    assertTrue(ObjectUtils.nullSafeEquals(new Character('c'), 'c'));
    assertTrue(ObjectUtils.nullSafeEquals(Double.MIN_VALUE, Double.MIN_VALUE));
    assertTrue(ObjectUtils.nullSafeEquals(Integer.MAX_VALUE, Integer.MAX_VALUE));
    assertTrue(ObjectUtils.nullSafeEquals("null", "null"));
    assertTrue(ObjectUtils.nullSafeEquals("test", new String("test")));
  }

  @Test
  public void testEqualsIgnoreNullWithUnequalObjects() {
    assertFalse(ObjectUtils.equalsIgnoreNull(null, "null"));
    assertFalse(ObjectUtils.equalsIgnoreNull("nil", null));
    assertFalse(ObjectUtils.equalsIgnoreNull("nil", "null"));
    assertFalse(ObjectUtils.equalsIgnoreNull("test", "testing"));
  }

  @Test
  public void testEqualsIgnoreNullWithEqualObjects() {
    assertTrue(ObjectUtils.equalsIgnoreNull(null, null));
    assertTrue(ObjectUtils.equalsIgnoreNull("nil", "nil"));
    assertTrue(ObjectUtils.equalsIgnoreNull("null", "null"));
    assertTrue(ObjectUtils.equalsIgnoreNull("test", "test"));
  }

  @Test
  public void testHashCode() {
    assertEquals(0, ObjectUtils.hashCode(null));
    assertEquals(Character.valueOf('c').hashCode(), ObjectUtils.hashCode('c'));
    assertEquals(Boolean.TRUE.hashCode(), ObjectUtils.hashCode(true));
    assertEquals(Double.valueOf(Math.PI).hashCode(), ObjectUtils.hashCode(Math.PI));
    assertEquals(Integer.valueOf(0).hashCode(), ObjectUtils.hashCode(0));
    assertEquals("test".hashCode(), ObjectUtils.hashCode("test"));
  }

  @Test
  public void testToString() {
    assertNull(ObjectUtils.toString(null));
    assertEquals("", ObjectUtils.toString(""));
    assertEquals(" ", ObjectUtils.toString(" "));
    assertEquals("null", ObjectUtils.toString("null"));
    assertEquals("test", ObjectUtils.toString("test"));
    assertEquals("J", ObjectUtils.toString('J'));
    assertEquals("2", ObjectUtils.toString(2));
    assertEquals(String.valueOf(Math.PI), ObjectUtils.toString(Math.PI));
    assertEquals("true", ObjectUtils.toString(Boolean.TRUE));
  }

  @Test
  public void testGetArgumentsTypesForNullArgumentsObjectArray() {
    assertNull(ObjectUtils.getArgumentTypes((Object[]) null));
  }

  @Test
  public void testGetArgumentsTypesForEmptyArgumentsObjectArray() {
    final Class[] argumentTypes = ObjectUtils.getArgumentTypes(new Object[0]);

    assertNotNull(argumentTypes);
    assertEquals(0, argumentTypes.length);
  }

  @Test
  public void testGetArgumentsTypes() {
    final Object[] arguments = { true, 'A', 0, Math.PI, "test" };
    final Class[] argumentTypes = ObjectUtils.getArgumentTypes(arguments);

    assertNotNull(argumentTypes);
    assertEquals(arguments.length, argumentTypes.length);

    int index = 0;

    for (Object argument : arguments) {
      assertEquals(argument.getClass(), argumentTypes[index++]);
    }
  }

  @Test
  public void testInvoke() {
    final ValueHolder<String> value = new ValueHolder<String>("test");

    assertEquals("test", ObjectUtils.invoke(value, "getValue"));
  }

  @Test
  public void testInvokeWithArguments() {
    final ValueHolder<String> value = new ValueHolder<String>("test");

    assertEquals("TEST", ObjectUtils.invoke(value, "transform", true));
  }

  @Test
  public void testInvokeWithParametersAndArguments() {
    final ValueHolder<Integer> value = new ValueHolder<Integer>(1);

    assertEquals("1 is the loneliest number!", ObjectUtils.invoke(value, "transform", new Class[] { String.class },
      " is the loneliest number!"));
  }

  public static final class ValueHolder<T> {

    private final T value;

    public ValueHolder(final T value) {
      assert value != null : "The value for this holder cannot be null!";
      this.value = value;
    }

    public T getValue() {
      return value;
    }

    public Object transform(final Boolean upperCase) {
      return String.valueOf(getValue()).toUpperCase();
    }

    public Object transform(final String concatenationValue) {
      return (String.valueOf(getValue()) + concatenationValue);
    }
  }

}