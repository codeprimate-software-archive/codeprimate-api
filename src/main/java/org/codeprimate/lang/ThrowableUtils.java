package org.codeprimate.lang;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * The ThrowableUtils class is an abstract utility class for interacting with Throwable, Exception and Error objects.
 *
 * @author John J. Blum
 * @see java.lang.Error
 * @see java.lang.Exception
 * @see java.lang.Throwable
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class ThrowableUtils {

  public static String toString(final Throwable t) {
    StringWriter writer = new StringWriter();
    t.printStackTrace(new PrintWriter(writer));
    return writer.toString();
  }

}
