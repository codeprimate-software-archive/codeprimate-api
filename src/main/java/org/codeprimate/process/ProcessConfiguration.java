package org.codeprimate.process;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codeprimate.io.FileUtils;
import org.codeprimate.lang.Assert;
import org.codeprimate.lang.StringUtils;
import org.codeprimate.util.CollectionUtils;

/**
 * The ProcessConfiguration class is a container encapsulating configuration and context meta-data
 * for a running process.
 *
 * @author John Blum
 * @see java.lang.Process
 * @see java.lang.ProcessBuilder
 * @since 1.5.0
 */
@SuppressWarnings("unused")
public class ProcessConfiguration {

  private final boolean redirectingErrorStream;

  private final File workingDirectory;

  private final List<String> command;

  private final Map<String, String> environment;

  public static ProcessConfiguration create(final ProcessBuilder processBuilder) {
    Assert.notNull(processBuilder, "The ProcessBuilder used to construct, configure and start the Process must not be null!");

    return new ProcessConfiguration(processBuilder.command(), processBuilder.environment(), processBuilder.directory(),
      processBuilder.redirectErrorStream());
  }

  public ProcessConfiguration(final List<String> command,
                              final Map<String, String> environment,
                              final File workingDirectory,
                              final boolean redirectingErrorStream)
  {
    Assert.legalArgument(!CollectionUtils.isEmpty(command), "The command used to run the process must be specified!");

    Assert.legalArgument(FileUtils.isDirectory(workingDirectory), String.format(
      "The process working directory (%1$s) is not valid!", workingDirectory));

    this.command = new ArrayList<String>(command);
    this.workingDirectory = workingDirectory;
    this.redirectingErrorStream = redirectingErrorStream;

    this.environment = (environment != null
      ? Collections.unmodifiableMap(new HashMap<String, String>(environment))
      : Collections.<String, String>emptyMap());
  }

  public List<String> getCommand() {
    return Collections.unmodifiableList(command);
  }

  public String getCommandString() {
    return StringUtils.concat(getCommand().toArray(), StringUtils.SINGLE_SPACE);
  }

  public Map<String, String> getEnvironment() {
    return environment;
  }

  public boolean isRedirectingErrorStream() {
    return redirectingErrorStream;
  }

  public File getWorkingDirectory() {
    return workingDirectory;
  }

  @Override
  public String toString() {
    return "{ command = ".concat(getCommandString())
      .concat(", workingDirectory = ".concat(getWorkingDirectory().getAbsolutePath()))
      .concat(", redirectingErrorStream = ".concat(String.valueOf(isRedirectingErrorStream())))
      .concat(", environment = ".concat(String.valueOf(getEnvironment())))
      .concat(" }");
  }

}
