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
import java.util.Set;

import org.codeprimate.lang.Assert;

/**
 * The PropertyUtils class is an abstract utility class for working with Properties.
 *
 * @author John J. Blum
 * @see java.util.Map
 * @see java.util.Map.Entry
 * @see java.util.Properties
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class PropertyUtils {

  public static Properties createProperties(final Map<String, ?> map) {
    Properties properties = new Properties();

    if (!CollectionUtils.isEmpty(map)) {
      for (Entry<String, ?> entry : map.entrySet()) {
        properties.setProperty(entry.getKey(), String.valueOf(entry.getValue()));
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

  public static PropertiesWrapper with(final Properties properties) {
    return new PropertiesWrapper(properties);
  }

  public static class PropertiesWrapper {

    private Properties properties;

    public PropertiesWrapper(final Properties properties) {
      Assert.notNull(properties, "Properties backing this wrapper must not be null");
      this.properties = properties;
    }

    protected Properties getProperties() {
      return properties;
    }

    public Properties asProperties() {
      return getProperties();
    }

    @Override
    @SuppressWarnings("all")
    public PropertiesWrapper clone() {
      this.properties = (Properties) this.properties.clone();
      return this;
    }

    public PropertiesWrapper removeAll(final Object... propertyNames) {
      for (Object propertyName : propertyNames) {
        getProperties().remove(propertyName);
      }

      return this;
    }

    public PropertiesWrapper removeAll(final Properties target) {
      Set<String> thesePropertyNames = getProperties().stringPropertyNames();
      thesePropertyNames.retainAll(target.stringPropertyNames());
      return removeAll(thesePropertyNames.toArray());
    }

    public PropertiesWrapper retainAll(final Properties target) {
      Set<String> thesePropertyNames = getProperties().stringPropertyNames();
      thesePropertyNames.removeAll(target.stringPropertyNames());
      return removeAll(thesePropertyNames.toArray());
    }
  }

}
