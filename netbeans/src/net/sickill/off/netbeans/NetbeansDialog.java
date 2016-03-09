package net.sickill.off.netbeans;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import net.sickill.off.common.IDE;
import net.sickill.off.common.OffDialog;
import net.sickill.off.common.Settings;
import net.sickill.off.common.OffPanel;
import org.openide.windows.WindowManager;

/**
 * @author sickill
 */
public class NetbeansDialog extends OffDialog {

    private static NetbeansDialog instance;
    OffPanel off;

    public static NetbeansDialog getInstance() {
        if (instance == null) {
            instance = new NetbeansDialog();
        }

        return instance;
    }

    public NetbeansDialog() {
        super(WindowManager.getDefault().getMainWindow(), "Open File Fast");

        IDE ide = new NetbeansIDE();
        off = new OffPanel(ide, settings, NetbeansProject.getInstance());

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                saveRecentSearch();
            }
        });

        ide.setDialog(this);
        getContentPane().add(off, BorderLayout.CENTER);
    }

    private void saveRecentSearch() {
        String recentSearch = off.getPatternInput().getText();
        getSettings().addToSearchHistory(recentSearch);
    }

    public void showDialog() {
        // try to use monitor, where the input focus is
        // therefor get the topmost component based on the input focus
        Component focusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        if (null != focusOwner) {
            while (focusOwner.getParent() != null) {
                focusOwner = focusOwner.getParent();
            }
        }
        this.setLocationRelativeTo(focusOwner);
        
        this.setVisible(true);
        off.focusOnDefaultComponent();
    }

    public void closeDialog() {
        saveRecentSearch();
        dispose();
        instance = null;
    }

    @Override
    protected Settings getSettings() {
        return NetbeansSettings.getInstance();
    }

}
