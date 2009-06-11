/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.common;

import java.util.regex.Pattern;

/**
 *
 * @author kill
 */
public abstract class Settings {
    public static int DEFAULT_DIALOG_WIDTH = 380;
    public static int DEFAULT_DIALOG_HEIGHT = 400;
    public static float DEFAULT_SEARCH_DELAY = 0.3f;
    public static int DEFAULT_MIN_PATTERN_LENGTH = 3;
    public static boolean DEFAULT_NAME_SORTING = true;
    public static boolean DEFAULT_EXTENSION_SORTING = false;
    public static boolean DEFAULT_SMART_MATCH = true;
    public static String DEFAULT_IGNORE_MASK = "^gems\\/.+";
    public static boolean DEFAULT_SHOW_EXTENSION = true;
    public static boolean DEFAULT_SHOW_PATH = true;
    public static boolean DEFAULT_SHOW_SIZE = false;
    public static boolean DEFAULT_POPULARITY_SORTING = true;
    public static boolean DEFAULT_DISTANCE_SORTING = true;
    public static boolean DEFAULT_MATCH_FROM_START = true;
    public static boolean DEFAULT_CLEAR_ON_OPEN = false;
    public static String DEFAULT_LESS_PRIORITY_MASK = "";

    private Pattern lessPriorityMaskCompiled;
    private Pattern ignoreMaskCompiled;

    public abstract void setBoolean(String prop, boolean b);
    public abstract boolean getBoolean(String prop, boolean def);
    public abstract void setString(String prop, String s);
    public abstract String getString(String prop, String def);
    public abstract void setInt(String prop, int i);
    public abstract int getInt(String prop, int def);
    public abstract void setFloat(String prop, float f);
    public abstract float getFloat(String prop, float def);

    protected static Pattern compileMask(String mask) {
        return mask.equals("") ? null : Pattern.compile("(" + mask.replaceAll("\n", ")|(") + ")");
    }

    protected void compileLessPriorityMask() {
        String mask = getLessPriorityMask().trim();
        lessPriorityMaskCompiled = compileMask(mask);
    }

    protected void compileIgnoreMask() {
        String mask = getIgnoreMask().trim();
        ignoreMaskCompiled = compileMask(mask);
    }

    public Pattern getLessPriorityMaskCompiled() {
        if (lessPriorityMaskCompiled == null) {
            compileLessPriorityMask();
        }
        return lessPriorityMaskCompiled;
    }

    public Pattern getIgnoreMaskCompiled() {
        if (ignoreMaskCompiled == null) {
            compileIgnoreMask();
        }
        return ignoreMaskCompiled;
    }

    public void lessPriorityMaskChanged() {
        lessPriorityMaskCompiled = null;
    }

    // dialog

    public void setDialogWidth(int w) {
        setInt("dialog-width", w);
    }

    public int getDialogWidth() {
        return getInt("dialog-width", Settings.DEFAULT_DIALOG_WIDTH);
    }

    public void setDialogHeight(int h) {
        setInt("dialog-height", h);
    }

    public int getDialogHeight() {
        return getInt("dialog-height", Settings.DEFAULT_DIALOG_HEIGHT);
    }

    // behaviour

    public float getSearchDelay() {
        return getFloat("search-delay", Settings.DEFAULT_SEARCH_DELAY);
    }

    public void setSearchDelay(float value) {
        setFloat("search-delay", value);
    }

    public int getMinPatternLength() {
        return getInt("min-pattern-length", Settings.DEFAULT_MIN_PATTERN_LENGTH);
    }

    public void setMinPatternLength(int value) {
        setInt("min-pattern-length", value);
    }

    public boolean isSmartMatch() {
        return getBoolean("smart-match", Settings.DEFAULT_SMART_MATCH);
    }

    public void setSmartMatch(boolean selected) {
        setBoolean("smart-match", selected);
    }

    public String getIgnoreMask() {
        return getString("ignore-mask", Settings.DEFAULT_IGNORE_MASK);
    }

    public void setIgnoreMask(String mask) {
        setString("ignore-mask", mask);
        compileIgnoreMask();
    }

    public void setMatchFromStart(boolean selected) {
        setBoolean("match-from-start", selected);
    }

    public boolean isClearOnOpen() {
        return getBoolean("clear-on-open", Settings.DEFAULT_CLEAR_ON_OPEN);
    }

    public void setClearOnOpen(boolean selected) {
        setBoolean("clear-on-open", selected);
    }

    // show

    public boolean isShowExt() {
        return getBoolean("show-ext", Settings.DEFAULT_SHOW_EXTENSION);
    }

    public boolean isShowPath() {
        return getBoolean("show-path", Settings.DEFAULT_SHOW_PATH);
    }

    public boolean isShowSize() {
        return getBoolean("show-size", Settings.DEFAULT_SHOW_SIZE);
    }

    // sorting

    public boolean isNameSorting() {
        return getBoolean("name-sorting", Settings.DEFAULT_NAME_SORTING);
    }

    public boolean isExtensionSorting() {
        return getBoolean("extension-sorting", Settings.DEFAULT_EXTENSION_SORTING);
    }

    public boolean isPopularitySorting() {
        return getBoolean("popularity-sorting", Settings.DEFAULT_POPULARITY_SORTING);
    }

    public boolean isCustomSorting() {
        return false;
    }

    public boolean isDistanceSorting() {
        return getBoolean("distance-sorting", Settings.DEFAULT_DISTANCE_SORTING);
    }

    public boolean isMatchFromStart() {
        return getBoolean("match-from-start", Settings.DEFAULT_MATCH_FROM_START);
    }

    public void setLessPriorityMask(String mask) {
        setString("less-priority-mask", mask);
    }

    public String getLessPriorityMask() {
        return getString("less-priority-mask", Settings.DEFAULT_LESS_PRIORITY_MASK);
    }

}
