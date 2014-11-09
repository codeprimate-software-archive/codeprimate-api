package org.codeprimate.lang;

/**
 * The Ordered interface defines a contract for implementing classes who's instances must participate in some type
 * of ordered data structure, such as an array or List, or exist in a context where order relative to other
 * object instances matter.
 * 
 * @author John J. Blum
 * @see org.codeprimate.lang.Orderable
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public interface Ordered {

  /**
   * Gets the index of this Object in the ordered Collection.
   *
   * @return an integer value indicating the index of this Object in the ordered Collection.
   */
  int getIndex();

  /**
   * Sets the index of this Object in the ordered Collection.
   *
   * @param index an integer value indicating the index of this Object in the ordered Collection.
   */
  void setIndex(int index);

}
