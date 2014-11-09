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
 * The BooleanUtils class is an abstract utility class for interacting with Boolean objects.
 *
 * @author John J. Blum
 * @see java.lang.Boolean
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class BooleanUtils {

  public static String valueOf(final Boolean value, final String trueValue, final String falseValue) {
    return (Boolean.TRUE.equals(value) ? trueValue : falseValue);
  }

}
