package net.sickill.off.common;

import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * @author sickill
 */
public abstract class IDE {

    protected JDialog dialog;
    protected OffPanel off;

    public void setDialog(JDialog dialog) {
        this.dialog = dialog;
    }

    public void setPanel(OffPanel off) {
        this.off = off;
    }

    public abstract void openFile(String fullPath, int lineNo);

    public abstract void closeWindow();

    public abstract void onFocus();

    public abstract void onIndexing(boolean indexing);

    public abstract void addCustomControls(JPanel panel);

}
