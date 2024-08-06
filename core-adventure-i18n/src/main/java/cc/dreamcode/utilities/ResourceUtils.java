package cc.dreamcode.utilities;

import static java.util.Spliterator.ORDERED;
import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.Collectors.toUnmodifiableSet;
import static java.util.stream.StreamSupport.stream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResourceUtils {

  private static final String BLANK = "";

  public static List<File> unpackResources(
      File file,
      File dataFile,
      Collection<String> paths,
      boolean expelExtensions,
      Collection<String> fileExtensions) {
    List<File> resources = new ArrayList<>();
    for (String path : paths) {
      resources.addAll(
          unpackResources(file, dataFile, path, BLANK, BLANK, expelExtensions, fileExtensions));
    }
    return resources;
  }

  public static List<File> unpackResources(
      File file,
      File dataFile,
      String path,
      String prefix,
      String suffix,
      boolean expelExtensions,
      Collection<String> fileExtensions) {
    List<File> resources = new ArrayList<>();
    try (JarFile jarFile = new JarFile(file)) {
      Set<JarEntry> entries = getUnmodifiableSet(jarFile.entries().asIterator());
      for (JarEntry entry : entries) {
        String entryName = entry.getName();
        if (!isExpectedPath(entryName, path)) {
          continue;
        }

        String fileName = entryName.substring(path.length() + 1);
        if (shouldSkipFile(fileName, prefix, suffix, expelExtensions, fileExtensions)) {
          continue;
        }

        File resourceFile = new File(dataFile, entryName);
        if (!resourceFile.exists()) {
          createNecessaryFilesAndDirectories(dataFile, resourceFile);
          try (FileOutputStream outputStream = new FileOutputStream(resourceFile)) {
            jarFile.getInputStream(entry).transferTo(outputStream);
          }
        }

        resources.add(resourceFile);
      }

      return resources;
    } catch (IOException exception) {
      throw new IllegalStateException(
          "Could not search or create resources, due to: " + exception.getMessage(), exception);
    }
  }

  private static boolean shouldSkipFile(
      String fileName,
      String prefix,
      String suffix,
      boolean expelExtensions,
      Collection<String> fileExtensions) {
    boolean startsWithPrefix = fileName.startsWith(prefix);
    boolean endsWithSuffix = fileName.endsWith(suffix);
    boolean extensionCheck =
        expelExtensions
            ? fileExtensions.stream().anyMatch(fileName::contains)
            : fileExtensions.stream().noneMatch(fileName::contains);
    return startsWithPrefix && endsWithSuffix && extensionCheck;
  }

  private static boolean isExpectedPath(String entryName, String path) {
    return entryName.startsWith(path + '/') && !entryName.endsWith("/");
  }

  private static boolean isExpectedFile(
      String fileName, String prefix, String suffix, Set<String> omittedFileNames) {
    return fileName.startsWith(prefix)
        && fileName.endsWith(suffix)
        && omittedFileNames.stream().noneMatch(fileName::contains);
  }

  private static void createNecessaryFilesAndDirectories(File dataFile, File resourceFile)
      throws IOException {
    if (!dataFile.exists() && !dataFile.mkdirs()) {
      throw new IllegalStateException(
          "Could not create data directory for resource file: " + dataFile.getPath());
    }

    File parentFile = resourceFile.getParentFile();
    if (!parentFile.exists() && !parentFile.mkdirs()) {
      throw new IllegalStateException(
          "Could not create parent directory for resource file: " + resourceFile.getPath());
    }

    if (!resourceFile.createNewFile()) {
      throw new IllegalStateException("Could not create resource file: " + resourceFile.getPath());
    }
  }

  private static <T> Set<T> getUnmodifiableSet(Iterator<T> iterator) {
    return stream(spliteratorUnknownSize(iterator, ORDERED), false).collect(toUnmodifiableSet());
  }
}
