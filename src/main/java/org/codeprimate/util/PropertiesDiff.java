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

import static org.codeprimate.util.PropertyUtils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.codeprimate.io.FileSystemUtils;
import org.codeprimate.lang.ObjectUtils;

/**
 * The PropertiesDiff class...
 *
 * @author John Blum
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class PropertiesDiff {

  public static void main(final String[] args) throws Exception {
    if (args.length != 2) {
      System.err.printf("$java -cp <CLASSPATH> %1$s /path/to/file1.properties /path/to/file2.properties%n",
        PropertiesDiff.class.getName());
      System.exit(-1);
    }

    new PropertiesDiff().diff(args[0], args[1]);
  }

  public void diff(final String expectedPropertiesFilePathname, final String actualPropertiesFilePathname)
      throws IOException
  {
    diff(FileSystemUtils.createFile(expectedPropertiesFilePathname),
      FileSystemUtils.createFile(actualPropertiesFilePathname));
  }

  public void diff(final File expectedProperties, final File actualProperties) throws IOException {
    Properties expected = new Properties();
    Properties actual = new Properties();

    expected.load(new FileInputStream(expectedProperties));
    actual.load(new FileInputStream(actualProperties));

    diff(expected, actual);
  }

  public void diff(final Properties expected, final Properties actual) {
    if (expected.size() != actual.size()) {
      System.out.printf("Expected contains (%1$d) properties; Actual contains (%2$d) properties%n",
        expected.size(), actual.size());
    }
    else {
      System.out.printf("Both 'expected' and 'actual' contain (%1$d) properties%n", expected.size());
    }

    Set<String> matchingProperties = expected.stringPropertyNames();

    matchingProperties.retainAll(actual.stringPropertyNames());

    if (!matchingProperties.isEmpty()) {
      System.out.printf("Matching properties found...%n");

      for (String propertyName : matchingProperties) {
        String expectedPropertyValue = expected.getProperty(propertyName);
        String actualPropertyValue = actual.getProperty(propertyName);

        if (!ObjectUtils.nullSafeEquals(expectedPropertyValue, actualPropertyValue)) {
          System.err.printf("'%1$s' = expected (%2$s); but was (%3$s)%n", propertyName,
            expectedPropertyValue, actualPropertyValue);
        }
      }
    }

    missingPropertiesDiff(expected, actual, "Properties in 'expected' not in 'actual':", "No missing properties found in 'actual'.");
    missingPropertiesDiff(actual, expected, "Properties in 'actual' not in 'expected':", "No missing properties found in 'expected'.");
  }

  public void missingPropertiesDiff(final Properties expected,
                                    final Properties actual,
                                    final String errorMessage,
                                    final String successMessage)
  {
    Properties missingProperties = with(expected).clone().removeAll(actual).asProperties();

    if (!missingProperties.isEmpty()) {
      System.err.printf("%1$s%n", errorMessage);
      missingProperties.list(System.err);
    }
    else {
      System.out.printf("%1$s%n", successMessage);
    }
  }

}
