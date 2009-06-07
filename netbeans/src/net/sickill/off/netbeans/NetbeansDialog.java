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
import net.sickill.off.common.IDE;
import net.sickill.off.common.Settings;
import net.sickill.off.common.OffPanel;
import org.openide.windows.WindowManager;

/**
 *
 * @author kill
 */
public class NetbeansDialog extends JDialog implements ComponentListener {
    static OffPanel off;
    static IDE ide;
    static Settings settings;
    static NetbeansDialog instance;

    public static NetbeansDialog getInstance() {
        if (instance == null) {
            instance = new NetbeansDialog();
        }
        return instance;
    }

    public NetbeansDialog() {
        super(WindowManager.getDefault().getMainWindow(), "Open File Fast");
        addComponentListener(this);
        addNotify();
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        if (off == null) {
            settings = NetbeansSettings.getInstance();
            ide = new NetbeansIDE();
            off = new OffPanel(ide, settings, NetbeansProject.getInstance());
        }
        ide.setDialog(this);
        setSize(settings.getDialogWidth(), settings.getDialogHeight());
        setLocationRelativeTo(null);
        getContentPane().add(off, BorderLayout.CENTER);
    }

    public void showDialog() {
        this.setVisible(true);
        off.focusOnDefaultComponent();
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
