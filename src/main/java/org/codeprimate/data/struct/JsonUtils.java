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

package org.codeprimate.data.struct;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * The JsonUtils class is a utility class for working with JSON documents.
 *
 * @author John Blum
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class JsonUtils {

  protected static final String JSON_KEY_VALUE_FORMAT = "\"%1$s\":\"%2$s\"";

  public static String toSimpleJson(final Object bean) {
    try {
      BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());

      StringBuilder builder = new StringBuilder("{ ");

      int count = 0;

      for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
        if (!"class".equals(propertyDescriptor.getName())) {
          Method readMethod = propertyDescriptor.getReadMethod();

          if (readMethod.getParameterTypes().length == 0) {
            builder.append(count++ > 0 ? ", " : "");
            builder.append(String.format(JSON_KEY_VALUE_FORMAT, propertyDescriptor.getName(),
              readMethod.invoke(bean)));
          }
        }
      }

      builder.append(" }");

      return builder.toString();
    }
    catch (Exception e) {
      throw new IllegalArgumentException(String.format("Failed to convert object (%1$s) to JSON", bean), e);
    }
  }

}
