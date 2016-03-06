package net.sickill.off.netbeans;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;

@ActionID(id = "net.sickill.off.netbeans.OpenOffAction", category = "Edit")
@ActionRegistration(displayName = "#CTL_OpenOffAction", lazy = false)
@ActionReference(path = "Menu/GoTo", position = 0)
@NbBundle.Messages(value = "CTL_OpenOffAction=Go to File (Fa&st)...")
public final class OpenOffAction extends AbstractAction {

    public OpenOffAction() {
        putValue(Action.NAME, Bundle.CTL_OpenOffAction());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        NetbeansDialog dialog = NetbeansDialog.getInstance();
        dialog.showDialog();
    }

    @Override
    public boolean isEnabled() {
        return OpenProjects.getDefault().getOpenProjects().length > 0;
    }

}
