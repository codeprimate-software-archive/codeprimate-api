package org.codeprimate.util;

/**
 * The ResourceNotFoundException class is a RuntimeException indicating that a resource could not be found.
 *
 * @author John J. Blum
 * @see java.lang.RuntimeException
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException() {
  }

  public ResourceNotFoundException(final String message) {
    super(message);
  }

  public ResourceNotFoundException(final Throwable cause) {
    super(cause);
  }

  public ResourceNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
