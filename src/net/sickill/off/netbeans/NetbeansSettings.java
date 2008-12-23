/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.netbeans;

import java.util.prefs.Preferences;
import java.util.regex.Pattern;
import net.sickill.off.Settings;
import net.sickill.off.OffPanel;
import org.openide.util.NbPreferences;

/**
 *
 * @author kill
 */
public class NetbeansSettings implements Settings {
    private static NetbeansSettings settings;

    private NetbeansSettings() {
    }

    public static NetbeansSettings getInstance() {
        if (settings == null) {
            settings = new NetbeansSettings();
        }
        return settings;
    }
    
    // dialog

    public void setDialogWidth(int w) {
        Preferences prefs = NbPreferences.forModule(OffPanel.class);
        prefs.putInt("dialog-width", w);
    }

    public int getDialogWidth() {
        return NbPreferences.forModule(OffPanel.class).getInt("dialog-width", Settings.DEFAULT_DIALOG_WIDTH);
    }

    public void setDialogHeight(int h) {
        Preferences prefs = NbPreferences.forModule(OffPanel.class);
        prefs.putInt("dialog-height", h);
    }

    public int getDialogHeight() {
        return NbPreferences.forModule(OffPanel.class).getInt("dialog-height", Settings.DEFAULT_DIALOG_HEIGHT);
    }

    // behaviour

    public int getSearchDelay() {
        return NbPreferences.forModule(OffPanel.class).getInt("search-delay", Settings.DEFAULT_SEARCH_DELAY);
    }

    public int getMinPatternLength() {
        return NbPreferences.forModule(OffPanel.class).getInt("min-pattern-length", Settings.DEFAULT_MIN_PATTERN_LENGTH);
    }

    public boolean isSmartMatch() {
        return NbPreferences.forModule(OffPanel.class).getBoolean("smart-match", Settings.DEFAULT_SMART_MATCH);
    }

    public Pattern getIgnoreMask() {
        String mask = NbPreferences.forModule(OffPanel.class).get("ignore-mask", Settings.DEFAULT_IGNORE_MASK);
        return mask.equals("") ? null : Pattern.compile(mask);
    }

    // show

    public boolean isShowExt() {
        return NbPreferences.forModule(OffPanel.class).getBoolean("show-ext", Settings.DEFAULT_SHOW_EXTENSION);
    }

    public boolean isShowPath() {
        return NbPreferences.forModule(OffPanel.class).getBoolean("show-path", Settings.DEFAULT_SHOW_PATH);
    }

    public boolean isShowSize() {
        return NbPreferences.forModule(OffPanel.class).getBoolean("show-size", Settings.DEFAULT_SHOW_SIZE);
    }

    // sorting

    public boolean isNameSorting() {
        return NbPreferences.forModule(OffPanel.class).getBoolean("name-sorting", Settings.DEFAULT_NAME_SORTING);
    }

    public boolean isExtensionSorting() {
        return NbPreferences.forModule(OffPanel.class).getBoolean("extension-sorting", Settings.DEFAULT_EXTENSION_SORTING);
    }

    public boolean isPopularitySorting() {
        return NbPreferences.forModule(OffPanel.class).getBoolean("popularity-sorting", Settings.DEFAULT_POPULARITY_SORTING);
    }

    public boolean isCustomSorting() {
        return false;
    }

    public boolean isDistanceSorting() {
        return NbPreferences.forModule(OffPanel.class).getBoolean("distance-sorting", Settings.DEFAULT_DISTANCE_SORTING);
    }


}
