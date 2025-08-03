package org.codeprimate.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.codeprimate.io.IOUtils;

/**
 * The DependencyGraphAnalyzer class...
 *
 * @author John Blum
 * @since 1.0.0
 */
@SuppressWarnings("unused")
// TODO refactor! For example, introduce a Dependency class modeling a Maven Dependency with groupId, artifactId, version, etc.
public class DependencyGraphAnalyzer {

  private static final Map<String, Map<String, Set<String>>> groupArtifactVersionGraph = new TreeMap<>();

  private static void parseDependencyGraph(final String dependencyGraphPathname) throws IOException {
    parseDependencyGraph(new File(dependencyGraphPathname));
  }

  private static void parseDependencyGraph(final File dependencyGraph) throws IOException {
    BufferedReader fileReader = null;

    try {
      fileReader = new BufferedReader(new FileReader(dependencyGraph));

      String line;

      while ((line = fileReader.readLine()) != null) {
        int index = findIndexOfGroupId(line);
        if (index > -1) {
          line = line.substring(index).trim();
          parseDependency(line);
        }
      }
    }
    finally {
      IOUtils.close(fileReader);
    }
  }

  private static void parseDependency(final String line) {
    String[] groupArtifactPackageVersionScope = line.split(":");

/*
    assert groupArtifactPackageVersionScope.length == 5 :
      String.format("Expected dependency of format: groupId:artifactId:packaging:version:scope; but was [%1$s]",
        Arrays.toString(groupArtifactPackageVersionScope));
*/

    String groupId = groupArtifactPackageVersionScope[0];
    String artifactId = groupArtifactPackageVersionScope[1];
    String version = groupArtifactPackageVersionScope[2];
    //String version = groupArtifactPackageVersionScope[3];

    Map<String, Set<String>> artifactVersionGraph = groupArtifactVersionGraph.get(groupId);

    if (artifactVersionGraph == null) {
      artifactVersionGraph = new TreeMap<>();
      artifactVersionGraph.put(artifactId, new HashSet<>());
      groupArtifactVersionGraph.put(groupId, artifactVersionGraph);
    }

    Set<String> versionSet = artifactVersionGraph.get(artifactId);

    if (versionSet == null) {
      versionSet = new HashSet<>();
      artifactVersionGraph.put(artifactId, versionSet);
    }

    versionSet.add(version.trim());
  }

  private static int findIndexOfGroupId(final String value) {
    char[] valueCharArray = value.toCharArray();

    for (int index = 0; index < valueCharArray.length; index++) {
      if (Character.isAlphabetic(valueCharArray[index])) {
        return index;
      }
    }

    return -1;
  }

  private static void printDependencyGraph() {
    printDependencyGraph(false);
  }

  private static void printDependencyGraph(final boolean duplicatesOnly) {
    for (Entry<String, Map<String, Set<String>>> groups : groupArtifactVersionGraph.entrySet()) {
      String groupId = groups.getKey();

      for (Entry<String, Set<String>> artifacts : groups.getValue().entrySet()) {
        String artifactId = artifacts.getKey();

        if (!duplicatesOnly || artifacts.getValue().size() > 1) {
          for (String version : artifacts.getValue()) {
            System.out.printf("%1$s:%2$s:%3$s%n", groupId, artifactId, version);
          }
        }
      }
    }
  }

  public static void main(final String[] args) throws Exception {
    if (args.length == 0) {
      System.err.println("usage: $java org.codeprimate.tools.DependencyGraphAnalyzer file1 [, file2 [, file3] ... ]");
      System.exit(-1);
    }

    for (String arg : args) {
      parseDependencyGraph(arg);
    }

    printDependencyGraph(true);
  }

}
