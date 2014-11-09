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

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * The PropertyUtils class is an abstract utility class for working with Properties.
 *
 * @author John J. Blum
 * @see java.util.Properties
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class PropertyUtils {

  public static Properties createProperties(final Map<String, String> map) {
    Properties properties = new Properties();

    if (!CollectionUtils.isEmpty(map)) {
      for (Entry<String, String> entry : map.entrySet()) {
        properties.setProperty(entry.getKey(), entry.getValue());
      }
    }

    return properties;
  }

  public static String toString(final Properties properties) {
    StringBuilder buffer = new StringBuilder("[");
    int count = 0;

    if (properties != null) {
      for (String propertyName : properties.stringPropertyNames()) {
        buffer.append(count++ > 1 ? ", " : "");
        buffer.append(String.format("%1$s = %2$s", propertyName, properties.getProperty(propertyName)));
      }
    }

    buffer.append("]");

    return buffer.toString();
  }

}
