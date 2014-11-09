package org.codeprimate.util.zip;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.codeprimate.io.FileUtils;
import org.codeprimate.io.IOUtils;
import org.codeprimate.lang.Assert;
import org.codeprimate.util.CollectionUtils;

/**
 * The ZipUtils class is an abstract utility class for working with JAR and ZIP archives.
 *
 * @author John J. Blum
 * @see java.io.File
 * @see java.util.zip.ZipFile
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class ZipUtils {

  /**
   * Unzips the specified ZIP file to the target directory.
   *
   * @param zip the ZIP file to unzip.
   * @param directory a File indicating the directory (path/location) in which to unzip the ZIP file.
   * @throws IOException if the ZIP archive file could not be read or the contents unzipped.
   * @see java.io.File
   * @see java.util.zip.ZipFile
   */
  public static void unzip(final File zip, final File directory) throws IOException {
    Assert.notNull(zip, "The ZIP archive must not be null!");

    Assert.legalArgument(FileUtils.createDirectory(directory), String.format(
      "The file system pathname (%1$s) is not a valid directory!", directory));

    ZipFile zipFile = new ZipFile(zip, ZipFile.OPEN_READ);

    for (ZipEntry entry : CollectionUtils.iterable(zipFile.entries())) {
      if (entry.isDirectory()) {
        Assert.legalState(FileUtils.createDirectory(new File(directory, entry.getName())), String.format(
          "Failed to create directory (%1$s) for ZIP entry!", entry.getName()));
      }
      else {
        DataInputStream entryInputStream = new DataInputStream(zipFile.getInputStream(entry));

        DataOutputStream entryOutputStream = new DataOutputStream(new FileOutputStream(
          new File(directory, entry.getName())));

        try {
          IOUtils.copy(entryInputStream, entryOutputStream);
        }
        finally {
          IOUtils.close(entryInputStream);
          IOUtils.close(entryOutputStream);
        }
      }
    }
  }

}
