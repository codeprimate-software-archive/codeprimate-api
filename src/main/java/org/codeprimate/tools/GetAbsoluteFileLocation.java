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
