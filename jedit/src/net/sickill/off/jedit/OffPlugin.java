package net.sickill.off.jedit;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
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

    static Map<View, OffPanel> viewsWithOff = new HashMap<>();
    static Map<View, JEditDialog> dialogs = new HashMap<>();
    static WindowAdapter windowAdapter;

    @Override
    public void start() {
        windowAdapter = new OffWindowAdapter();
        super.start();
    }

    @Override
    public void stop() {
        for (View v : viewsWithOff.keySet()) {
            v.removeWindowListener(windowAdapter);
        }
        viewsWithOff.clear();
        super.stop();
    }

    /**
     * @param view
     *            The View we activated OFF from.
     * @return A lightweight JPanel wrapper around the OFF instance for this
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
            @Override
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

    class OffWindowAdapter extends WindowAdapter {
        // fired when JEdit View is closed
        @Override
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
