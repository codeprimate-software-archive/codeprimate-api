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
 * The Initializer class is an abstract utility class that identifies Initable objects and initializes them
 * by calling their init method.
 *
 * @author John J. Blum
 * @see org.codeprimate.lang.Initable
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class Initializer {

  /**
   * Initializes the specified Object by calling it's init method if and only if the Object implements the
   * Initable interface.
   *
   * @param obj the Object to be initialized.
   * @return true if the Object was successfully initialized using it's init method; false otherwise.
   * @see org.codeprimate.lang.Initable#init()
   */
  public static boolean init(final Object obj) {
    if (obj instanceof Initable) {
      ((Initable) obj).init();
      return true;
    }

    return false;
  }

}
