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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.codeprimate.lang.Assert;
import org.codeprimate.lang.StringUtils;

/**
 * The FileUtils class is an abstract utility class for working with files, directories and the file system.
 *
 * @author John J. Blum
 * @see java.io.File
 * @see org.codeprimate.io.IOUtils
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class FileUtils extends IOUtils {

  public static boolean createDirectory(final File path) {
    return (path != null && (path.isDirectory() || path.mkdirs()));
  }

  public static File createFile(final String pathname) {
    return new File(pathname);
  }

  /**
   * Determines the file extension of the specified File.
   *
   * @param file a File indicating the file system object.
   * @return a String value indicating the extension of the specified file.
   * @see java.io.File#getName()
   * @throws java.lang.NullPointerException if the File object reference is null.
   * @throws java.lang.IllegalArgumentException if the File object is not an actual file.
   */
  public static String getFileExtension(final File file) {
    Assert.notNull(file, "The File must not be null!");
    Assert.legalArgument(file.isFile(), "The File must be a file!");

    String filename = file.getName();
    int dotIndex = filename.lastIndexOf(".");

    return (dotIndex > -1 ? filename.substring(dotIndex + 1) : null);
  }

  /**
   * Extracts the filename from the pathname of a file system resource (file).
   *
   * @param pathname a String indicating the path, or location of the file system resource.
   * @return a String value containing only the filename of the file system resource (file).
   */
  public static String getFilename(final String pathname) {
    String filename = pathname;

    if (StringUtils.hasText(filename)) {
      int separatorIndex = filename.lastIndexOf(File.separator);
      filename = (separatorIndex != -1 ? filename.substring(separatorIndex + 1) : filename);
    }

    return filename;
  }

  public static boolean exists(final File file) {
    return (file != null && file.exists());
  }

  /**
   * Determines whether the path represented by name exists in the file system of the localhost.
   *
   * @param pathname a String indicating the name of the path.
   * @return a boolean indicating whether the path represented by name (pathname) actually exists in the file system
   * of the localhost (system).
   * @see java.io.File#exists()
   */
  public static boolean exists(final String pathname) {
    return (StringUtils.hasText(pathname) && exists(new File(pathname)));
  }

  public static boolean isDirectory(final File file) {
    return (file != null && file.isDirectory());
  }

  public static boolean isFile(final File file) {
    return (file != null && file.isFile());
  }

  public static String read(final File file) throws IOException {
    Assert.legalArgument(isFile(file), String.format(
      "The File reference (%1$s) from which to read the contents is not a valid file!", file));

    assert file != null;

    BufferedReader fileReader = new BufferedReader(new FileReader(file));

    try {
      StringBuilder buffer = new StringBuilder();

      for (String line = fileReader.readLine(); line != null; line = fileReader.readLine()) {
        buffer.append(line);
        buffer.append(StringUtils.LINE_SEPARATOR);
      }

      return buffer.toString().trim();
    }
    finally {
      close(fileReader);
    }
  }

  /**
   * This method attempts to get the canonical form of the specified file otherwise returns it's absolute form.
   *
   * @param file the java.io.File object who's canonical representation is attempted to be returned.
   * @return the canonical form of the specified File or the absolute form if an IOException occurs during the
   * File.getCanonicalFile call.
   * @see java.io.File#getCanonicalFile()
   * @see java.io.File#getAbsoluteFile()
   */
  public static File tryGetCanonicalFileElseGetAbsoluteFile(final File file) {
    try {
      return file.getCanonicalFile();
    }
    catch (IOException e) {
      return file.getAbsoluteFile();
    }
  }

  /**
   * This method attempts to get the canonical path of the specified file otherwise returns it's absolute path.
   *
   * @param file the java.io.File object who's canonical path is attempted to be returned.
   * @return the canonical path of the specified File or the absolute path if an IOException occurs during the
   * File.getCanonicalPath call.
   * @see java.io.File#getCanonicalPath()
   * @see java.io.File#getAbsolutePath()
   */
  public static String tryGetCanonicalPathElseGetAbsolutePath(final File file) {
    try {
      return file.getCanonicalPath();
    }
    catch (IOException e) {
      return file.getAbsolutePath();
    }
  }

  /**
   * Verifies that the specified pathname is valid and actually exists in the file system on localhost.  The pathname
   * is considered valid if it is not null, empty or blank and exists in the file system as a file path (which could
   * represent a file or a directory).
   *
   * @param pathname a String indicating the file path in the file system on localhost.
   * @return the pathname if valid and it exits.
   * @throws java.io.FileNotFoundException if the pathname is invalid or does not exist in the file system on localhost.
   * @see #exists(String)
   * @see java.io.File#exists()
   */
  public static String verifyExists(final String pathname) throws FileNotFoundException {
    if (exists(pathname)) {
      return pathname;
    }

    throw new FileNotFoundException(String.format("Pathname (%1$s) could not be found!", pathname));
  }

  public static void write(final File file, final String contents) throws IOException {
    Assert.notNull(file, "The File to write to must not be null!");
    Assert.legalArgument(StringUtils.hasText(contents), "The 'contents' of the file cannot be null or empty!");

    BufferedWriter fileWriter = null;

    try {
      fileWriter = new BufferedWriter(new FileWriter(file));
      fileWriter.write(contents);
      fileWriter.flush();
    }
    finally {
      IOUtils.close(fileWriter);
    }
  }

}
