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
 * The Filter interface defines a contract for implementing objects that filter other objects.
 *
 * @author John J. Blum
 * @since 1.0.0
 */
public interface Filter<T> {

  /**
   * Determines whether the specified object satisfies the criteria of this Filter.
   *
   * @param obj the Object being filtered.
   * @return a boolean value indicating whether the Object satisfies the criteria of this Filter.
   */
  boolean accept(T obj);

}
