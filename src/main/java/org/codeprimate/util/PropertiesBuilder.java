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

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

/**
 * The PropertiesBuilder class...
 *
 * @author John Blum
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class PropertiesBuilder {

  private final Properties properties;

  public PropertiesBuilder() {
    properties = new Properties();
  }

  public PropertiesBuilder(final Properties defaults) {
    properties = new Properties(defaults);
  }

  protected Properties getProperties() {
    return properties;
  }

  public PropertiesBuilder setProperty(final String propertyName, final String propertyValue) {
    properties.setProperty(propertyName, propertyValue);
    return this;
  }

  public PropertiesBuilder load(final InputStream in) throws IOException {
    getProperties().load(in);
    return this;
  }

  public PropertiesBuilder load(final Reader reader) throws IOException {
    getProperties().load(reader);
    return this;
  }

  public PropertiesBuilder loadFromXML(final InputStream in) throws IOException {
    getProperties().loadFromXML(in);
    return this;
  }

  public Properties build() {
    return getProperties();
  }

}
