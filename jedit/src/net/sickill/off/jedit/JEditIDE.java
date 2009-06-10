package net.sickill.off.jedit;

import javax.swing.JPanel;
import net.sickill.off.common.IDE;
import net.sickill.off.common.ProjectFile;

/**
 *
 * @author kill
 */
public class JEditIDE extends IDE {

    @Override
    public void openFile(ProjectFile pf) {
        ((JEditProjectViewerFile)pf).getFile().open();
    }

    @Override
    public void openFile(ProjectFile pf, int lineNo) {
        openFile(pf);
    }

    @Override
    public void closeWindow() {
        OffPlugin.closeDialogsAndPanels();
    }

    @Override
    public void onFocus() {
    }

    @Override
    public void onIndexing(boolean indexing) {
    }

    @Override
    public void addCustomControls(JPanel panel) {
    }

}
