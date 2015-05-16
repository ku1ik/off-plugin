package net.sickill.off.netbeans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import net.sickill.off.common.*;
import java.util.logging.Logger;
import org.netbeans.api.project.FileOwnerQuery;
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
import org.openide.loaders.DataObject;
import org.openide.util.Lookup;
import org.openide.util.Utilities;

/**
 *
 * @author kill
 */
public class NetbeansProject extends AbstractProject implements FileChangeListener, PropertyChangeListener {
    private static NetbeansProject instance;
    private static final Logger logger = Logger.getLogger(NetbeansProject.class.getName());
    private String projectRoot = null;
    private ImportWorker worker = new ImportWorker();
    private Project selectedProject;

    public static NetbeansProject getInstance() {
        if (instance == null) {
            instance = new NetbeansProject();
        }
        return instance;
    }

    @Override
    public void init(OffListModel model) {
        super.init(model);
        OpenProjects.getDefault().addPropertyChangeListener(this);
        getSelectedProject();
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
            Project p = getCurrentProject();
            setSelectedProject(p);
        }
        return selectedProject;
    }

    // see http://netbeans-org.1045718.n5.nabble.com/Lookup-current-active-Project-td3036983.html
    public Project getCurrentProject() {

        Lookup lookup = Utilities.actionsGlobalContext();

        for (Project p : lookup.lookupAll(Project.class)) {
            return p;
        }

        for (DataObject dObj : lookup.lookupAll(DataObject.class)) {
            FileObject fObj = dObj.getPrimaryFile();
            Project p = FileOwnerQuery.getOwner(fObj);
            if (p != null) {
                return p;
            }
        }

        Project p = OpenProjects.getDefault().getMainProject();
        if (p != null) {
            return p;
        }

        for(Project project : OpenProjects.getDefault().getOpenProjects()) {
            return project;
        }

        return null;
    }

    class ImportWorker implements Runnable {
        private boolean running = false;
        private boolean shouldRestart = false;

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
                    // During initialization, selectedProject may be an instance of LazyProject.
                    // This will break the group.contains(child) check in collectFiles() because
                    // the owner of the files will be an instance of J2SEProject instead.
                    // We try to detect this here and update the selectedProject reference.
                    FileObject projectDir = selectedProject.getProjectDirectory();
                    Project owner = FileOwnerQuery.getOwner(projectDir);

                    if (owner != null && owner != selectedProject) {
                      selectedProject = owner;
                      projectDir = selectedProject.getProjectDirectory();
                    }

                    Sources sources = ProjectUtils.getSources(selectedProject);
                    SourceGroup[] groups = sources.getSourceGroups(Sources.TYPE_GENERIC);

                    for (SourceGroup group : groups) {
                      String groupRoot = group.getRootFolder().getPath();
                      final boolean matches = groupRoot.equals(projectDir.getPath());

                      if (projectRoot == null || matches) {
                        projectRoot = groupRoot + "/";
                      }

                      if (matches) {
                        break;
                      }
                    }

                    logger.log(Level.INFO, "[OFF] fetching files from project {0}", projectRoot);

                    for (SourceGroup group : groups) {
                      FileObject folder = group.getRootFolder();

                      logger.log(Level.INFO,
                        "[OFF] found source group: {0} ({1})",
                        new Object[]{ group.getName(), folder.getPath() }
                      );

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
                        model.addFile(new NetbeansProjectFile(NetbeansProject.this, child));
                    }
                }
            }
        }
    }

    private void watchDirectory(FileObject dir) {
        dir.removeFileChangeListener(this);
        dir.addFileChangeListener(this);

    }

    @Override
    public String getProjectRootPath() {
        return projectRoot;
    }

    @Override
    public void fileFolderCreated(FileEvent fe) {
        logger.info("fileFolderCreated");
        watchDirectory(fe.getFile());
    }

    @Override
    public void fileDataCreated(FileEvent fe) {
        logger.info("fileDataCreated");
        model.addFile(new NetbeansProjectFile(this, fe.getFile()));
    }

    @Override
    public void fileChanged(FileEvent fe) {
        logger.info("fileChanged: ignoring internal file changes");
    }

    @Override
    public void fileDeleted(FileEvent fe) {
        logger.info("fileDeleted");
        model.removeFile(fe.getFile());
    }

    @Override
    public void fileRenamed(FileRenameEvent fe) {
        logger.info("fileRenamed");
        model.renameFile(fe.getFile(), fe.getName() + "." + fe.getExt());
    }

    @Override
    public void fileAttributeChanged(FileAttributeEvent fe) {

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(OpenProjects.PROPERTY_MAIN_PROJECT)) {
            logger.info("main project changed");
            selectedProject = null;
            getSelectedProject();
        }
    }

}
