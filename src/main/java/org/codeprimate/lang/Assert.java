package org.codeprimate.lang;

/**
 * The Assert class is an abstract utility class for making assertions (validations).
 *
 * @author John J. Blum
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class Assert {

  public static void legalArgument(final boolean valid, final String message) {
    if (!valid) {
      throw new IllegalArgumentException(message);
    }
  }

  public static void legalState(final boolean state, final String message) {
    if (!state) {
      throw new IllegalStateException(message);
    }
  }

  public static void notNull(final Object obj, final String message) {
    if (obj == null) {
      throw new NullPointerException(message);
    }
  }

}
