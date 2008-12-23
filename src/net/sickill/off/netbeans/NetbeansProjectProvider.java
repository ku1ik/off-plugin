/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.netbeans;

import java.util.Collection;
import javax.swing.event.ChangeEvent;
import net.sickill.off.*;
import net.sickill.off.netbeans.NetbeansProjectFile;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.api.project.ui.OpenProjects;
import org.netbeans.spi.project.support.ProjectOperations;
import org.openide.filesystems.FileAttributeEvent;
import org.openide.filesystems.FileChangeListener;
import org.openide.filesystems.FileEvent;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileRenameEvent;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author kill
 */
public class NetbeansProjectProvider implements ProjectProvider, ChangeListener, FileChangeListener {
    private static Collection<ProjectFile> projectFiles;
    private Logger logger;

    public NetbeansProjectProvider() {
        logger = Logger.getLogger(this.getClass().getName());
    }

    public void fetchProjectFiles() {
        Project p = OpenProjects.getDefault().getMainProject();
        logger.info("project: " + p.getProjectDirectory().getPath());
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
                    if (NetbeansSettings.getSettings().ignoreMask !~ fullPath && !projectFilesHash.containsKey(fullPath)) {
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
}
