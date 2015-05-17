package net.sickill.off.common;

import java.nio.file.Path;
import java.util.regex.Matcher;
import javax.swing.Icon;

/**
 * @author sickill
 */
public class OffListElement {

  String filename;
  Path path;
  private final ProjectFile file;
  Matcher matcher;
  int priority = 0;
  boolean withPath;

  public OffListElement(Matcher matcher, ProjectFile file, boolean withPath) {
    this.matcher = matcher;
    this.file = file;
    this.withPath = withPath;
  }

  public ProjectFile getFile() {
    return file;
  }

  private void buildFilename() {
    filename = withPath ? file.getName() : Highlighter.highlight(file.getName(), matcher);
  }

  public String getFilename() {
    if (filename == null) {
      buildFilename();
    }

    return "<html>" + filename + "</html>";
  }

  public Path getPath() {
    return file.getDirectory();
  }

  public Icon getIcon() {
    return file.getIcon();
  }

  int getFilterDistance() {
    int dist = 0;

    for (int i = 1; i < matcher.groupCount(); i++) {
      dist += matcher.start(i + 1) - matcher.start(i) - 1;
    }

    return dist;
  }

  void setPriority(int priority) {
    this.priority = priority;
  }

  int getPriority() {
    return this.priority;
  }

  @Override
  public String toString() {
    return getFilename();
  }

}
