package org.codeprimate.lang;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The ObjectUtils class is an abstract utility class for interacting with Objects.
 *
 * @author John J. Blum
 * @see java.lang.Object
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class ObjectUtils {

  public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

  /**
   * Gets the first non-null value in an array of values.  If the array is null, then null is returned, otherwise the
   * first non-null array element is returned.  If the array is not null and all the array elements are null, then null
   * is still returned.
   * 
   * @param <T> Class type parameter specifying the array element type.
   * @param values the array of values being iterated over in search of the first non-null value.
   * @return the first non-null value from the array of values, otherwise return null if either the array or all
   * the elements in the array are null.
   */
  public static <T> T defaultIfNull(T... values) {
    if (values != null) {
      for (T value : values) {
        if (value != null) {
          return value;
        }
      }
    }

    return null;
  }

  /**
   * Determines whether 2 Objects are equal in value by ignoring nulls.  If both Object references are null, then they
   * are considered equal, or neither must be null and the Objects must be equal in value as determined by their nullSafeEquals
   * method.
   *
   * @param obj1 the first Object in the equality comparison.
   * @param obj2 the second Object in the equality comparison.
   * @return a boolean value indicating whether the 2 Objects are equal in value.  If both Object references are null,
   * then they are considered equal.
   * @see java.lang.Object#equals(Object)
   */
  public static boolean equalsIgnoreNull(final Object obj1, final Object obj2) {
    return (obj1 == null ? obj2 == null : obj1.equals(obj2));
  }

  /**
   * Determines whether 2 Objects are equal in value.  The Objects are equal if and only if neither are null
   * and are equal according to the nullSafeEquals method of the Object's class type.
   *
   * @param obj1 the first Object in the equality comparison.
   * @param obj2 the second Object in the equality comparison.
   * @return a boolean value indicating whether the 2 Objects are equal in value.
   * @see java.lang.Object#equals(Object)
   */
  public static boolean nullSafeEquals(final Object obj1, final Object obj2) {
    return (obj1 != null && obj1.equals(obj2));
  }

  /**
   * A null-safe computation of the specified Object's hash value.  If the Object reference is null, then this method
   * returns 0 and will be consistent with the equalsIgnoreNull equality comparison.
   * 
   * @param obj the Object who's hash value will be computed.
   * @return an integer signifying the hash value of the Object or 0 if the Object reference is null.
   * @see java.lang.Object#hashCode()
   */
  public static int hashCode(final Object obj) {
    return (obj == null ? 0 : obj.hashCode());
  }

  /**
   * Null-safe implementation of the Object.toString method.
   * 
   * @param obj the Object on which to call toString.
   * @return the String representation of the specified Object or null if the Object reference is null.
   * @see java.lang.Object#toString()
   */
  public static String toString(final Object obj) {
    return (obj == null ? null : obj.toString());
  }

  /**
   * Gets the Class types of all arguments in the Object array.
   * 
   * @param args the Object array of arguments to determine the Class types for.
   * @return a Class array containing the Class types of each argument in the arguments Object array.
   * @see #invoke(Object, String, Object...)
   */
  static Class[] getArgumentTypes(final Object... args) {
    Class[] argTypes = null;

    if (args != null) {
      argTypes = new Class[args.length];
      for (int index = 0, size = args.length; index < size; index++) {
        argTypes[index] = ClassUtils.getClass(args[index]);
      }
    }

    return argTypes;
  }

  /**
   * Builds the signature of a method based on the method name and the parameter types.
   *
   * @param methodName a String indicating the name of the method.
   * @param parameterTypes an array of Class objects indicating the type of each method parameter.
   * @return a String describing the signature of the the method.
   * @see java.lang.Class
   */
  static String getMethodSignature(final String methodName, final Class<?>[] parameterTypes) {
    StringBuilder methodSignature = new StringBuilder(methodName);

    methodSignature.append("(");

    if (parameterTypes != null) {
      int index = 0;
      for (Class parameterType : parameterTypes) {
        methodSignature.append(index++ > 0 ? ", :" : ":");
        methodSignature.append(ClassUtils.getSimpleName(parameterType));
      }
    }

    methodSignature.append(")");

    return methodSignature.toString();
  }

  /**
   * Invokes a method by name on the specified Object using Java Reflection.
   * 
   * @param obj the Object in which to invoke the method on.
   * @param methodName a String value indication the name of the method to invoke.
   * @param <T> a generic type parameter for the method return value.
   * @return a value of the method invocation on Object cast to the generized type.
   * @see #invoke(Object, String, Class[], Object...)
   */
  public static <T> T invoke (final Object obj, final String methodName) {
    return invoke(obj, methodName, (Class<?>[]) null, (Object[]) null);
  }

  /**
   * Invokes a method by name on the specified Object using Java Reflection.
   * 
   * @param obj the Object in which to invoke the method on.
   * @param methodName a String value indication the name of the method to invoke.
   * @param arguments the Object arguments to the method based on its parameters.
   * @param <T> a generic type parameter for the method return value.
   * @return a value of the method invocation on Object cast to the generized type.
   * @see #getArgumentTypes(Object...)
   * @see #invoke(Object, String, Class[], Object...)
   */
  public static <T> T invoke(final Object obj, final String methodName, final Object... arguments) {
    return invoke(obj, methodName, getArgumentTypes(arguments), arguments);
  }

  /**
   * Invokes a method by name on the specified Object using Java Reflection.
   * 
   * @param <T> a generic type parameter for the method return value.
   * @param obj the Object in which to invoke the method on.
   * @param methodName a String value indication the name of the method to invoke.
   * @param parameterTypes the Class types of the method parameters indicating the exact method to invoke
   * (parameters in number, order and type) if the method is overloaded.
   * @param arguments the Object arguments to the method based on its parameters.
   * @return the value of the method invocation on the object cast to the generic type.
   * @see java.lang.Class#getMethod(String, Class[])
   * @see java.lang.reflect.Method#invoke(Object, Object...)
   */
  @SuppressWarnings("unchecked")
  public static <T> T invoke(final Object obj, final String methodName, final Class<?>[] parameterTypes, final Object... arguments) {
    Assert.notNull(obj, String.format("The object on which to invoke the method (%1$s) cannot be null!", methodName));

    Assert.notNull(methodName, String.format("The name of the method to invoke on object of type (%1$s) cannot be null",
      obj.getClass().getName()));

    try {
      Method method = obj.getClass().getMethod(methodName, parameterTypes);
      method.setAccessible(true);
      return (T) method.invoke(obj, arguments);
    }
    catch (NoSuchMethodException e) {
      throw new RuntimeException(String.format("Method (%1$s) does not exist on Object of type (%2$s)!",
        getMethodSignature(methodName, parameterTypes), obj.getClass().getName()), e);
    }
    catch (IllegalAccessException e) {
      throw new RuntimeException(String.format("The method (%1$s) on Object of type (%2$s) is not accessible!",
        getMethodSignature(methodName, parameterTypes), obj.getClass().getName()), e);
    }
    catch (InvocationTargetException e) {
      throw new RuntimeException(String.format("The invocation of method (%1$s) on Object of type (%2$s) failed!",
        getMethodSignature(methodName, parameterTypes), obj.getClass().getName()), e);
    }
  }

}
