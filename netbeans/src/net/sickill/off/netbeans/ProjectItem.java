package net.sickill.off.netbeans;

import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectInformation;
import org.netbeans.api.project.ProjectUtils;

/**
 * @author sickill
 */
public class ProjectItem {

  private Project project;

  public ProjectItem(Project p) {
    project = p;
  }

  @Override
  public String toString() {
    ProjectInformation pi = ProjectUtils.getInformation(project);
    return pi.getDisplayName() + " [" + project.getProjectDirectory().getPath() + "]";
  }

  public Project getProject() {
    return project;
  }

}
