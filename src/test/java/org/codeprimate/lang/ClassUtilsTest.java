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

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

/**
 * The ClassUtilsTest class is a test suite of test cases testing the contract and functionality of the ClassUtils class.
 *
 * @author John J. Blum
 * @see org.codeprimate.lang.ClassUtils
 * @see org.junit.Test
 * @since 1.0.0
 */
public class ClassUtilsTest {

  @Test
  public void testForNameWithExistingClass() {
    assertEquals(Object.class, ClassUtils.forName("java.lang.Object", new RuntimeException("unexpected")));
  }

  @Test(expected = RuntimeException.class)
  public void testForNameWithNonExistingClass() {
    try {
      ClassUtils.forName("com.mycompany.non.existing.Class", new RuntimeException("expected"));
    }
    catch (RuntimeException expected) {
      assertEquals("expected", expected.getMessage());
      throw expected;
    }
  }

  @Test(expected = NullPointerException.class)
  public void testForNameWithNullClassName() {
    ClassUtils.forName(null, new IllegalArgumentException("Null Class!"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNameWithEmptyClassName() {
    try {
      ClassUtils.forName(StringUtils.EMPTY_STRING, new IllegalArgumentException("Empty Class Name!"));
    }
    catch (IllegalArgumentException expected) {
      assertEquals("Empty Class Name!", expected.getMessage());
      throw expected;
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNameWithBlankClassName() {
    try {
      ClassUtils.forName("  ", new IllegalArgumentException("Blank Class Name!"));
    }
    catch (IllegalArgumentException expected) {
      assertEquals("Blank Class Name!", expected.getMessage());
      throw expected;
    }
  }

  @Test(expected = NullPointerException.class)
  public void testForNameThrowsNullPointerExceptionForNullRuntimeExceptionArgument() {
    ClassUtils.forName("com.mycompany.non.existing.Class", null);
  }

  @Test
  public void testGetClassWithNull() {
    assertNull(ClassUtils.getClass(null));
  }

  @Test
  public void testGetClassWithObject() {
    assertEquals(java.lang.String.class, ClassUtils.getClass("test"));
  }

  @Test
  public void testGetClassNameWithNull() {
    assertNull(ClassUtils.getClassName(null));
  }

  @Test
  public void testGetClassNameWithObject() {
    assertEquals(java.lang.String.class.getName(), ClassUtils.getClassName("null"));
  }

  @Test
  public void testGetClassSimpleNameWithNull() {
    assertNull(ClassUtils.getClassSimpleName(null));
  }

  @Test
  public void testGetGetClassSimpleNameWithObject() {
    assertEquals(java.lang.String.class.getSimpleName(), ClassUtils.getClassSimpleName("mock"));
  }

  @Test
  public void testIsAnInstanceOf() {
    assertTrue(ClassUtils.isInstanceOf("null", Object.class));
    assertTrue(ClassUtils.isInstanceOf("C", String.class));
    assertTrue(ClassUtils.isInstanceOf(Math.PI, Number.class));
    assertTrue(ClassUtils.isInstanceOf(3.14f, Number.class));
    assertTrue(ClassUtils.isInstanceOf(0l, Number.class));
    assertTrue(ClassUtils.isInstanceOf(0, Number.class));
    assertTrue(ClassUtils.isInstanceOf(true, Boolean.class));
    assertTrue(ClassUtils.isInstanceOf(false, Boolean.class));
  }

  @Test
  public void testIsNotAnInstanceOf() {
    assertFalse(ClassUtils.isInstanceOf(null, null));
    assertFalse(ClassUtils.isInstanceOf(new Object(), null));
    assertFalse(ClassUtils.isInstanceOf(null, Object.class));
    assertFalse(ClassUtils.isInstanceOf('C', String.class));
    assertFalse(ClassUtils.isInstanceOf(Math.PI, Long.class));
    assertFalse(ClassUtils.isInstanceOf(3.14f, Double.class));
    assertFalse(ClassUtils.isInstanceOf(Calendar.getInstance(), Date.class));
    assertFalse(ClassUtils.isInstanceOf(1, Boolean.class));
    assertFalse(ClassUtils.isInstanceOf("false", Boolean.class));
  }

  @Test
  public void testIsNotInstanceOfWithNoTypes() {
    assertTrue(ClassUtils.isNotInstanceOf("test"));
  }

  @Test
  public void testIsNotInstanceOfWithMultipleTypes() {
    assertTrue(ClassUtils.isNotInstanceOf("test", Boolean.class, Character.class, Integer.class, Double.class));
  }

  @Test
  public void testIsNotInstanceOfWithMultipleNumberTypes() {
    assertFalse(ClassUtils.isNotInstanceOf(1, Double.class, Long.class, Number.class));
  }

  @Test
  public void testIsNotInstanceOfWithMultipleCompatibleTypes() {
    assertFalse(ClassUtils.isNotInstanceOf(1, Double.class, Float.class, Integer.class, Long.class, Number.class));
  }

  @Test
  public void testGetNameWithNonNullType() {
    assertEquals(String.class.getName(), ClassUtils.getName(String.class));
  }

  @Test
  public void testGetNameWithNullType() {
    assertNull(ClassUtils.getName(null));
  }

  @Test
  public void testGetSimpleNameWithNonNullType() {
    assertEquals(String.class.getSimpleName(), ClassUtils.getSimpleName(String.class));
  }

  @Test
  public void testGetSimpleNameWithNullType() {
    assertNull(ClassUtils.getSimpleName(null));
  }

  @Test
  public void testIsPresentWithExistingClass() {
    assertTrue(ClassUtils.isPresent("java.lang.Object"));
  }

  @Test
  public void testIsPresentWithNonExistingClass() {
    assertFalse(ClassUtils.isPresent("com.mycompany.non.existing.Class"));
  }

}
