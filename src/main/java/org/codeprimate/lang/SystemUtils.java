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
 * The SystemUtils class is an abstract utility class interacting with and accessing properties of the Java System
 * class.
 * 
 * @author John J. Blum
 * @see java.lang.System
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class SystemUtils {

  public static final String CURRENT_DIRECTORY = System.getProperty("user.dir");

  // Java Virtual Machine (JVM) Names
  public static final String IBM_J9_JVM_NAME = "J9";
  public static final String ORACLE_HOTSPOT_JVM_NAME = "HotSpot";
  public static final String ORACLE_JROCKIT_JVM_NAME = "JRockit";

  // Java Virtual Machine (JVM) Vendor Names
  public static final String APPLE_JVM_VENDOR = "Apple";
  public static final String IBM_JVM_VENDOR = "IBM";
  public static final String ORACLE_JVM_VENDOR = "Oracle";

  // Operating System Names
  public static final String LINUX_OS_NAME = "Linux";
  public static final String MAC_OSX_NAME = "Mac";
  public static final String WINDOWS_OS_NAME = "Windows";

  /**
   * Utility method to determine whether the installed Java Runtime Environment (JRE) is minimally at the specified,
   * expected version.  Typically, Java versions are of the form "1.6.0_31"...
   *
   * @param expectedVersion an int value specifying the minimum expected version of the Java Runtime.
   * @return a boolean value indicating if the Java Runtime meets the expected version requirement.
   * @see java.lang.System#getProperty(String) with "java.version".
   */
  public static boolean isJavaVersionAtLeast(final String expectedVersion) {
    String actualVersionDigits = StringUtils.getDigitsOnly(System.getProperty("java.version"));

    String expectedVersionDigits = StringUtils.pad(StringUtils.getDigitsOnly(expectedVersion), '0',
      actualVersionDigits.length());

    try {
      return (Long.parseLong(actualVersionDigits) >= Long.parseLong(expectedVersionDigits));
    }
    catch (NumberFormatException ignore) {
      return false;
    }
  }

  /**
   * Utility method to determine whether the Java application process is executing on the Java HotSpot VM.
   * Client or Server VM does not matter.
   *
   * @return a boolean value indicating whether the Java application process is executing on the Java HotSpot VM.
   * @see #isJVM(String)
   */
  public static boolean isHotSpotJVM() {
    return isJVM(ORACLE_HOTSPOT_JVM_NAME);
  }

  /**
   * Utility method to determine whether the Java application process is executing on the IBM J9 VM.
   *
   * @return a boolean value indicating whether the Java application process is executing on the IBM J9 VM.
   * @see #isJVM(String)
   */
  public static boolean isJ9JVM() {
    return isJVM(IBM_J9_JVM_NAME);
  }

  /**
   * Utility method to determine whether the Java application process is executing on the Oracle JRockit VM.
   * Client or Server VM does not matter.
   *
   * @return a boolean value indicating whether the Java application process is executing on the Oracle JRockit VM.
   * @see #isJVM(String)
   */
  public static boolean isJRockitJVM() {
    return isJVM(ORACLE_JROCKIT_JVM_NAME);
  }

  // @see java.lang.System#getProperty(String) with "java.vm.name".
  private static boolean isJVM(final String expectedJvmName) {
    String jvmName = System.getProperty("java.vm.name");
    return (jvmName != null && jvmName.contains(expectedJvmName));
  }

  /**
   * Utility method to determine whether the Java application process is executing on the Apple JVM.
   *
   * @return a boolean value indicating whether the Java application process is executing and running
   * on the Apple JVM.
   * @see #isJvmVendor(String)
   */
  public static boolean isAppleJVM() {
    return isJvmVendor(APPLE_JVM_VENDOR);
  }

  /**
   * Utility method to determine whether the Java application process is executing on the Oracle JVM.
   *
   * @return a boolean value indicating whether the Java application process is executing and running
   * on the Oracle JVM.
   * @see #isJvmVendor(String)
   */
  public static boolean isOracleJVM() {
    return isJvmVendor(ORACLE_JVM_VENDOR);
  }

  // @see java.lang.System#getProperty(String) with 'java.vm.vendor'.
  private static boolean isJvmVendor(final String expectedJvmVendor) {
    String jvmVendor = System.getProperty("java.vm.vendor");
    return (jvmVendor != null && jvmVendor.contains(expectedJvmVendor));
  }

  /**
   * Utility method that determines whether the Java application process is executing in a Linux
   * operating system environment.
   *
   * @return a boolean value indicating whether the Java application process is executing in Linux.
   * @see #isOS(String)
   */
  public static boolean isLinux() {
    return isOS(LINUX_OS_NAME);
  }

  /**
   * Utility method that determines whether the Java application process is executing in a Apple Mac OSX
   * operating system environment.
   *
   * @return a boolean value indicating whether the Java application process is executing in Mac OSX.
   * @see #isOS(String)
   */
  public static boolean isMacOSX() {
    return isOS(MAC_OSX_NAME);
  }

  /**
   * Utility method that determines whether the Java application process is executing in a Microsoft Windows-based
   * operating system environment.
   *
   * @return a boolean value indicating whether the Java application process is executing in Windows.
   * @see #isOS(String)
   */
  public static boolean isWindows() {
    return isOS(WINDOWS_OS_NAME);
  }

  // @see java.lang.System#getProperty(String) with "os.name".
  private static boolean isOS(final String expectedOsName) {
    String osName = System.getProperty("os.name");
    return (osName != null && osName.contains(expectedOsName));
  }

}
