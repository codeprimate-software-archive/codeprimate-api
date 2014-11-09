package org.codeprimate.process.support;

/**
 * The PidUnavailableException class is a RuntimeException indicating that the current process ID cannot be determined.
 *
 * @author John J. Blum
 * @see java.lang.RuntimeException
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class PidUnavailableException extends RuntimeException {

  public PidUnavailableException() {
  }

  public PidUnavailableException(final String message) {
    super(message);
  }

  public PidUnavailableException(final Throwable cause) {
    super(cause);
  }

  public PidUnavailableException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
