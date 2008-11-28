/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.taz.netbeans;

import javax.swing.JDialog;
import net.sickill.taz.ActionsProvider;
import net.sickill.taz.ProjectFile;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.Exceptions;
import org.openide.cookies.OpenCookie;

/**
 *
 * @author kill
 */
public class NetbeansActionsProvider implements ActionsProvider {
    private JDialog dialog;

    public NetbeansActionsProvider(JDialog dialog) {
        this.dialog = dialog;
    }

    public void openFile(ProjectFile pf, int lineNo) {
        try {
            DataObject data = DataObject.find(((NetbeansProjectFile)pf).getFileObject());
            data.getLookup().lookup(OpenCookie.class).open();
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public void closeWindow() {
        dialog.dispose();
    }
}
