/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.jedit;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sickill.off.common.AbstractProject;
import net.sickill.off.common.OffListModel;
import net.sickill.off.common.ProjectFile;
import org.gjt.sp.jedit.View;
import projectviewer.ProjectViewer;
import projectviewer.event.ProjectEvent;
import projectviewer.event.ProjectListener;
import projectviewer.event.ProjectViewerEvent;
import projectviewer.event.ProjectViewerListener;
import projectviewer.vpt.VPTFile;
import projectviewer.vpt.VPTProject;

/**
 *
 * @author kill
 */
public class JEditProjectViewerProject extends AbstractProject implements ProjectViewerListener, ProjectListener {
    View view;
    VPTProject project;
    Logger logger = Logger.getLogger(this.getClass().getName());

    JEditProjectViewerProject(View view) {
        this.view = view;
        ProjectViewer.addProjectViewerListener(this, view);
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

    public void nodeSelected(ProjectViewerEvent evt) {
    }

    public void projectLoaded(ProjectViewerEvent evt) {
        fetchProjectFiles(evt.getProject());
    }

    private synchronized void fetchProjectFiles(VPTProject p) {
        if (project != null && project.equals(p)) {
            logger.info("reindexProject: selected project is the same as already indexed project, leaving");
            return;
        }
        project = p;
        new Thread(new ImportWorker(this)).start();
    }

    class ImportWorker implements Runnable {
        private JEditProjectViewerProject parent;

        public ImportWorker(JEditProjectViewerProject project) {
            this.parent = project;
        }

        public void run() {
            model.clear();
            if (project != null) {
                project.addProjectListener(parent);
                logger.info("reindexProject: indexing files from project " + project.getName());
                model.setIndexing(true);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(JEditProjectViewerProject.class.getName()).log(Level.SEVERE, null, ex);
                }
                for (Object node : project.getOpenableNodes()) {
                    ProjectFile pf = new JEditProjectViewerFile(parent, (VPTFile)node);
                    model.addFile(pf);
                }
                model.setIndexing(false);
                model.refilter();
            } else {
                logger.info("reindexProject: no project selected");
            }
        }

    }

    public void projectAdded(ProjectViewerEvent evt) {
    }

    public void projectRemoved(ProjectViewerEvent evt) {
        evt.getProject().removeProjectListener(this);
        fetchProjectFiles(null);
    }

    public void groupAdded(ProjectViewerEvent evt) {
    }

    public void groupRemoved(ProjectViewerEvent evt) {
    }

    public void groupActivated(ProjectViewerEvent evt) {
        evt.getProject().removeProjectListener(this);
        fetchProjectFiles(null);
    }

    public void nodeMoved(ProjectViewerEvent evt) {
    }

    public void fileAdded(ProjectEvent evt) {
        model.addFile(new JEditProjectViewerFile(this, evt.getAddedFile()));
    }

    public void filesAdded(ProjectEvent evt) {
        for (Object file : evt.getAddedFiles()) {
            model.addFile(new JEditProjectViewerFile(this, (VPTFile)file));
        }
    }

    public void fileRemoved(ProjectEvent evt) {
        model.removeFile(evt.getAddedFile());
    }

    public void filesRemoved(ProjectEvent evt) {
        for (Object file : evt.getRemovedFiles()) {
            model.removeFile(file);
        }
    }

    public void propertiesChanged(ProjectEvent evt) {
    }
}
