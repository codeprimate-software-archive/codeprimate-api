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

import java.io.File;

import org.codeprimate.lang.BooleanUtils;

/**
 * The GetAbsoluteFileLocation class determines the absolute pathname or location for a set of files.
 * 
 * @author John J. Blum
 * @see java.io.File
 * @since 1.0.0
 */
public class GetAbsoluteFileLocation {

  protected static void printPathInformation(final String[] args) {
    for (String arg : args) {
      File file = new File(arg);
      System.out.printf("File (%1$s) exists (%2$s).%n", file.getAbsolutePath(), BooleanUtils.valueOf(
        file.exists(), "Yes", "No"));
    }
  }

  protected static String usage() {
    return String.format("> java %1$s pathname [pathname]*", GetAbsoluteFileLocation.class.getName());
  }

  public static void main(final String... args) {
    if (args.length < 1) {
      System.out.println(usage());
      System.exit(1);
    }

    printPathInformation(args);
  }

}
