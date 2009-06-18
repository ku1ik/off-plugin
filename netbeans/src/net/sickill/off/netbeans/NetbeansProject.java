/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.netbeans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import net.sickill.off.common.*;
import java.util.logging.Logger;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.project.Sources;
import org.netbeans.api.project.ui.OpenProjects;
import org.netbeans.api.queries.VisibilityQuery;
import org.openide.filesystems.FileAttributeEvent;
import org.openide.filesystems.FileChangeListener;
import org.openide.filesystems.FileEvent;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileRenameEvent;

/**
 *
 * @author kill
 */
public class NetbeansProject extends AbstractProject implements FileChangeListener, PropertyChangeListener {
    private static NetbeansProject instance;
    private Logger logger;
    private String projectRoot;
    private ImportWorker worker;
    private Project selectedProject;

    public static NetbeansProject getInstance() {
        if (instance == null) {
            instance = new NetbeansProject();
        }
        return instance;
    }

    public NetbeansProject() {
        worker = new ImportWorker(this);
        logger = Logger.getLogger(this.getClass().getName());
    }

    public void init(OffListModel model) {
        super.init(model);
        OpenProjects.getDefault().addPropertyChangeListener(this);
        fetchProjectFiles();
    }

    public synchronized void fetchProjectFiles() {
        if (worker.isRunning()) {
            worker.restart();
        } else {
            worker.start();
        }
    }

    public void setSelectedProject(Project selected) {
        selectedProject = selected;
        fetchProjectFiles();
    }

    public Project getSelectedProject() {
        if (selectedProject == null) {
            Project p = OpenProjects.getDefault().getMainProject();
            if (p == null) {
                Project[] projects = OpenProjects.getDefault().getOpenProjects();
                if (projects.length > 0)
                    p = projects[0];
            }
            setSelectedProject(p);
        }
        return selectedProject;
    }

    class ImportWorker implements Runnable {
        private NetbeansProject project;
        private boolean running = false;
        private boolean shouldRestart = false;

        public ImportWorker(NetbeansProject project) {
            this.project = project;
        }

        public void start() {
            if (model == null) { return; }
            setRunning(true);
            Thread t = new Thread(this);
            t.start();
        }

        public boolean isRunning() {
            return running;
        }

        public void setRunning(boolean value) {
            running = value;
        }

        public void restart() {
            shouldRestart = true;
        }

        @Override
        public void run() {
            boolean firstRun = true;
            model.setIndexing(true);
            do {
                shouldRestart = false;
                model.clear();

                if (firstRun) {
                    logger.info("[OFF] ImportWorker started...");
                    firstRun = false;
                } else {
                    logger.info("[OFF] ImportWorker restarted...");
                }

                if (selectedProject == null) {
                    logger.info("[OFF] no main project selected");
                } else {
                    projectRoot = selectedProject.getProjectDirectory().getPath() + "/";
                    logger.info("[OFF] fetching files from project " + projectRoot);

                    Sources s = ProjectUtils.getSources(selectedProject);
//                    s.addChangeListener(this);
                    SourceGroup[] groups = s.getSourceGroups(Sources.TYPE_GENERIC);

                    for (SourceGroup group : groups) {
                        FileObject folder = group.getRootFolder();
                        logger.info("[OFF] found source group: " + group.getName() + " (" + folder.getPath() + ")");
                        collectFiles(group, folder);
                    }
                } 
            } while (shouldRestart);
            model.setIndexing(false);
            model.refilter();
            logger.info("[OFF] ImportWorker finished.");
            setRunning(false);
        }

        private void collectFiles(SourceGroup group, FileObject dir) {
            watchDirectory(dir);
            FileObject[] children = dir.getChildren();
            for (FileObject child : children) {
                if (child.isValid() && group.contains(child) && VisibilityQuery.getDefault().isVisible(child)) {
                    if (child.isFolder()) {
                        collectFiles(group, child);
                    } else if (child.isData()) {
                        model.addFile(new NetbeansProjectFile(project, child));
                    }
                }
            }
        }
    }

    private void watchDirectory(FileObject dir) {
        dir.removeFileChangeListener(this);
        dir.addFileChangeListener(this);

    }

    public String getProjectRootPath() {
        return projectRoot;
    }

//    public void stateChanged(ChangeEvent e) {
//        logger.info("source groups changed");
//        fetchProjectFiles();
//    }

    public void fileFolderCreated(FileEvent fe) {
        logger.info("fileFolderCreated");
        watchDirectory(fe.getFile());
    }

    public void fileDataCreated(FileEvent fe) {
        logger.info("fileDataCreated");
        model.addFile(new NetbeansProjectFile(this, fe.getFile()));
    }

    public void fileChanged(FileEvent fe) {
        logger.info("fileChanged: ignoring internal file changes");
    }

    public void fileDeleted(FileEvent fe) {
        logger.info("fileDeleted");
        model.removeFile(fe.getFile());
    }

    public void fileRenamed(FileRenameEvent fe) {
        logger.info("fileRenamed");
        model.renameFile(fe.getFile(), fe.getName() + "." + fe.getExt());
    }

    public void fileAttributeChanged(FileAttributeEvent fe) {
        logger.info("fileAttributeChanged, ignoring");
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(OpenProjects.PROPERTY_MAIN_PROJECT)) {
            logger.info("main project changed");
            selectedProject = null;
            getSelectedProject();
        }
    }

}
