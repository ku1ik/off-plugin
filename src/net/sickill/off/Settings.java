/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off;

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
    public static String DEFAULT_IGNORE_MASK = "^gems\\/";
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

    public abstract boolean isCustomSorting();
    public abstract boolean isDistanceSorting();
    public abstract boolean isExtensionSorting();
    public abstract boolean isMatchFromStart();
    public abstract boolean isPopularitySorting();
    public abstract boolean isShowExt();
    public abstract boolean isShowPath();
    public abstract boolean isShowSize();
    public abstract boolean isSmartMatch();
    public abstract boolean isNameSorting();
    public abstract boolean isClearOnOpen();
    public abstract void setDialogWidth(int w);
    public abstract int getDialogWidth();
    public abstract void setDialogHeight(int h);
    public abstract int getDialogHeight();
    public abstract float getSearchDelay();
    public abstract int getMinPatternLength();
    public abstract String getIgnoreMask();
    public abstract void setIgnoreMask(String mask);
    public abstract void setMatchFromStart(boolean selected);
    public abstract boolean getMatchFromStart();
    public abstract void setMinPatternLength(int value);
    public abstract void setSearchDelay(float value);
    public abstract void setSmartMatch(boolean selected);
    public abstract void setClearOnOpen(boolean selected);
    public abstract void setLessPriorityMask(String mask);
    public abstract String getLessPriorityMask();
    public abstract void setNameSorting(boolean b);

    protected void compileLessPriorityMask() {
        String mask = getLessPriorityMask();
        lessPriorityMaskCompiled = mask.equals("") ? null : Pattern.compile("(" + mask.replaceAll("\n", ")|(") + ")");
    }

    protected void compileIgnoreMask() {
        String mask = getIgnoreMask().trim();
        ignoreMaskCompiled = mask.equals("") ? null : Pattern.compile("(" + mask.replaceAll("\n", ")|(") + ")");
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
}
