package net.sickill.off.netbeans;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;

@ActionID(id = "net.sickill.off.netbeans.OpenOffAction", category = "Project")
@ActionRegistration(displayName = "#CTL_OpenOffAction")
@ActionReference(path = "Menu/GoTo", position = 0)
public final class OpenOffAction extends AbstractAction {

    @Override
    public void actionPerformed(ActionEvent e) {
        NetbeansDialog dialog = NetbeansDialog.getInstance();
        dialog.showDialog();
    }
}
