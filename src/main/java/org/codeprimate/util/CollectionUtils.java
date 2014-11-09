package org.codeprimate.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codeprimate.lang.Filter;

/**
 * The CollectionUtils class is an abstract utility class for working with the Java Collections framework of classes,
 * data structures and algorithms.
 * 
 * @author John J. Blum
 * @see java.lang.Iterable
 * @see java.util.Arrays
 * @see java.util.Collection
 * @see java.util.Collections
 * @see java.util.Enumeration
 * @see java.util.Iterator
 * @see java.util.List
 * @see java.util.Map
 * @see java.util.Set
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class CollectionUtils {

  /**
   * Returns the specified array as a List of elements.
   * 
   * @param <T> the class type of the elements in the array.
   * @param array the object array of elements to convert to a List.
   * @return a List of elements contained in the specified array.
   * @see java.util.Arrays#asList(Object[])
   */
  public static <T> List<T> asList(final T... array) {
    List<T> arrayList = new ArrayList<T>(array.length);
    Collections.addAll(arrayList, array);
    return arrayList;
  }

  /**
   * Returns the specified array as a Set of elements.
   * 
   * @param <T> the class type of the elements in the array.
   * @param array the object array of elements to convert to a Set.
   * @return a Set of elements contained in the specified array.
   * @see java.util.Arrays#asList(Object[])
   */
  public static <T> Set<T> asSet(final T... array) {
    Set<T> arraySet = new HashSet<T>(array.length);
    Collections.addAll(arraySet, array);
    return arraySet;
  }

  /**
   * Null-safe implementation for method invocations that return a List Collection.  If the returned List is null,
   * then this method will return an empty List in it's place.
   * 
   * @param <T> the class type of the List's elements.
   * @param list the target List to verify as not null.
   * @return the specified List if not null otherwise return an empty List.
   */
  public static <T> List<T> emptyList(final List<T> list) {
    return (list != null ? list : Collections.<T>emptyList());
  }

  /**
   * Null-safe implementation for method invocations that return a Set Collection.  If the returned Set is null,
   * then this method will return an empty Set in it's place.
   * 
   * @param <T> the class type of the Set's elements.
   * @param set the target Set to verify as not null.
   * @return the specified Set if not null otherwise return an empty Set.
   */
  public static <T> Set<T> emptySet(final Set<T> set) {
    return (set != null ? set : Collections.<T>emptySet());
  }

  public static <T> Enumeration<T> enumeration(final Iterator<T> iterator) {
    return new Enumeration<T>() {
      @Override public boolean hasMoreElements() {
        return iterator.hasNext();
      }

      @Override public T nextElement() {
        return iterator.next();
      }
    };
  }

  /**
   * Iterates the Collection and finds all object elements that match the Filter criteria.
   * 
   * @param <T> the class type of the Collection elements.
   * @param collection the Collection of elements to iterate and filter.
   * @param filter the Filter applied to the Collection of elements in search of all matching elements.
   * @return a List of elements from the Collection matching the criteria of the Filter in the order in which they were
   * found.  If no elements match the Filter criteria, then an empty List is returned.
   */
  public static <T> List<T> findAll(final Collection<T> collection, final Filter<T> filter) {
    List<T> matches = new ArrayList<T>(collection.size());

    for (T element : collection) {
      if (filter.accept(element)) {
        matches.add(element);
      }
    }

    return matches;
  }

  /**
   * Iterates the Collection and finds all object elements that match the Filter criteria.
   * 
   * @param <T> the class type of the Collection elements.
   * @param collection the Collection of elements to iterate and filter.
   * @param filter the Filter applied to the Collection of elements in search of the matching element.
   * @return a single element from the Collection that match the criteria of the Filter.  If multiple elements match
   * the Filter criteria, then this method will return the first one.  If no element of the Collection matches
   * the criteria of the Filter, then this method returns null.
   */
  public static <T> T findBy(final Collection<T> collection, final Filter<T> filter) {
    for (T element : collection) {
      if (filter.accept(element)) {
        return element;
      }
    }

    return null;
  }

  /**
   * Removes keys from the Map based on a Filter.
   * 
   * @param <K> the Class type of the key.
   * @param <V> the Class type of the value.
   * @param map the Map from which to remove key-value pairs based on a Filter.
   * @param filter the Filter to apply to the Map entries to ascertain their "value".
   * @return the Map with entries filtered by the specified Filter.
   * @see java.util.Map
   * @see java.util.Map.Entry
   */
  public static <K, V> Map<K, V> filter(final Map<K, V> map, final Filter<Map.Entry<K, V>> filter) {
    for (Iterator<Map.Entry<K, V>> mapEntries = map.entrySet().iterator(); mapEntries.hasNext(); ) {
      if (!filter.accept(mapEntries.next())) {
        mapEntries.remove();
      }
    }

    return map;
  }

  /**
   * Determines if the Collection is empty.  The Collection is considered empty if the reference is null
   * or the Collection is "empty".
   *
   * @param collection the Collection to evaluate.
   * @return a boolean value indicating whether the Collection is empty or not.
   * @see java.util.Collection#isEmpty()
   */
  public static boolean isEmpty(final Collection<?> collection) {
    return (collection == null || collection.isEmpty());
  }

  /**
   * Determines if the Map is empty.  The Map is considered empty if the reference is null or the Map is "empty".
   *
   * @param map the Map to evaluate.
   * @return a boolean value indicating whether the Map is empty or not.
   * @see java.util.Map#isEmpty()
   */
  public static boolean isEmpty(final Map<?, ?> map) {
    return (map == null || map.isEmpty());
  }

  public static <T> Iterable<T> iterable(final Enumeration<T> enumeration) {
    return new Iterable<T>() {
      @Override public Iterator<T> iterator() {
        return CollectionUtils.iterator(enumeration);
      }
    };
  }

  public static <T> Iterable<T> iterable(final Iterator<T> iterator) {
    return new Iterable<T>() {
      @Override public Iterator<T> iterator() {
        return iterator;
      }
    };
  }

  public static <T> Iterator<T> iterator(final Enumeration<T> enumeration) {
    return new Iterator<T>() {
      @Override public boolean hasNext() {
        return enumeration.hasMoreElements();
      }

      @Override public T next() {
        return enumeration.nextElement();
      }

      @Override public void remove() {
        throw new UnsupportedOperationException("Not Supported!");
      }
    };
  }

  /**
   * Removes keys with null values in the Map.
   * 
   * @param map the Map from which to remove null key-value pairs.
   * @return the Map without any null keys or values.
   * @see #filter(java.util.Map, org.codeprimate.lang.Filter)
   * @see java.util.Map
   */
  public static <K, V> Map<K, V> removeKeysWithNullValues(final Map<K, V> map) {
    return filter(map, new Filter<Map.Entry<K, V>>() {
      @Override public boolean accept(final Map.Entry<K, V> entry) {
        return (entry.getValue() != null);
      }
    });
  }

  public static <T> List<T> subList(final List<T> source, final int... indices) {
    List<T> subList = new ArrayList<T>(indices.length);

    for (int index : indices) {
      subList.add(source.get(index));
    }

    return subList;
  }

}
