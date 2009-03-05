/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.netbeans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.event.ChangeEvent;
import net.sickill.off.*;
import java.util.Enumeration;
import java.util.logging.Logger;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.filesystems.FileAttributeEvent;
import org.openide.filesystems.FileChangeListener;
import org.openide.filesystems.FileEvent;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileRenameEvent;

/**
 *
 * @author kill
 */
public class NetbeansProject implements AbstractProject, ChangeListener, FileChangeListener, PropertyChangeListener {
    private static NetbeansProject instance;
    private Logger logger;
    private OffListModel model;
    private String projectRoot;

    static NetbeansProject getInstance() {
        if (instance == null) {
            instance = new NetbeansProject();
        }
        return instance;
    }

    public NetbeansProject() {
        logger = Logger.getLogger(this.getClass().getName());
    }

    public void init(OffListModel m) {
        model = m;
        fetchProjectFiles();
    }

    public void fetchProjectFiles() {
        model.clear();

        // ensure we have listener registered
        OpenProjects.getDefault().removePropertyChangeListener(this);
        OpenProjects.getDefault().addPropertyChangeListener(this);

        Project p = OpenProjects.getDefault().getMainProject();
        if (p == null) {
            logger.info("[OFF] no main project selected");
            return;
        }

        projectRoot = p.getProjectDirectory().getPath() + "/";
        logger.info("[OFF] fetching files from project " + projectRoot);

        Sources s = ProjectUtils.getSources(p);
        //s.addChangeListener(this);
        SourceGroup[] groups = s.getSourceGroups(Sources.TYPE_GENERIC);

        for (SourceGroup group : groups) {
            FileObject folder = group.getRootFolder();
            logger.info("[OFF] found source group: " + group.getName() + " (" + folder.getPath() + ")");
            folder.removeFileChangeListener(this);
            folder.addFileChangeListener(this);
            Enumeration<? extends FileObject> children = folder.getChildren(true);
            while (children.hasMoreElements()) {
                FileObject fo = children.nextElement();
                if (fo.isValid() && group.contains(fo)) { // && VisibilityQuery.getDefault().isVisible(child)
                    if (fo.isFolder()) {
                        fo.removeFileChangeListener(this);
                        fo.addFileChangeListener(this);
                    } else {
                        model.addFile(new NetbeansProjectFile(this, fo));
                    }
                }
            }
        }
        model.refresh();
    }

    public String getProjectRootPath() {
        return projectRoot;
    }

    public void stateChanged(ChangeEvent e) {
        logger.info("source groups changed");
        fetchProjectFiles();
    }

    public void fileFolderCreated(FileEvent fe) {
        logger.info("fileFolderCreated");
        fetchProjectFiles();
    }

    public void fileDataCreated(FileEvent fe) {
        logger.info("fileDataCreated");
        fetchProjectFiles();
    }

    public void fileChanged(FileEvent fe) {
        logger.info("fileChanged: ignoring internal file changes");
    }

    public void fileDeleted(FileEvent fe) {
        logger.info("fileDeleted");
        fetchProjectFiles();
    }

    public void fileRenamed(FileRenameEvent fe) {
        logger.info("fileRenamed");
        fetchProjectFiles();
    }

    public void fileAttributeChanged(FileAttributeEvent fe) {
        logger.info("fileAttributeChanged: ignoring");
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(OpenProjects.PROPERTY_MAIN_PROJECT)) {
            logger.info("main project changed");
            fetchProjectFiles();
        }
    }

}
