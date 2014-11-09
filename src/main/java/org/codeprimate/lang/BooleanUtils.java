package org.codeprimate.lang;

/**
 * The BooleanUtils class is an abstract utility class for interacting with Boolean objects.
 *
 * @author John J. Blum
 * @see java.lang.Boolean
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class BooleanUtils {

  public static String valueOf(final Boolean value, final String trueValue, final String falseValue) {
    return (Boolean.TRUE.equals(value) ? trueValue : falseValue);
  }

}
