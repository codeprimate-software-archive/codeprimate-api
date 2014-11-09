package org.codeprimate.lang;

/**
 * The Identifiable interface defines a contract for classes whose object instances can be uniquely identified with
 * other object instances within the same class type hierarchy.
 *
 * @author John J. Blum
 * @param <T> the Class type of the identifier.
 * @see java.lang.Comparable
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface Identifiable<T extends Comparable<T>> {

  /**
   * Gets the identifier uniquely identifying this Object instance from other Objects of the same Class type.
   *
   * @return an identifier uniquely identifying this Object.
   */
  T getId();

  /**
   * Sets the identifier uniquely identifying this Object instance from other Objects of the same Class type.
   *
   * @param id the identifier uniquely identifying this Object.
   */
  void setId(T id);

}
