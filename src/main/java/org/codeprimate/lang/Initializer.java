package org.codeprimate.lang;

/**
 * The Initializer class is an abstract utility class that identifies Initable objects and initializes them
 * by calling their init method.
 *
 * @author John J. Blum
 * @see org.codeprimate.lang.Initable
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class Initializer {

  /**
   * Initializes the specified Object by calling it's init method if and only if the Object implements the
   * Initable interface.
   *
   * @param initableObj the Object to be initialized.
   * @return true if the Object was successfully initialized using it's init method; false otherwise.
   * @see org.codeprimate.lang.Initable#init()
   */
  public static boolean init(final Object initableObj) {
    if (initableObj instanceof Initable) {
      ((Initable) initableObj).init();
      return true;
    }

    return false;
  }

}
