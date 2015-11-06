/*
 * Copyright 2014-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codeprimate.lang;

/**
 * The StringUtils class is an abstract utility class for interacting with String literals.
 *
 * @author John J. Blum
 * @see java.lang.Character
 * @see java.lang.String
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class StringUtils {

  public static final String COMMA_DELIMITER = ",";
  public static final String COMMA_SPACE_DELIMITER = ", ";
  public static final String EMPTY_STRING = "";
  public static final String LINE_SEPARATOR = System.getProperty("line.separator");
  public static final String SINGLE_SPACE = " ";
  public static final String UTF_8 = "UTF-8";

  public static final char[] EMPTY_CHAR_ARRAY = new char[0];

  public static final String[] EMPTY_STRING_ARRAY = new String[0];

  public static final String[] SPACES = {
    "",
    " ",
    "  ",
    "   ",
    "    ",
    "     ",
    "      ",
    "       ",
    "        ",
    "         ",
    "          "
  };

  /**
   * Concatenates all Objects in the array into a single String by calling toString on the Object.
   * 
   * @param values the Object elements of the array to be concatenated into the String.
   * @return a single String with all the individual Objects in the array concatenated.
   * @see #concat(Object[], String)
   */
  public static String concat(final Object... values) {
    return concat(values, EMPTY_STRING);
  }

  /**
   * Concatenates all Objects in the array into a single String using the Object's toString method, delimited by the
   * specified delimiter.
   * 
   * @param values an array of Objects to concatenate into a single String value.
   * @param delimiter the String value to use as a separator between the individual Object values.  If delimiter is
   * null, then a empty String is used.
   * @return a single String with all the individual Objects of the array concatenated together, separated by the
   * specified delimiter.
   * @see java.lang.Object#toString()
   * @see java.lang.StringBuilder
   */
  public static String concat(final Object[] values, String delimiter) {
    StringBuilder buffer = new StringBuilder();

    if (values != null) {
      delimiter = ObjectUtils.defaultIfNull(delimiter, EMPTY_STRING);
      for (Object value : values) {
        buffer.append(buffer.length() > 0 ? delimiter : EMPTY_STRING);
        buffer.append(value);
      }
    }

    return buffer.toString();
  }

  /**
   * Returns the first non-null, non-empty and non-blank String value in the array of String values.
   * 
   * @param values an array of String values, usually consisting of the preferred value followed by default values
   * if any value in the array of String values is null, empty or blank.
   * @return the first non-null, non-empty and non-blank String value in the array of Strings.  If all values are
   * either null, empty or blank then null is returned.
   * @see #isBlank(String)
   */
  public static String defaultIfBlank(final String... values) {
    if (values != null) {
      for (String value : values) {
        if (hasText(value)) {
          return value;
        }
      }
    }

    return null;
  }

  /**
   * Returns only the digits (0..9) from the specified String value.
   * 
   * @param value the String value from which to extract digits.
   * @return only the digits from the specified String value.  If the String is null or contains no digits,
   * then this method returns an empty String.
   * @see java.lang.Character#isDigit(char)
   */
  public static String getDigitsOnly(final String value) {
    StringBuilder buffer = new StringBuilder();

    for (char chr : toCharArray(value)) {
      if (Character.isDigit(chr)) {
        buffer.append(chr);
      }
    }

    return buffer.toString();
  }

  /**
   * Returns only the letters (a..zA..Z) from the specified String value.
   * 
   * @param value the String value from which to extract letters.
   * @return only the letters from the specified String value.  If the String is null or contains no letters,
   * then this method returns an empty String.
   * @see java.lang.Character#isLetter(char)
   */
  public static String getLettersOnly(final String value) {
    StringBuilder buffer = new StringBuilder();

    for (char chr : toCharArray(value)) {
      if (Character.isLetter(chr)) {
        buffer.append(chr);
      }
    }

    return buffer.toString();
  }

  /**
   * Gets a number of spaces determined by number.
   * 
   * @param number an integer value indicating the number of spaces to return.
   * @return a String value containing a number of spaces given by number.
   */
  public static String getSpaces(int number) {
    StringBuilder spaces = new StringBuilder(SPACES[Math.min(number, SPACES.length - 1)]);

    do {
      number -= (SPACES.length -1);
      number = Math.max(number, 0);
      spaces.append(SPACES[Math.min(number, SPACES.length - 1)]);
    }
    while (number > 0);

    return spaces.toString();
  }

  public static boolean hasText(final String value) {
    return !isBlank(value);
  }

  /**
   * Determines whether the specified String value is blank, which is true if it is null, an empty String or a String
   * containing only spaces (blanks).
   * 
   * @param value the String value used in the determination for the "blank" check.
   * @return a boolean value indicating whether the specified String is blank.
   * @see #isEmpty(String)
   */
  public static boolean isBlank(final String value) {
    return (value == null || value.trim().isEmpty());
  }

  /**
   * Determines whether the specified String value is empty, which is true if and only if the value is the empty String.
   * 
   * @param value the String value used in the determination of the "empty" check.
   * @return a boolean value indicating if the specified String is empty.
   * @see #isBlank(String)
   */
  public static boolean isEmpty(final String value) {
    return EMPTY_STRING.equals(value);
  }

  /**
   * Pads the specified String value by appending the specified character up to the given length.
   * 
   * @param value the String value to pad by appending 'paddingCharacter' to the end.
   * @param paddingCharacter the character used to pad the end of the String value.
   * @param length an int value indicating the final length of the String value with padding of the 'paddingCharacter'.
   * @return the String value padded with the specified character by appending 'paddingCharacter' to the end of the
   * String value up to the given length.
   * @throws NullPointerException if the String value is null.
   */
  public static String pad(final String value, final char paddingCharacter, final int length) {
    Assert.notNull(value, "The String value to pad cannot be null!");

    StringBuilder buffer = new StringBuilder(value);

    for (int valueLength = value.length(); valueLength < length; valueLength++) {
      buffer.append(paddingCharacter);
    }

    return buffer.toString();
  }

  /**
   * Returns a char array for the specified String value, where each character in the String is a separate element
   * in the char array.
   *
   * @param value the String value to separate into characters.
   * @return a char array for the specified String.
   * @see java.lang.String#toCharArray()
   */
  public static char[] toCharArray(final String value) {
    return (value != null ? value.toCharArray() : EMPTY_CHAR_ARRAY);
  }

  /**
   * A null-safe implementation of the String.toLowerCase method.
   *
   * @param value a String value to convert to lower case.
   * @return a lower case representation of the specified String value.
   * @see java.lang.String#toLowerCase()
   */
  public static String toLowerCase(final String value) {
    return (value == null ? null : value.toLowerCase());
  }

  /**
   * A null-safe implementation of the String.toUpperCase method.
   *
   * @param value a String value to convert to upper case.
   * @return an upper case representation of the specified String value.
   * @see java.lang.String#toUpperCase()
   */
  public static String toUpperCase(final String value) {
    return (value == null ? null : value.toUpperCase());
  }

  /**
   * A method to trim the value of a String and guard against null values.
   * 
   * @param value the String value that will be trimmed if not null.
   * @return null if the String value is null or the trimmed version of the String value if String value is not null.
   * @see java.lang.String#trim()
   */
  public static String trim(final String value) {
    return (value == null ? null : value.trim());
  }

  /**
   * Null-safe implementation of String truncate using substring.  Truncates the specified String value to the specified
   * length.  Returns null if the String value is null.
   * 
   * @param value the String value to truncate.
   * @param length an int value indicating the length to truncate the String value to.
   * @return the String value truncated to specified length, or null if the String value is null.
   * @throws IllegalArgumentException if the value of length is less than 0.
   * @see java.lang.String#substring(int, int)
   */
  public static String truncate(final String value, final int length) {
    if (length < 0) {
      throw new IllegalArgumentException("Length must be greater than equal to 0!");
    }

    return (value == null ? null : value.substring(0, Math.min(value.length(), length)));
  }

  /**
   * Gets the value of the specified Object as a String.  If the Object is null then the first non-null String value
   * from the array of default String values is returned.  If the array of String values is null or all the elements
   * in the default String values array are null, then the value of String.valueOf(value) is returned.
   *
   * @param value the Object who's String representation is being evaluated.
   * @param defaultValues an array of default String values to assess if the specified Object value is null.
   * @return a String representation of the specified Object value or one of the default String values from the array
   * if the Object value is null.  If either the default String array is null or all the elements are null, then
   * the String value of String.valueOf(value) is returned.
   * @see java.lang.String#valueOf(Object)
   */
  @SuppressWarnings("all")
  public static String valueOf(final Object value, final String... defaultValues) {
    if (value != null) {
      return value.toString();
    }
    else {
      if (defaultValues != null) {
        for (String defaultValue : defaultValues) {
          if (defaultValue != null) {
            return defaultValue;
          }
        }
      }

      return String.valueOf(value);
    }
  }

  /**
   * Wraps a line of text to no longer than the specified width, measured by the number of characters in each line,
   * indenting all subsequent lines with the indent.  If the indent is null, then an empty String is used.
   * 
   * @param line a String containing the line of text to wrap.
   * @param widthInCharacters an integer value indicating the width of each line measured by the number of characters.
   * @param indent the String value used to indent all subsequent lines.
   * @return the line of text wrapped.
   * @throws IndexOutOfBoundsException if widthInCharacters is less than 0, or there are no word boundaries within
   * the given width on any given split.
   * @throws NullPointerException if the line of text is null.
   */
  public static String wrap(String line, final int widthInCharacters, String indent) {
    StringBuilder buffer = new StringBuilder();

    int lineCount = 1;
    int spaceIndex;

    // if indent is null, then do not indent the wrapped lines
    indent = valueOf(indent, EMPTY_STRING);

    while (line.length() > widthInCharacters) {
      spaceIndex = line.substring(0, widthInCharacters).lastIndexOf(SINGLE_SPACE);
      buffer.append(lineCount++ > 1 ? indent : EMPTY_STRING);
      // throws IndexOutOfBoundsException if spaceIndex is -1, implying no word boundary was found within
      // the given width; this also avoids the infinite loop
      buffer.append(line.substring(0, spaceIndex));
      buffer.append(LINE_SEPARATOR);
      // possible infinite loop if spaceIndex is -1, see comment above
      line = line.substring(spaceIndex + 1);
    }

    buffer.append(lineCount > 1 ? indent : "");
    buffer.append(line);

    return buffer.toString();
  }

}
