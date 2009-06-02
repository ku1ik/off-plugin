/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.netbeans;

import javax.swing.JDialog;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import net.sickill.off.common.ActionsProvider;
import net.sickill.off.common.ProjectFile;
import org.netbeans.api.editor.EditorRegistry;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.Exceptions;
import org.openide.cookies.OpenCookie;
import org.openide.text.NbDocument;

/**
 *
 * @author kill
 */
public class NetbeansActionsProvider implements ActionsProvider {
    private NetbeansDialog dialog;

    public NetbeansActionsProvider(NetbeansDialog dialog) {
        this.dialog = dialog;
    }

    public void openFile(ProjectFile pf, int lineNo) {
        try {
            DataObject data = DataObject.find(((NetbeansProjectFile)pf).getFileObject());
            data.getLookup().lookup(OpenCookie.class).open();
            if (lineNo > -1)
                performGoto(lineNo);
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public void openFile(ProjectFile pf) {
        openFile(pf, -1);
    }

    private boolean performGoto(int lineNo) {
        JTextComponent editor = EditorRegistry.lastFocusedComponent();
        Document doc = editor.getDocument();
        editor.setCaretPosition(NbDocument.findLineOffset((StyledDocument)doc, lineNo-1));
        return true;
    }

    public void closeWindow() {
        dialog.closeDialog();
    }

}
