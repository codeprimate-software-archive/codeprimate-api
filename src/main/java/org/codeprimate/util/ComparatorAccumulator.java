package org.codeprimate.util;

/**
 * The ComparatorAccumulator class is a multi-comparison Comparator.
 *
 * @author John J. Blum
 * @see java.lang.Comparable
 * @see java.util.Comparator
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class ComparatorAccumulator {

  private int compareValue = 0;

  // Sort null values last!
  public <T extends Comparable<T>> int compare(final T value1, final T value2) {
    return (value1 == null ? 1 : (value2 == null ? -1 : value1.compareTo(value2)));
  }

  public <T extends Comparable<T>> ComparatorAccumulator doCompare(final T value1, final T value2) {
    compareValue = (compareValue != 0 ? compareValue : compare(value1, value2));
    return this;
  }

  public int getResult() {
    return compareValue;
  }

}
