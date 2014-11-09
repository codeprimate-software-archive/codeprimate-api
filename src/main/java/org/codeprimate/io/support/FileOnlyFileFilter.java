package org.codeprimate.io.support;

import java.io.File;
import java.io.FileFilter;

import org.codeprimate.io.FileUtils;

/**
 * The FileOnlyFileFilter class is a FileFilter filtering (accepting) File objects representing only actual files.
 *
 * @author John Blum
 * @see java.io.File
 * @see java.io.FileFilter
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class FileOnlyFileFilter implements FileFilter {

  @Override
  public boolean accept(final File pathname) {
    return FileUtils.isFile(pathname);
  }

}
