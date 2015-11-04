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

package org.codeprimate.io;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codeprimate.io.support.FileOnlyFileFilter;
import org.codeprimate.lang.Assert;
import org.codeprimate.lang.ObjectUtils;
import org.codeprimate.lang.StringUtils;

/**
 * The FileSystemUtils class is an abstract utility class for working with the OS file system.
 *
 * @author John J. Blum
 * @see java.io.File
 * @see org.codeprimate.io.FileUtils
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class FileSystemUtils extends FileUtils {

  public static final File JAVA_HOME = new File(System.getProperty("java.home"));
  public static final File JAVA_EXE = new File(new File(JAVA_HOME, "bin"), "java");

  public static final File USER_HOME_DIRECTORY = new File(System.getProperty("user.home"));
  public static final File USER_WORKING_DIRECTORY = new File(System.getProperty("user.dir"));

  /**
   * Gets a fully qualified path with the path elements appended to the specified based pathname using the
   * File.separator character.  If the base pathname is unspecified (null, empty or blank) then path elements
   * are considered relative to file system root, beginning with File.separator.  If the array of path elements
   * are null, then the base pathname is returned as is.
   *
   * @param basePathname a String value indicating the base pathname.
   * @param pathElements the path elements to append to the base pathname.
   * @return the path elements appended to base pathname.
   * @see java.io.File#separator
   */
  public static String appendToPath(String basePathname, String... pathElements) {
    if (pathElements != null) {
      basePathname = StringUtils.defaultIfBlank(basePathname, File.separator);

      for (String pathElement : pathElements) {
        basePathname += (basePathname.endsWith(File.separator) ? StringUtils.EMPTY_STRING : File.separator);
        basePathname += pathElement;
      }
    }

    return basePathname;
  }

  /**
   * Creates a path with the given path elements delimited with File.separator.
   *
   * @param pathElements an array of Strings constituting elements of the path.
   * @return a fully constructed pathname containing the elements from the given array as path elements separated
   * by File.separator.
   * @see #createPath(String[], String)
   * @see java.io.File#separator
   */
  public static String createPath(String... pathElements) {
    return createPath(pathElements, File.separator);
  }

  /**
   * Creates a path with the given path elements delimited by separator.
   *
   * @param pathElements an array of Strings constituting elements of the path.
   * @param separator a String specifying the path separator.  If the given String is null, then separator defaults to
   * File.separator.
   * @return a fully constructed pathname containing the elements of the path from the given array delimited
   * by the separator.
   * @throws NullPointerException if pathElements is null.
   * @see #createPath(String...)
   * @see java.io.File#separator
   */
  public static String createPath(String[] pathElements, String separator) {
    separator = ObjectUtils.defaultIfNull(separator, File.separator);

    StringBuilder buffer = new StringBuilder();

    for (String pathElement : pathElements) {
      buffer.append(separator).append(pathElement);
    }

    return buffer.toString();
  }

  public static boolean deleteRecursive(final File path) {
    Assert.notNull(path, "The file system path to delete cannot be null!");

    boolean success = true;

    if (isDirectory(path)) {
      for (File file : safeListFiles(path)) {
        success &= deleteRecursive(file);
      }
    }

    return (path.delete() && success);
  }

  public static File getRootRelativeToWorkingDirectoryOrPath(final File path) {
    File localPath = path;

    if (isDirectory(localPath)) {
      while (localPath != null && !USER_WORKING_DIRECTORY.equals(localPath.getParentFile())) {
        localPath = localPath.getParentFile();
      }
    }

    return (localPath != null ? localPath : path);
  }

  public static File[] listFiles(final File directory, FileFilter fileFilter) {
    Assert.legalArgument(isDirectory(directory), String.format("The File (%1$s) does not refer to a valid directory!",
      directory));

    List<File> results = new ArrayList<>();

    fileFilter = (fileFilter != null ? fileFilter : new FileOnlyFileFilter());

    for (File file : safeListFiles(directory, fileFilter)) {
      if (file.isDirectory()) {
        results.addAll(Arrays.asList(listFiles(file, fileFilter)));
      }
      else {
        results.add(file);
      }
    }

    return results.toArray(new File[results.size()]);
  }

  private static File[] safeListFiles(final File directory) {
    return safeListFiles(directory, AllFiles.INSTANCE);
  }

  private static File[] safeListFiles(final File directory, final FileFilter fileFilter) {
    File[] files = (directory != null ? directory.listFiles(fileFilter) : null);
    return (files != null ? files : new File[0]);
  }

  public static final class AllFiles implements FileFilter {

    public static final AllFiles INSTANCE = new AllFiles();

    @Override
    public boolean accept(final File pathname) {
      return true;
    }
  }

}
