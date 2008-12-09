/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.netbeans;

import java.util.prefs.Preferences;
import net.sickill.off.Settings;
import net.sickill.off.OffPanel;
import org.openide.util.NbPreferences;

/**
 *
 * @author kill
 */
public class NetbeansSettings implements Settings {

    public void setDialogWidth(int w) {
        Preferences prefs = NbPreferences.forModule(OffPanel.class);
        prefs.putInt("dialog-width", w);
    }

    public int getDialogWidth() {
        return NbPreferences.forModule(OffPanel.class).getInt("dialog-width", 380);
    }

    public void setDialogHeight(int h) {
        Preferences prefs = NbPreferences.forModule(OffPanel.class);
        prefs.putInt("dialog-height", h);
    }

    public int getDialogHeight() {
        return NbPreferences.forModule(OffPanel.class).getInt("dialog-height", 400);
    }

    public int getSearchDelay() {
        return NbPreferences.forModule(OffPanel.class).getInt("search-delay", 300);
    }

    public int getMinPatternLength() {
        return NbPreferences.forModule(OffPanel.class).getInt("min-pattern-length", 3);
    }

    public boolean isGroupResults() {
        return NbPreferences.forModule(OffPanel.class).getBoolean("group-results", false);
    }

    public boolean isShowExt() {
        return NbPreferences.forModule(OffPanel.class).getBoolean("show-ext", true);
    }

    public boolean isShowPath() {
        return NbPreferences.forModule(OffPanel.class).getBoolean("show-path", true);
    }

    public boolean isShowSize() {
        return NbPreferences.forModule(OffPanel.class).getBoolean("show-size", false);
    }

    public boolean isSmartMatch() {
        return NbPreferences.forModule(OffPanel.class).getBoolean("smart-match", true);
    }

    public boolean isSortResults() {
        return NbPreferences.forModule(OffPanel.class).getBoolean("sort-results", true);
    }

}
