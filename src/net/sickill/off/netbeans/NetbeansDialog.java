/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.netbeans;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JDialog;
import javax.swing.WindowConstants;
import net.sickill.off.ActionsProvider;
import net.sickill.off.Settings;
import net.sickill.off.OffPanel;
import org.openide.windows.WindowManager;

/**
 *
 * @author kill
 */
public class NetbeansDialog extends JDialog implements ComponentListener {
    static OffPanel taz;
    static Settings settings;
    static NetbeansDialog instance;
    ActionsProvider actions;

    public static NetbeansDialog getInstance() {
        if (instance == null) {
            instance = new NetbeansDialog();
        }
        return instance;
    }

    public NetbeansDialog() {
        super(WindowManager.getDefault().getMainWindow(), "Open File Fast");
        this.addComponentListener(this);
        addNotify();
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        if (taz == null) {
            settings = NetbeansSettings.getInstance();
            taz = new OffPanel(settings, NetbeansProject.getInstance());
        }
        actions = new NetbeansActionsProvider(this);
        taz.setActionsProvider(actions);
        setSize(settings.getDialogWidth(), settings.getDialogHeight());
        setLocationRelativeTo(null);
        getContentPane().add(taz, BorderLayout.CENTER);
        taz.focusOnDefaultComponent();
    }

    public void showDialog() {
        this.setVisible(true);
    }

    public void closeDialog() {
        dispose();
        instance = null;
    }

    public void componentResized(ComponentEvent e) {
        settings.setDialogWidth(this.getWidth());
        settings.setDialogHeight(this.getHeight());
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }
}
