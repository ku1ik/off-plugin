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
public class NetbeansSettings extends Settings {
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

    public float getSearchDelay() {
        return NbPreferences.forModule(OffPanel.class).getFloat("search-delay", Settings.DEFAULT_SEARCH_DELAY);
    }

    public void setSearchDelay(float value) {
        NbPreferences.forModule(OffPanel.class).putFloat("search-delay", value);
    }

    public int getMinPatternLength() {
        return NbPreferences.forModule(OffPanel.class).getInt("min-pattern-length", Settings.DEFAULT_MIN_PATTERN_LENGTH);
    }

    public void setMinPatternLength(int value) {
        NbPreferences.forModule(OffPanel.class).putInt("min-pattern-length", value);
    }

    public boolean isSmartMatch() {
        return NbPreferences.forModule(OffPanel.class).getBoolean("smart-match", Settings.DEFAULT_SMART_MATCH);
    }

    public void setSmartMatch(boolean selected) {
        NbPreferences.forModule(OffPanel.class).putBoolean("smart-match", selected);
    }

    public String getIgnoreMask() {
        return NbPreferences.forModule(OffPanel.class).get("ignore-mask", Settings.DEFAULT_IGNORE_MASK);
    }

    public void setIgnoreMask(String mask) {
        NbPreferences.forModule(OffPanel.class).put("ignore-mask", mask);
    }

    public boolean getMatchFromStart() {
        return NbPreferences.forModule(OffPanel.class).getBoolean("match-from-start", Settings.DEFAULT_MATCH_FROM_START);
    }

    public void setMatchFromStart(boolean selected) {
        NbPreferences.forModule(OffPanel.class).putBoolean("match-from-start", selected);
    }

    public boolean isClearOnOpen() {
        return NbPreferences.forModule(OffPanel.class).getBoolean("clear-on-open", Settings.DEFAULT_CLEAR_ON_OPEN);
    }

    public void setClearOnOpen(boolean selected) {
        NbPreferences.forModule(OffPanel.class).putBoolean("clear-on-open", selected);
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

    public boolean isMatchFromStart() {
        return NbPreferences.forModule(OffPanel.class).getBoolean("match-from-start", Settings.DEFAULT_MATCH_FROM_START);
    }

    public void setLessPriorityMask(String mask) {
        NbPreferences.forModule(OffPanel.class).put("less-priority-mask", mask);
    }

    public String getLessPriorityMask() {
        return NbPreferences.forModule(OffPanel.class).get("less-priority-mask", Settings.DEFAULT_LESS_PRIORITY_MASK);
    }

    @Override
    public void setNameSorting(boolean b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
