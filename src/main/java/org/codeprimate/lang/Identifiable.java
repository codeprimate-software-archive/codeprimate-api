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
 * The Identifiable interface defines a contract for classes whose object instances can be uniquely identified with
 * other object instances within the same class type hierarchy.
 *
 * @author John J. Blum
 * @param <T> the Class type of the identifier.
 * @see java.lang.Comparable
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface Identifiable<T extends Comparable<T>> {

  /**
   * Gets the identifier uniquely identifying this Object instance from other Objects of the same Class type.
   *
   * @return an identifier uniquely identifying this Object.
   */
  T getId();

  /**
   * Sets the identifier uniquely identifying this Object instance from other Objects of the same Class type.
   *
   * @param id the identifier uniquely identifying this Object.
   */
  void setId(T id);

}
