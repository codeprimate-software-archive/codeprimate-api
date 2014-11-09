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
 * The Ordered interface defines a contract for implementing classes who's instances must participate in some type
 * of ordered data structure, such as an array or List, or exist in a context where order relative to other
 * object instances matter.
 * 
 * @author John J. Blum
 * @see org.codeprimate.lang.Orderable
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface Ordered {

  /**
   * Gets the index of this Object in the ordered Collection.
   *
   * @return an integer value indicating the index of this Object in the ordered Collection.
   */
  int getIndex();

  /**
   * Sets the index of this Object in the ordered Collection.
   *
   * @param index an integer value indicating the index of this Object in the ordered Collection.
   */
  void setIndex(int index);

}
