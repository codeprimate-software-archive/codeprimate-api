package org.codeprimate.lang;

/**
 * The Filter interface defines a contract for implementing objects that filter other objects.
 *
 * @author John J. Blum
 * @since 1.0.0
 */
public interface Filter<T> {

  /**
   * Determines whether the specified object satisfies the criteria of this Filter.
   *
   * @param obj the Object being filtered.
   * @return a boolean value indicating whether the Object satisfies the criteria of this Filter.
   */
  boolean accept(T obj);

}
