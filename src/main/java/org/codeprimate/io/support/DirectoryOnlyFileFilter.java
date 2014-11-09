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

package org.codeprimate.io.support;

import java.io.File;
import java.io.FileFilter;

import org.codeprimate.io.FileUtils;

/**
 * The DirectoryOnlyFileFilter class is a FileFilter filtering (accepting) File objects representing only directories.
 *
 * @author John Blum
 * @see java.io.File
 * @see java.io.FileFilter
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class DirectoryOnlyFileFilter implements FileFilter {

  @Override
  public boolean accept(final File pathname) {
    return FileUtils.isDirectory(pathname);
  }
}
