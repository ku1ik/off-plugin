package net.sickill.off;

import net.sickill.off.common.ProjectFile;
import net.sickill.off.common.AbstractProject;
import javax.swing.Icon;
import org.netbeans.api.project.SourceGroup;
import org.openide.filesystems.FileObject;

/**
 * @author sickill
 */
class FakeProjectFile implements ProjectFile {

    private String pathInProject;
    private String name;

    public FakeProjectFile(String path) {
        this.pathInProject = path;
        this.name = path.substring(path.lastIndexOf('/') + 1);
    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public String getFullPath() {
        return FakeProject.PROJECT_ROOT + pathInProject;
    }

}
