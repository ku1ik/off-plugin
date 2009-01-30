/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.netbeans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import javax.swing.event.ChangeEvent;
import net.sickill.off.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.netbeans.spi.project.support.ProjectOperations;
import org.openide.filesystems.FileAttributeEvent;
import org.openide.filesystems.FileChangeListener;
import org.openide.filesystems.FileEvent;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileRenameEvent;

/**
 *
 * @author kill
 */
public class NetbeansProjectProvider implements ProjectProvider, ChangeListener, FileChangeListener, PropertyChangeListener {
    private static NetbeansProjectProvider instance;
    private static Collection<ProjectFile> projectFiles;
    private Logger logger;

    static NetbeansProjectProvider getInstance() {
        if (instance == null) {
            instance = new NetbeansProjectProvider();
        }
        return instance;
    }

    public NetbeansProjectProvider() {
        logger = Logger.getLogger(this.getClass().getName());
    }

    public void fetchProjectFiles() {
        // ensure we have listener registered
        OpenProjects.getDefault().removePropertyChangeListener(this);
        OpenProjects.getDefault().addPropertyChangeListener(this);

        Project p = OpenProjects.getDefault().getMainProject();
        if (p == null) {
            logger.info("[OFF] no main project selected");
            System.out.println("[OFF] no main project selected");
            projectFiles = new ArrayList<ProjectFile>();
            return;
        }
        logger.info("[OFF] fetching files from project " + p.getProjectDirectory().getPath());
        System.out.println("[OFF] fetching files from project " + p.getProjectDirectory().getPath());

//        Sources s = ProjectUtils.getSources(p);
        //s.addChangeListener(this);
//        SourceGroup[] groups = s.getSourceGroups(Sources.TYPE_GENERIC);
//        for (SourceGroup group : groups) {
//            logger.info("group: " + group.getName() + " | " + group.getRootFolder().getPath());
//            group.getRootFolder().addFileChangeListener(this);
//        }
        List<FileObject> srcFolders = ProjectOperations.getDataFiles(p);
//        ArrayList<ProjectFile> projectFiles = new ArrayList<ProjectFile>();
        HashMap<String, ProjectFile> projectFilesHash = new HashMap<String, ProjectFile>();

        Pattern mask = NetbeansSettings.getInstance().getIgnoreMaskCompiled();

        for (FileObject folder : srcFolders) {
            folder.removeFileChangeListener(this);
            folder.addFileChangeListener(this);
            Enumeration<? extends FileObject> children = folder.getChildren(true);
            while (children.hasMoreElements()) {
                FileObject fo = children.nextElement();
                if (fo.isFolder()) {
                    fo.removeFileChangeListener(this);
                    fo.addFileChangeListener(this);
                } else {
//                    projectFiles.add(new NetbeansProjectFile(this, fo));
                    ProjectFile pf = new NetbeansProjectFile(this, fo);
                    String fullPath = pf.getFullPath();
                    if ((mask == null || !mask.matcher(pf.getPathInProject().toLowerCase()).matches()) && !projectFilesHash.containsKey(fullPath)) {
                        projectFilesHash.put(fullPath, pf);
                    }
                }
            }
        }
        projectFiles = projectFilesHash.values();
    }

    public Collection<ProjectFile> getProjectFiles() {
        if (projectFiles == null) {
            fetchProjectFiles();
        }
        return projectFiles;
    }

    public String getProjectRootPath() {
        return OpenProjects.getDefault().getMainProject().getProjectDirectory().getPath();
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
        logger.info("fileChanged");
        fetchProjectFiles();
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
        logger.info("fileAttributeChanged");
        fetchProjectFiles();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(OpenProjects.PROPERTY_MAIN_PROJECT)) {
            logger.info("main project changed");
            fetchProjectFiles();
        }
    }
}
