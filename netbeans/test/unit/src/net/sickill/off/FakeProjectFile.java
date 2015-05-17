package net.sickill.off;

import net.sickill.off.common.ProjectFile;
import net.sickill.off.common.AbstractProject;
import javax.swing.Icon;

/**
 * @author sickill
 */
class FakeProjectFile extends ProjectFile {

  private String pathInProject;
  private String name;

  public FakeProjectFile(AbstractProject pp, String path) {
    super(pp);
    this.pathInProject = path;
    this.name = path.substring(path.lastIndexOf("/") + 1);
  }

  @Override
  public Icon getIcon() {
    return null;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getFullPath() {
    return FakeProject.PROJECT_ROOT + pathInProject;
  }

  @Override
  public long getSize() {
    return 123456;
  }

  @Override
  public void rename(String newName) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Object getId() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}
