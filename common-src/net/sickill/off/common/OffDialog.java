/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.common;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

/**
 *
 * @author kill
 */
public abstract class OffDialog extends JDialog implements ComponentListener {
    protected static Settings settings;

    protected abstract Settings getSettings();

    public OffDialog(Frame f, String name) {
        super(f, name);
        addComponentListener(this);
        addNotify();
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        settings = getSettings();
        setMinimumSize(new Dimension(settings.DEFAULT_DIALOG_WIDTH / 2, settings.DEFAULT_DIALOG_HEIGHT / 2));
        setSize(settings.getDialogWidth(), settings.getDialogHeight());
        setLocationRelativeTo(null);
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
