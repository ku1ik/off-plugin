package net.sickill.off.common;

import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.Icon;

/**
 * @author sickill
 */
public abstract class ProjectFile {

  protected AbstractProject project;

  public abstract Icon getIcon();

  public abstract String getName();

  public abstract String getFullPath();

  public abstract long getSize();

  public abstract void rename(String newName);

  public abstract Object getId();

  public ProjectFile(AbstractProject pp) {
    this.project = pp;
  }

  public Path getDirectory() {
    Path fullPath = Paths.get(getFullPath());
    Path rootPath = Paths.get(project.getProjectRootPath());

    Path relative = rootPath.relativize(fullPath);
    Path parent = relative.getParent();

    if (parent == null) {
      return Paths.get("");
    }

    return parent;
  }

  public Path getPathInProject() {
    return getDirectory().resolve(getName());
  }

  String getExtension() {
    String name = getName();
    int index = name.lastIndexOf('.');
    return index == -1 ? "" : name.substring(index + 1, name.length());
  }

}
