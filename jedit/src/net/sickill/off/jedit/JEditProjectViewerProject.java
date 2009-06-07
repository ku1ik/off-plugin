/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.jedit;

import net.sickill.off.common.AbstractProject;
import net.sickill.off.common.OffListModel;
import net.sickill.off.common.ProjectFile;
import org.gjt.sp.jedit.EBComponent;
import org.gjt.sp.jedit.EBMessage;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.msg.ViewUpdate;
import projectviewer.ProjectViewer;
import projectviewer.event.ProjectViewerEvent;
import projectviewer.event.ProjectViewerListener;
import projectviewer.vpt.VPTNode;
import projectviewer.vpt.VPTProject;

/**
 *
 * @author kill
 */
public class JEditProjectViewerProject extends AbstractProject implements ProjectViewerListener {
    View view;
    VPTProject project;

    JEditProjectViewerProject(View view) {
        this.view = view;
        ProjectViewer.addProjectViewerListener(this, view);
    }

    public void init(OffListModel model) {
        super.init(model);
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getProjectRootPath() {
        if (project != null) {
            return project.getRootPath();
        }
        return null; //ProjectViewer.getActiveProject(view).getRootPath();
    }

//	public void handleMessage(EBMessage msg) {
//		if (msg instanceof ViewUpdate) {
//			ViewUpdate update = (ViewUpdate) msg;
//			if (update.getType() == ViewUpdate.Type.PROJECT_LOADED) {
//				VPTProject p = ProjectViewer.getActiveProject(update.getView());
//				if (p != null) {
//					listModel.setProject(p);
//					Log.log(Log.DEBUG, this.getClass(),
//							"ViewerUpdate: activeProject = " + p.getName());
//					Log.log(Log.DEBUG, this.getClass(), "ViewerUpdate: "
//							+ listModel.getSize() + " files in project "
//							+ p.getName());
//				} else {
//					listModel.setProject(null);
//					Log.log(Log.DEBUG, this.getClass(),
//							"ViewerUpdate: activeProject = null");
//				}
//			} else {
//				Log.log(Log.DEBUG, this.getClass(), "ViewerUpdate: type="
//						+ update.getType());
//			}
//			patternInput.setText("");
//			startSearching();
//		}
//	}

    public void nodeSelected(ProjectViewerEvent evt) {
    }

    public void projectLoaded(ProjectViewerEvent evt) {
        project = evt.getProject();
        model.clear();
        if (project != null) {
            for (Object node : project.getOpenableNodes()) {
                ProjectFile pf = new JEditProjectViewerFile(this, (VPTNode)node);
                model.addFile(pf);
            }
        }
    }

    public void projectAdded(ProjectViewerEvent evt) {
    }

    public void projectRemoved(ProjectViewerEvent evt) {
        model.clear();
    }

    public void groupAdded(ProjectViewerEvent evt) {
    }

    public void groupRemoved(ProjectViewerEvent evt) {
    }

    public void groupActivated(ProjectViewerEvent evt) {
        model.clear();
    }

    public void nodeMoved(ProjectViewerEvent evt) {
    }
}
