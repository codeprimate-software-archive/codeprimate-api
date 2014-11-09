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

package org.codeprimate.tools;

/**
 * The GetSystemPropertyValue class gets the value(s) for the specified System property(ies).
 * 
 * @author John J. Blum
 * @see java.lang.System#getProperty(String)
 * @see java.lang.System#getProperties()
 * @since 1.0.0
 */
public class GetSystemPropertyValue {

  protected static String usage() {
    return String.format("> java %1$s systemProperty [systemProperty]*", GetSystemPropertyValue.class.getName());
  }

  public static void main(final String... args) {
    if (args.length == 0) {
      System.out.println(usage());
      System.exit(1);
    }

    for (String arg : args) {
      System.out.printf("%1$s = %2$s%n", arg, System.getProperty(arg));
    }
  }

}
