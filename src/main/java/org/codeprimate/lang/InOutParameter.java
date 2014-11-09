package org.codeprimate.lang;

/**
 * The InOutParameter class is a utility class for creating methods with in/out parameters.  This class is a wrapper 
 * around the value it encapsulates.  In essence, an instance of this class is the same thing as the value itself, 
 * as determined by the equals method and so this class serves as value holder.
 * 
 * @author John J. Blum
 * @param <T> the expected Class type of the parameter's value.
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class InOutParameter<T> {

  private T value;

  /**
   * Default constructor creating an instance of InOutParameter with a null initial value.
   */
  public InOutParameter() {
  }

  /**
   * Constructs an instance of InOutParameter with the specified value.
   * 
   * @param value the initial value of this parameter.
   */
  public InOutParameter(final T value) {
    this.value = value;
  }

  /**
   * Gets the value of this in/out parameter.
   * 
   * @return the value of this in/out parameter.
   */
  public T getValue() {
    return value;
  }

  /**
   * Sets the value of this in/out parameter.
   * 
   * @param value the Object value to set this in/out parameter to.
   */
  public void setValue(final T value) {
    this.value = value;
  }

  /**
   * Determines whether the in/out parameter value is equal in value to the specified Object.  Note, this is not
   * typically how an nullSafeEquals method should be coded, but then this is not your typical class either!
   * 
   * @param obj the Object value being compared for equality with this in/out parameter value.
   * @return boolean value indicating whether this in/out parameter value is equal to the specified Object.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof InOutParameter) {
      obj = ((InOutParameter) obj).getValue();
    }

    return ((obj == value) || ObjectUtils.nullSafeEquals(value, obj));
  }

  /**
   * Computes the hash value of this in/out parameter value.
   * 
   * @return an integer value constituting the computed hash value of this in/out parameter.
   */
  @Override
  public int hashCode() {
    return (value == null ? 0 : value.hashCode());
  }

  /**
   * Gets the String representation of this in/out parameter value.
   * 
   * @return a String value representing the value of this in/out parameter.
   */
  @Override
  public String toString() {
    return String.valueOf(value);
  }

}