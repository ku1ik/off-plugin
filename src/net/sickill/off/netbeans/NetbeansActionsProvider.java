/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.netbeans;

import javax.swing.JDialog;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import net.sickill.off.ActionsProvider;
import net.sickill.off.ProjectFile;
import org.netbeans.api.editor.EditorRegistry;
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
            performGoto(lineNo);
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private boolean performGoto(int lineNo) {
        JTextComponent editor = EditorRegistry.lastFocusedComponent();
        Document doc = editor.getDocument();
        //doc.
        editor.setCaretPosition(lineNo);
        return true;
    }

    public void closeWindow() {
        dialog.dispose();
    }
}
