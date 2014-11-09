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
