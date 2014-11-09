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
 * The Initable interface defines a contract for implementing classes who's object instances can be initialized after
 * construction.
 *
 * @author John J. Blum
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface Initable {

  /**
   * Determines whether this Object has been initialized.
   *
   * @return a boolean value indicating whether this Object was initialized.
   */
  boolean isInitialized();

  /**
   * Called to perform additional initialization logic after construction of the Object instance.  This is necessary
   * in certain cases in order to prevent escape of the "this" reference during construction by subclasses needing to
   * instantiate other collaborators or to start of additional services, like Threads, and so on.
   */
  void init();

}
