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

/**
 * The ComparatorAccumulator class is a multi-comparison Comparator.
 *
 * @author John J. Blum
 * @see java.lang.Comparable
 * @see java.util.Comparator
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class ComparatorAccumulator {

  private int compareValue = 0;

  // Sort null values last!
  public <T extends Comparable<T>> int compare(final T value1, final T value2) {
    return (value1 == null ? 1 : (value2 == null ? -1 : value1.compareTo(value2)));
  }

  public <T extends Comparable<T>> ComparatorAccumulator doCompare(final T value1, final T value2) {
    compareValue = (compareValue != 0 ? compareValue : compare(value1, value2));
    return this;
  }

  public int getResult() {
    return compareValue;
  }

}
