package org.codeprimate.lang;

/**
 * The Initable interface defines a contract for implementing classes who's object instances can be initialized after
 * construction.
 *
 * @author John J. Blum
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface Initable {

  /**
   * Determines whether this Object has been initialized.
   *
   * @return a boolean value indicating whether this Object was initialized.
   */
  boolean isInitialized();

  /**
   * Called to perform additional initialization logic after construction of the Object instance.  This is necessary
   * in certain cases in order to prevent escape of the "this" reference during construction by subclasses needing to
   * instantiate other collaborators or to start of additional services, like Threads, and so on.
   */
  void init();

}
