package org.codeprimate.tools;

/**
 * The GetEnvironmentVariableValue class gets the value(s) for the specified Environment variable(s).
 * 
 * @author John J. Blum
 * @see java.lang.System#getenv(String)
 * @since 1.0.0
 */
public class GetEnvironmentVariableValue {

  protected static String usage() {
    return String.format("> java %1$s environmentVariable [environmentVariable]*",
      GetEnvironmentVariableValue.class.getName());
  }

  public static void main(final String... args) {
    if (args.length == 0) {
      System.out.println(usage());
      System.exit(1);
    }

    for (String arg : args) {
      System.out.printf("%1$s = %2$s%n", arg, System.getenv(arg));
    }
  }

}
