package org.codeprimate.process;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.codeprimate.io.FileSystemUtils;
import org.codeprimate.lang.Assert;
import org.codeprimate.lang.StringUtils;

/**
 * The JavaProcessExecutor class is an abstract utility class for launching and running Java processes.
 *
 * @author John J. Blum
 * @see java.lang.Process
 * @see java.lang.ProcessBuilder
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class JavaProcessExecutor {

  protected static final String JAVA_CLASSPATH = System.getProperty("java.class.path");

  public static ProcessWrapper launch(final Class<?> type, final String... args) throws IOException {
    return launch(FileSystemUtils.USER_WORKING_DIRECTORY, type, args);
  }

  public static ProcessWrapper launch(final File workingDirectory, final Class<?> type, final String... args)
    throws IOException
  {
    ProcessBuilder processBuilder = new ProcessBuilder()
      .command(buildCommand(type, args))
      .directory(validateDirectory(workingDirectory))
      .redirectErrorStream(true);

    Process process = processBuilder.start();

    ProcessWrapper processWrapper = new ProcessWrapper(process, ProcessConfiguration.create(processBuilder));

    processWrapper.register(new ProcessInputStreamListener() {
      @Override public void onInput(final String input) {
        System.err.printf("[FORK-OUT] - %1$s%n", input);
      }
    });

    return processWrapper;
  }

  protected static String[] buildCommand(final Class<?> type, final String... args) {
    Assert.notNull(type, "The main Class to launch must not be null!");

    List<String> command = new ArrayList<String>();
    List<String> programArgs = Collections.emptyList();

    command.add(FileSystemUtils.JAVA_EXE.getAbsolutePath());
    command.add("-server");
    command.add("-classpath");
    command.add(JAVA_CLASSPATH);
    command.addAll(getSystemProperties());

    if (args != null) {
      programArgs = new ArrayList<String>(args.length);

      for (String arg : args) {
        if (isJvmOption(arg)) {
          command.add(arg);
        }
        else if (StringUtils.hasText(arg)) {
          programArgs.add(arg);
        }
      }
    }

    command.add(type.getName());
    command.addAll(programArgs);

    return command.toArray(new String[command.size()]);
  }

  protected static Collection<? extends String> getSystemProperties() {
    List<String> systemProperties = new ArrayList<String>();

    for (String property : System.getProperties().stringPropertyNames()) {
      systemProperties.add(String.format("-D%1$s=%2$s", property, System.getProperty(property)));
    }

    return systemProperties;
  }

  protected static boolean isJvmOption(final String option) {
    return (StringUtils.hasText(option) && (option.startsWith("-D") || option.startsWith("-X")));
  }

  protected static File validateDirectory(final File workingDirectory) {
    Assert.legalArgument(FileSystemUtils.createDirectory(workingDirectory), String.format(
      "Working directory (%1$s) could not be found or does not exist!", workingDirectory));

    return workingDirectory;
  }

}
