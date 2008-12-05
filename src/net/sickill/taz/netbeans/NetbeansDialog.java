/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.taz.netbeans;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JDialog;
import javax.swing.WindowConstants;
import net.sickill.taz.ActionsProvider;
import net.sickill.taz.Taz;
import org.openide.windows.WindowManager;

/**
 *
 * @author kill
 */
public class NetbeansDialog extends JDialog implements ComponentListener {
    static Taz taz;
    ActionsProvider actions;

    public NetbeansDialog() {
        super(WindowManager.getDefault().getMainWindow(), "Taz");
        this.addComponentListener(this);
        addNotify();
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        actions = new NetbeansActionsProvider(this);
        if (taz == null) {
            taz = new Taz(new NetbeansProjectFilesProvider());
            taz.setSettings(new NetbeansSettings());
        }
        taz.setActionsProvider(actions);
        setSize(taz.getSettings().getDialogWidth(), taz.getSettings().getDialogHeight());
        setLocationRelativeTo(null);
        getContentPane().add(taz, BorderLayout.CENTER);
    }

    public void componentResized(ComponentEvent e) {
        taz.getSettings().setDialogWidth(this.getWidth());
        taz.getSettings().setDialogHeight(this.getHeight());
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }
}
