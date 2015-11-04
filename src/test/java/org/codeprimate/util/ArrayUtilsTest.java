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

package org.codeprimate.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * The ArrayUtilsTest class is a test suite of test cases testing the contract and functionality
 * of the ArrayUtils class.
 *
 * @author jblum
 * @see org.codeprimate.util.ArrayUtils
 * @since 1.0.0
 */
public class ArrayUtilsTest {

  @SafeVarargs
  protected static <T> T[] toArray(T... array) {
    return array;
  }

  @Test
  @SuppressWarnings("null")
  public void testGetElementAtIndex() {
    Object[] array = { "test", "testing", "tested" };

    assertEquals("test", ArrayUtils.elementAt(array, 0, null));
    assertEquals("testing", ArrayUtils.elementAt(array, 1, null));
    assertEquals("tested", ArrayUtils.elementAt(array, 2, null));
  }

  @Test
  public void testGetElementAtIndexThrowingArrayIndexOutOfBoundsException() {
    assertEquals("test", ArrayUtils.elementAt(new Object[0], 0, "test"));
  }

  @Test
  public void testGetElementAtIndexThrowingArrayIndexOutOfBoundsExceptionOnNonEmptyArray() {
    assertEquals("defaultValue", ArrayUtils.elementAt(new Object[] { "test" }, 1, "defaultValue"));
  }

  @Test
  public void testGetFirst() {
    assertEquals("first", ArrayUtils.getFirst(toArray("first", "second", "third")));
    assertEquals("null", ArrayUtils.getFirst(toArray("null", "nil", null)));
    assertEquals("test", ArrayUtils.getFirst(toArray("test")));
    assertNull(ArrayUtils.getFirst(null));
    assertNull(ArrayUtils.getFirst(new Object[0]));
    assertNull(ArrayUtils.getFirst(toArray(null, null, null)));
  }

  @Test
  public void testGetLast() {
    assertEquals("third", ArrayUtils.getLast(toArray("first", "second", "third")));
    assertNull(ArrayUtils.getLast(toArray("null", "nil", null)));
    assertEquals("test", ArrayUtils.getLast(toArray("test")));
    assertNull(ArrayUtils.getLast(null));
    assertNull(ArrayUtils.getLast(new Object[0]));
    assertNull(ArrayUtils.getLast(toArray(null, null, null)));
  }

  @Test
  public void testToString() {
    Object[] array = { "test", "testing", "tested" };

    assertEquals("[test, testing, tested]", ArrayUtils.toString(array));
  }

  @Test
  public void testToStringWithEmptyArray() {
    assertEquals("[]", ArrayUtils.toString((new Object[0])));
  }

  @Test
  public void testToStringWithNullArray() {
    assertEquals("[]", ArrayUtils.toString((Object[]) null));
  }

}
