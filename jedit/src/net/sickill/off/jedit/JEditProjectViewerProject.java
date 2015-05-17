package net.sickill.off.jedit;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sickill.off.common.AbstractProject;
import net.sickill.off.common.OffListModel;
import net.sickill.off.common.ProjectFile;

import org.gjt.sp.jedit.EBComponent;
import org.gjt.sp.jedit.EBMessage;
import org.gjt.sp.jedit.EditBus;
import org.gjt.sp.jedit.View;

import projectviewer.ProjectViewer;
import projectviewer.event.ViewerUpdate;
import projectviewer.event.ProjectUpdate;
import projectviewer.vpt.VPTFile;
import projectviewer.vpt.VPTGroup;
import projectviewer.vpt.VPTProject;

/**
 *
 * @author kill
 */
public class JEditProjectViewerProject extends AbstractProject implements EBComponent {
    View view;
    VPTProject project;
    static final Logger logger = Logger.getLogger(JEditProjectViewerProject.class.getName());

    JEditProjectViewerProject(View view) {
        this.view = view;
        EditBus.addToBus(this);
    }

    public void init(OffListModel model) {
        super.init(model);
        fetchProjectFiles(ProjectViewer.getActiveProject(view));
    }

    public String getProjectRootPath() {
        if (project != null) {
            return project.getRootPath() + "/";
        }
        return null;
    }

    private synchronized void fetchProjectFiles(VPTProject p) {
        if (project != null && project.equals(p)) {
            logger.info("reindexProject: selected project is the same as already indexed project, leaving");
            return;
        }
        project = p;
        new Thread(new ImportWorker()).start();
    }

    class ImportWorker implements Runnable {
        public void run() {
            model.clear();
            if (project != null) {
                logger.log(Level.INFO, "reindexProject: indexing files from project {0}", project.getName());
                model.setIndexing(true);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(JEditProjectViewerProject.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (Object node : project.getOpenableNodes()) {
                    ProjectFile pf = new JEditProjectViewerFile(JEditProjectViewerProject.this, (VPTFile)node);
                    model.addFile(pf);
                }
                model.setIndexing(false);
                model.refilter();
            } else {
                logger.info("reindexProject: no project selected");
            }
        }

    }

    public void groupActivated(VPTGroup g) {
        fetchProjectFiles(null);
    }

    public void projectLoaded(VPTProject project) {
        fetchProjectFiles(project);
    }

    public void filesAdded(VPTProject p, Collection<VPTFile> files) {
        if (files != null) {
            for (VPTFile file : files) {
                model.addFile(new JEditProjectViewerFile(this, file));
            }
        }
    }

    public void filesRemoved(VPTProject p, Collection<VPTFile> files) {
        if (files != null) {
            for (VPTFile file : files) {
                model.removeFile(file);
            }
        }
    }

    public void handleMessage(EBMessage message) {
        String className = message.getClass().getCanonicalName();
        if (className.endsWith("ViewerUpdate")) {
            ViewerUpdate msg = (ViewerUpdate)message;
            switch(msg.getType()) {
            case PROJECT_LOADED:
                projectLoaded((VPTProject)msg.getNode());
                break;
            case GROUP_ACTIVATED:
                groupActivated((VPTGroup)msg.getNode());
                break;
            }
        } else if (className.endsWith("ProjectUpdate")) {
            ProjectUpdate msg = (ProjectUpdate)message;
            VPTProject project = msg.getProject();
            switch(msg.getType()) {
            case FILES_CHANGED:
                filesAdded(project, msg.getAddedFiles());
                filesRemoved(project, msg.getRemovedFiles());
                break;
            case PROPERTIES_CHANGED:
                break;
            }
        }
    }
}
