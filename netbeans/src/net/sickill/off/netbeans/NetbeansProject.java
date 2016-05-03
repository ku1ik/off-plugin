package net.sickill.off.netbeans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;
import net.sickill.off.common.*;
import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.SourceGroup;
import org.netbeans.api.queries.SharabilityQuery;
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
 * @author sickill
 */
public class NetbeansProject extends AbstractProject
        implements FileChangeListener {

    private static NetbeansProject instance;
    private static final Logger logger = Logger.getLogger(NetbeansProject.class.getName());
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
        // select and index a project, when loading
        Project project = getCurrentProject();
        setSelectedProject(project);
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
        for (FileObject fObj : lookup.lookupAll(FileObject.class)) {
            Project p = FileOwnerQuery.getOwner(fObj);

            if (p != null) {
                return p;
            }
        }

        return null;
    }

    class ImportWorker implements Runnable {

        private boolean running = false;
        private boolean shouldRestart = false;

        public void start() {
            if (model == null) {
                return;
            }

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
                if (firstRun) {
                    logger.info("[OFF] ImportWorker started...");
                    firstRun = false;
                } else {
                    logger.info("[OFF] ImportWorker restarted...");
                }
                model.clear();
                if (selectedProject == null) {
                    logger.info("[OFF] no project selected");
                } else {
                    // During initialization, selectedProject may be an instance of LazyProject.
                    // This will break the group.contains(child) check in collectFiles() because
                    // the owner of the files will be an instance of J2SEProject instead.
                    // We try to detect this here and update the selectedProject reference.
                    {
                        FileObject projectDir = selectedProject.getProjectDirectory();
                        Project owner = FileOwnerQuery.getOwner(projectDir);
                        if (owner != null && owner != selectedProject) {
                            selectedProject = owner;
                        }
                    }
                    final Collection<SourceGroup> allSourceGroups = SourceGroups.getAllSourceGroups(selectedProject);
                    Collection<String> groups = getAsString(allSourceGroups);
                    logger.info("[OFF] SourceGroups: " + groups);
                    model.reinit(groups);
                    //start indexing at source group roots
                    for (SourceGroup allSourceGroup : allSourceGroups) {
                        FileObject rootFolder = allSourceGroup.getRootFolder();
                        collectFiles(rootFolder);
                    }
                }
            } while (shouldRestart);
            model.setIndexing(false);
            model.refilter();
            logger.info("[OFF] ImportWorker finished.");
            setRunning(false);
        }

        private void collectFiles(FileObject dir) {
            watchDirectory(dir);
            final VisibilityQuery query = VisibilityQuery.getDefault();
            FileObject[] children = dir.getChildren();

            for (FileObject child : children) {
                if (!child.isValid()) {
                    continue;
                }
                //ignore target/ build directories
                if (SharabilityQuery.getSharability(child) == SharabilityQuery.Sharability.NOT_SHARABLE) {
                    continue;
                }
                if (!query.isVisible(child)) {
                    continue;
                }
                {
                    if (child.isFolder()) {
                        collectFiles(child);
                    } else if (child.isData()) {
                        model.addFile(new NetbeansProjectFile(child));
                    }
                }
            }
        }

        private Collection<String> getAsString(Collection<SourceGroup> sourceGroups) {
            Collection<String> result = new ArrayList<>();
            for (SourceGroup sourceGroup : sourceGroups) {
                result.add(sourceGroup.getRootFolder().getPath());
            }
            return result;
        }

    }

    private void watchDirectory(FileObject dir) {
        dir.removeFileChangeListener(this);
        dir.addFileChangeListener(this);
    }

    @Override
    public void fileFolderCreated(FileEvent fe) {
        logger.info("fileFolderCreated");
        watchDirectory(fe.getFile());
    }

    @Override
    public void fileDataCreated(FileEvent fe) {
        logger.info("fileDataCreated");
        Collection<SourceGroup> groups = SourceGroups.getAllSourceGroups(selectedProject);
        for (SourceGroup group : groups) {

            if (group.contains(fe.getFile())) {
                model.addFile(new NetbeansProjectFile(fe.getFile()));
            }
        }
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
        fileDeleted(fe);
        fileDataCreated(fe);
    }

    @Override
    public void fileAttributeChanged(FileAttributeEvent fe) {
    }

}
