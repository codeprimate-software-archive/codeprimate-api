package org.codeprimate.lang;

/**
 * The NumberUtils class is an abstract utility class for working with Java Number types
 * (e.g. Integer, Long, Double, etc).
 *
 * @author John J. Blum
 * @see java.lang.Number
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class NumberUtils {

  public static int intValue(final Integer value) {
    return (value != null ? value : 0);
  }

}
