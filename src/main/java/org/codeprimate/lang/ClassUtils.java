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

/**
 * The ClassUtils class is an abstract utility class for interacting with Class objects.
 *
 * @author John J. Blum
 * @see java.lang.Class
 * @see java.lang.Object
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class ClassUtils {

  public static final Class[] EMPTY_CLASS_ARRAY = new Class[0];

  /**
   * Attempts to load the specified Class by its fully qualified class name.  If the class cannot be found, 
   * then this method handles the ClassNotFoundException or NoClassDefFoundError and throws the specified 
   * RuntimeException instead.
   * 
   * @param className a String indicating the fully qualified class name of the Class to load.
   * @param e the RuntimeException to throw in place of the ClassNotFoundException or NoClassDefFoundError if the class 
   * cannot be found and loaded.
   * @return a Class object representing the specified class by name.
   * @throws NullPointerException if className or RuntimeException is null.
   * @see java.lang.Class#forName(String)
   */
  public static Class forName(final String className, final RuntimeException e) {
    try {
      return Class.forName(className);
    }
    catch (ClassNotFoundException ignore) {
      throw e;
    }
    catch (NoClassDefFoundError ignore) {
      throw e;
    }
  }

  /**
   * Gets the Class type for the specified Object, or null if the Object reference is null.
   * 
   * @param obj the Object who's class type is determined.
   * @return the Class type of the Object argument, or null if the Object reference is null.
   * @see java.lang.Object#getClass()
   */
  public static Class getClass(final Object obj) {
    return (obj == null ? null : obj.getClass());
  }

  /**
   * Gets the name of the Object's Class type or null if the Object reference is null.
   * 
   * @param obj the Object's who's fully qualified class name is returned or null if the Object reference is null.
   * @return a String value specifying the fully qualified name of the Object's class type.
   * @see java.lang.Class#getName()
   * @see java.lang.Object#getClass()
   */
  public static String getClassName(final Object obj) {
    return (obj == null ? null : obj.getClass().getName());
  }

  /**
   * Gets the simple name of the Object's Class type or null if the Object reference is null.
   *
   * @param obj the Object's who's simple class name is returned or null if the Object reference is null.
   * @return a String value specifying the simple name of the Object's class type.
   * @see java.lang.Class#getSimpleName()
   * @see java.lang.Object#getClass()
   */
  public static String getClassSimpleName(final Object obj) {
    return (obj == null ? null : obj.getClass().getSimpleName());
  }

  /**
   * Determines whether the specified Object is an instance of, or is assignment-compatible with, the given Class type.
   * This method is null-safe for both Class and Object references.
   *
   * @param obj the Object being determined for assignment-compatibility with the specified Class type.
   * @param type the Class type used in an instanceof determination with the given Object.
   * @return a boolean value indicating whether the given Object is an instance of the specified Class type.
   * @see java.lang.Class#isInstance(Object)
   */
  public static boolean isInstanceOf(final Object obj, final Class type) {
    return (type != null && type.isInstance(obj));
  }

  /**
   * Determines if the specified Object is not a instance of any of the following Class types.  The Object is
   * an instance if the Object is instance of just 1 of the Class types.
   *
   * @param obj the Object who's Class type is in question.
   * @param types the array of Class types to which the Object is evaluated for instanceof.
   * @return a boolean value of true if and only if the Object is not an instance of any of the specified Class types.
   * @see java.lang.Class#isInstance(Object)
   */
  public static boolean isNotInstanceOf(final Object obj, final Class... types) {
    boolean condition = true;

    if (types != null) {
      for (Class type : types) {
        condition &= !type.isInstance(obj);
      }
    }

    return condition;
  }

  /**
   * Gets the fully-qualified name of the Class type.
   *
   * @param type the Class type to determine the fully-qualified name of.
   * @return a String indicating the fully-qualified name of the Class type.
   * @see java.lang.Class#getName()
   */
  public static String getName(final Class type) {
    return (type == null ? null : type.getName());
  }

  /**
   * Gets the simple name of the Class type.
   *
   * @param type the Class type to determine the simple name of.
   * @return a String indicating the simple name of the Class type.
   * @see java.lang.Class#getSimpleName()
   */
  public static String getSimpleName(final Class type) {
    return (type == null ? null : type.getSimpleName());
  }

  /**
   * Determine whether the specified class is on the classpath.
   *
   * @param className a String value specifying the fully-qualified name of the class.
   * @return a boolean value indicating whether the specified class is on the classpath.
   * @see #forName(String, RuntimeException)
   */
  public static boolean isPresent(final String className) {
    try {
      forName(className, new IllegalArgumentException(String.format(
        "Class (%1$s) is not on the classpath!", className)));
      return true;
    }
    catch (IllegalArgumentException ignore) {
      return false;
    }
  }

}
