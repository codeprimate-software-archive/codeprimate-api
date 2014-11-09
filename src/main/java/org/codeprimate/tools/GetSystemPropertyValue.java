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
