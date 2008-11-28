/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sickill.taz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.WindowConstants;
import net.sickill.taz.netbeans.NetbeansActionsProvider;
import org.openide.windows.WindowManager;

public final class OpenTazAction implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        // TODO implement action body
        //Project p = OpenProjects.getDefault().getMainProject();
        //int msgType = NotifyDescriptor.INFORMATION_MESSAGE;
        //NotifyDescriptor d = new NotifyDescriptor.Message(p.getProjectDirectory().getPath(), msgType);
        //DialogDisplayer.getDefault().notify(d);

        JDialog dialog = new JDialog(WindowManager.getDefault().getMainWindow(), "Taz");
        dialog.setSize(330, 400);
        dialog.setLocationRelativeTo(null);
        dialog.addNotify();
        dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        ActionsProvider actions = new NetbeansActionsProvider(dialog);
        Taz taz = new Taz(actions);
        dialog.getContentPane().add(taz, BorderLayout.CENTER);
        dialog.setVisible(true);
    }
}
