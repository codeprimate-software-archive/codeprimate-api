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

import static org.junit.Assert.*;

import java.lang.management.ManagementFactory;

import org.junit.Test;

/**
 * The SystemUtilsTest class is a test suite of test cases for testing the contract and functionality of the SystemUtils
 * class.
 *
 * @author John J. Blum
 * @see org.codeprimate.lang.SystemUtils
 * @see org.junit.Test
 * @since 1.0.0
 */
public class SystemUtilsTest {

  @Test
  public void isJavaVersionAtLeastJava8() {
    assertTrue(String.format("Expected Java version (1.8.0_65); but was (%1$s)", System.getProperty("java.version")),
      SystemUtils.isJavaVersionAtLeast("1.8.0_65"));
  }

  @Test
  public void isHotSpotVM() {
    final boolean expected = ManagementFactory.getRuntimeMXBean().getVmName().contains(SystemUtils.ORACLE_HOTSPOT_JVM_NAME);
    assertEquals(expected, SystemUtils.isHotSpotJVM());
  }

  @Test
  public void isJ9VM() {
    final boolean expected = ManagementFactory.getRuntimeMXBean().getVmName().contains(SystemUtils.IBM_J9_JVM_NAME);
    assertEquals(expected, SystemUtils.isJ9JVM());
  }

  @Test
  public void isJRockitVM() {
    final boolean expected = ManagementFactory.getRuntimeMXBean().getVmName().contains(SystemUtils.ORACLE_JROCKIT_JVM_NAME);
    assertEquals(expected, SystemUtils.isJRockitJVM());
  }

  @Test
  public void isLinux() {
    final boolean expected = ManagementFactory.getOperatingSystemMXBean().getName().contains(SystemUtils.LINUX_OS_NAME);
    assertEquals(expected, SystemUtils.isLinux());
  }
  @Test
  public void isMacOSX() {
    final boolean expected = ManagementFactory.getOperatingSystemMXBean().getName().contains(SystemUtils.MAC_OSX_NAME);
    assertEquals(expected, SystemUtils.isMacOSX());
  }

  @Test
  public void isWindows() throws Exception {
    final boolean expected = ManagementFactory.getOperatingSystemMXBean().getName().contains(SystemUtils.WINDOWS_OS_NAME);
    assertEquals(expected, SystemUtils.isWindows());
  }

}
