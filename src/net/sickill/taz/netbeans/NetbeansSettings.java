/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.taz.netbeans;

import java.util.prefs.Preferences;
import net.sickill.taz.Settings;
import net.sickill.taz.Taz;
import org.openide.util.NbPreferences;

/**
 *
 * @author kill
 */
public class NetbeansSettings implements Settings {

    public void setDialogWidth(int w) {
        Preferences prefs = NbPreferences.forModule(Taz.class);
        prefs.putInt("dialog-width", w);
    }

    public int getDialogWidth() {
        return NbPreferences.forModule(Taz.class).getInt("dialog-width", 380);
    }

    public void setDialogHeight(int h) {
        Preferences prefs = NbPreferences.forModule(Taz.class);
        prefs.putInt("dialog-height", h);
    }

    public int getDialogHeight() {
        return NbPreferences.forModule(Taz.class).getInt("dialog-height", 400);
    }

    public int getSearchDelay() {
        return NbPreferences.forModule(Taz.class).getInt("search-delay", 300);
    }

    public int getMinPatternLength() {
        return NbPreferences.forModule(Taz.class).getInt("min-pattern-length", 3);
    }

    public boolean isGroupResults() {
        return NbPreferences.forModule(Taz.class).getBoolean("group-results", false);
    }

    public boolean isShowExt() {
        return NbPreferences.forModule(Taz.class).getBoolean("show-ext", true);
    }

    public boolean isShowPath() {
        return NbPreferences.forModule(Taz.class).getBoolean("show-path", true);
    }

    public boolean isShowSize() {
        return NbPreferences.forModule(Taz.class).getBoolean("show-size", false);
    }

    public boolean isSmartMatch() {
        return NbPreferences.forModule(Taz.class).getBoolean("smart-match", true);
    }

    public boolean isSortResults() {
        return NbPreferences.forModule(Taz.class).getBoolean("sort-results", true);
    }

}
