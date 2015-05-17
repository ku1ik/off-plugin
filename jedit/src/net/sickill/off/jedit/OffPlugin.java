package net.sickill.off.jedit;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import net.sickill.off.common.IDE;
import net.sickill.off.common.OffListModel;
import net.sickill.off.common.OffPanel;
import net.sickill.off.common.Settings;
import org.gjt.sp.jedit.EditPlugin;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.gui.DockableWindowManager;
import org.gjt.sp.jedit.jEdit;


public class OffPlugin extends EditPlugin {

    private static Settings settings = new JEditSettings();

    static Hashtable<View, OffPanel> viewsWithOff = new Hashtable<View, OffPanel>();
    static Hashtable<View, JEditDialog> dialogs = new Hashtable<View, JEditDialog>();
    static WindowAdapter windowAdapter;

    @Override
    public void start() {
        windowAdapter = new TazWindowAdapter();
        super.start();
    }

    @Override
    public void stop() {
        Enumeration<View> iter = viewsWithOff.keys();
        while (iter.hasMoreElements()) {
            View v = iter.nextElement();
            v.removeWindowListener(windowAdapter);
        }
        viewsWithOff.clear();
        super.stop();
    }

    /**
     * @param view
     *            The View we activated Taz from.
     * @return A lightweight JPanel wrapper around the Taz instance for this
     *         View.
     */
    public static OffPanel getOffInstance(View view) {
        OffPanel off = viewsWithOff.get(view);
        if (off == null) {
            IDE ide = new JEditIDE();
            JEditProjectViewerProject project = new JEditProjectViewerProject(view);
            OffListModel model = new OffListModel(settings);
            project.init(model);
            off = new JEditOffPanel(ide, settings, project);
            viewsWithOff.put(view, off);
            view.addWindowListener(windowAdapter);
        }
        return off;
    }

    public static JDialog showDialog(View view) { // TODO: change return value to void
        JEditDialog dialog = dialogs.get(view);
        if (dialog == null) {
            dialog = new JEditDialog(view, getOffInstance(view));
            dialogs.put(view, dialog);
        }
        dialog.showDialog();
        return dialog;
    }

    static void closeDialogsAndPanels() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DockableWindowManager dwm = jEdit.getActiveView().getDockableWindowManager();
                dwm.hideDockableWindow("openfilefastdockable");
            }
        });
        for (JDialog dialog : dialogs.values()) {
            dialog.dispose();
        }
        dialogs.clear();
    }

    class TazWindowAdapter extends WindowAdapter {
        // fired when JEdit View is closed
        public void windowClosed(WindowEvent evt) {
            viewsWithOff.remove(evt.getWindow());
            JDialog dialog = (JDialog)dialogs.remove(evt.getWindow());
            if (dialog != null) {
                dialog.dispose();
                dialog = null;
                System.gc();
            }
        }
    }

}// End of class OffPlugin
