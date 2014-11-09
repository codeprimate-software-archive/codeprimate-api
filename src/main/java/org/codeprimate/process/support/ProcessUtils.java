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

package org.codeprimate.process.support;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import org.codeprimate.io.FileSystemUtils;
import org.codeprimate.io.FileUtils;
import org.codeprimate.io.IOUtils;
import org.codeprimate.lang.Assert;
import org.codeprimate.lang.StringUtils;

/**
 * The ProcessUtils class is an abstract utility class for controlling, evaluating and interacting with OS processes.
 *
 * @author John J. Blum
 * @see java.lang.Process
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class ProcessUtils {

  protected static final String TERM_TOKEN = "<TERM/>";

  protected static final Logger log = Logger.getLogger(ProcessUtils.class.getName());

  public static int currentProcessId() {
    RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();

    String runtimeMxBeanName = runtimeMxBean.getName();

    Exception cause = null;

    if (StringUtils.hasText(runtimeMxBeanName)) {
      int atSignIndex = runtimeMxBeanName.indexOf('@');

      if (atSignIndex > 0) {
        try {
          return Integer.parseInt(runtimeMxBeanName.substring(0, atSignIndex));
        }
        catch (NumberFormatException e) {
          cause = e;
        }
      }
    }

    throw new PidUnavailableException(String.format("The process ID (PID) is not available (%1$s)!",
      runtimeMxBeanName), cause);
  }

  public static boolean isRunning(final int processId) {
    for (VirtualMachineDescriptor vmDescriptor : VirtualMachine.list()) {
      if (String.valueOf(processId).equals(vmDescriptor.id())) {
        return true;
      }
    }

    return false;
  }

  public static boolean isRunning(final Process process) {
    try {
      if (process != null) {
        process.exitValue();
      }

      return false;
    }
    catch (IllegalThreadStateException ignore) {
      return true;
    }
  }

  public static int findAndReadPid(final File workingDirectory) {
    Assert.legalArgument(FileUtils.isDirectory(workingDirectory), String.format(
      "The file system pathname (%1$s) expected to contain a PID file is not a valid directory!",
        workingDirectory));

    File pidFile = findPidFile(workingDirectory);

    if (pidFile == null) {
      throw new PidUnavailableException(String.format(
        "No PID file was found in working directory (%1$s) or any of it's sub-directories!",
          workingDirectory));
    }

    return readPid(pidFile);
  }

  protected static File findPidFile(final File workingDirectory) {
    Assert.legalArgument(FileUtils.isDirectory(workingDirectory), String.format(
      "The file system pathname (%1$s) is not a valid directory!", workingDirectory));

    for (File file : workingDirectory.listFiles(DirectoryPidFileFilter.INSTANCE)) {
      if (file.isDirectory()) {
        file = findPidFile(file);
      }
      if (PidFileFilter.INSTANCE.accept(file)) {
        return file;
      }
    }

    return null;
  }

  public static int readPid(final File pidFile) {
    Assert.legalArgument(FileUtils.isFile(pidFile), String.format("The PID file (%1$s) does not exist!", pidFile));

    BufferedReader fileReader = null;

    try {
      fileReader = new BufferedReader(new FileReader(pidFile));
      return Integer.parseInt(fileReader.readLine());
    }
    catch (Exception e) {
      throw new PidUnavailableException(String.format("Failed to read process ID (PID) from file (%1$s)!",
        pidFile), e);
    }
    finally {
      IOUtils.close(fileReader);
    }
  }

  public static void registerProcessShutdownHook(final Process process) {
    registerProcessShutdownHook(process, process.toString(), FileSystemUtils.USER_WORKING_DIRECTORY);
  }

  // TODO change method signature to take a ProcessContext parameter instead, encapsulating all necessary process related information...
  public static void registerProcessShutdownHook(final Process process,
                                                 final String processDescriptor,
                                                 final File workingDirectory)
  {
    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
      @Override public void run() {
        System.out.printf("Stopping process (%1$s) running in (%2$s)...", processDescriptor, workingDirectory);

        String message = "STOPPED!";

        if (process != null) {
          process.destroy();

          try {
            process.waitFor();
          }
          catch (InterruptedException ignore) {
            message = "STILL RUNNING?";
          }
        }

        System.out.printf("%1$s%n", message);
      }
    }));
  }

  public static Process startProcess(final String[] command) throws IOException {
    return startProcess(command, FileSystemUtils.USER_WORKING_DIRECTORY);
  }

  public static Process startProcess(final String[] command, final File workingDirectory) throws IOException {
    return new ProcessBuilder(command).directory(workingDirectory).start();
  }

  public static void signalStop(final Process process) throws IOException {
    if (isRunning(process)) {
      OutputStream processOutputStream = process.getOutputStream();
      processOutputStream.write(TERM_TOKEN.concat(StringUtils.LINE_SEPARATOR).getBytes());
      processOutputStream.flush();
    }
  }

  public static void waitForStopSignal() {
    Scanner in = new Scanner(System.in);
    String message;
    while (!TERM_TOKEN.equals(message = in.next())) {
      if (log.isLoggable(Level.FINE)) {
        log.fine(String.format("Received message (%1$s)", message));
      }
    }
  }

  public static void writePid(final File pidFile, final int pid) throws IOException {
    PrintWriter fileWriter = new PrintWriter(new BufferedWriter(new FileWriter(pidFile, false), 16), true);

    try {
      fileWriter.println(pid);
      fileWriter.flush();
    }
    finally {
      IOUtils.close(fileWriter);
    }
  }

  protected static class DirectoryPidFileFilter extends PidFileFilter {

    protected static final DirectoryPidFileFilter INSTANCE = new DirectoryPidFileFilter();

    @Override
    public boolean accept(final File pathname) {
      return (pathname != null && (pathname.isDirectory() || super.accept(pathname)));
    }
  }

  protected static class PidFileFilter implements FileFilter {

    protected static final PidFileFilter INSTANCE = new PidFileFilter();

    @Override
    public boolean accept(final File pathname) {
      return (FileUtils.isFile(pathname) && pathname.getName().endsWith(".pid"));
    }
  }

}
