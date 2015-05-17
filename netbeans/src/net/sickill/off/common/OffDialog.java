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
        setMinimumSize(new Dimension(Settings.DEFAULT_DIALOG_WIDTH / 2, Settings.DEFAULT_DIALOG_HEIGHT / 2));
        setSize(settings.getDialogWidth(), settings.getDialogHeight());
        setLocationRelativeTo(null);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        settings.setDialogWidth(this.getWidth());
        settings.setDialogHeight(this.getHeight());
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

}
