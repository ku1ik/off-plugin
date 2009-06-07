/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void openFile(ProjectFile pf, int lineNo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void closeWindow() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onFocus() {
    }

    @Override
    public void onIndexing(boolean indexing) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addCustomControls(JPanel panel) {
    }

}
