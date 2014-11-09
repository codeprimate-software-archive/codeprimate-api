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

import org.codeprimate.lang.StringUtils;

/**
 * The ArrayUtils class is an abstract utility class for interacting with Object arrays.
 *
 * @author John J. Blum
 * @see java.util.Arrays
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class ArrayUtils {

  public static final Object[] EMPTY_ARRAY = new Object[0];

  /**
   * Gets the element at index in the array in a bound-safe manner.  If index is not a valid index in the given array,
   * then the default value is returned.
   * 
   * @param <T> the class type of the elements in the array.
   * @param array the array from which the element at index is retrieved.
   * @param index the index into the array to retrieve the element.
   * @param defaultValue the default value of element type to return in the event that the array index is invalid.
   * @return the element at index from the array or the default value if the index is invalid.
   */
  public static <T> T elementAt(T[] array, int index, T defaultValue) {
    try {
      return array[index];
    }
    catch (ArrayIndexOutOfBoundsException ignore) {
      return defaultValue;
    }
  }

  /**
   * Null safe operation to return a empty Object array if the given Object array reference is null.
   *
   * @param array the Object array being evaluated for a null reference.
   * @return the given Object array if not null, otherwise return an empty Object array.
   */
  public static Object[] emptyArray(final Object[] array) {
    return (array != null ? array : EMPTY_ARRAY);
  }

  /**
   * Gets the first element from the given array or null if the array reference is null or the array length is 0.
   * 
   * @param <T> the Class type of the elements in the array.
   * @param array the array of elements from which to retrieve the first element.
   * @return the first element from the array or null if either the array reference is null or the array length is 0.
   * @see #getLast(Object[])
   */
  public static <T> T getFirst(final T... array) {
    return (!isEmpty(array) ? array[0] : null);
  }

  /**
   * Gets the last element from the given array or null if the array reference is null or the array length is 0.
   *
   * @param <T> the Class type of the elements in the array.
   * @param array the array of elements from which to retrieve the last element.
   * @return the last element from the array or null if either the array reference is null or the array length is 0.
   * @see #getFirst(Object[])
   */
  public static <T> T getLast(final T... array) {
    return (array != null && array.length > 0 ? array[array.length - 1] : null);
  }

  /**
   * Determines whether an array is empty.  An Object array is considered empty if the Object array reference is null
   * or the array is length 0.
   *
   * @param array the Object array to evaluate.
   * @return a boolean value indicating whether the given array is empty or not.
   */
  public static boolean isEmpty(final Object[] array) {
    return (array == null || array.length == 0);
  }

  /**
   * Converts the specified Object array into a String representation.
   *
   * @param array the Object array of elements to convert to a String.
   * @return a String representation of the Object array.
   */
  public static String toString(final Object... array) {
    StringBuilder buffer = new StringBuilder("[");
    int count = 0;

    if (array != null) {
      for (Object element : array) {
        buffer.append(count++ > 0 ? ", " : StringUtils.EMPTY_STRING).append(element);
      }
    }

    buffer.append("]");

    return buffer.toString();
  }

}
