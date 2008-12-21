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
public interface Settings {
    public static int DEFAULT_DIALOG_WIDTH = 380;
    public static int DEFAULT_DIALOG_HEIGHT = 400;
    public static int DEFAULT_SEARCH_DELAY = 300;
    public static int DEFAULT_MIN_PATTERN_LENGTH = 3;
    public static boolean DEFAULT_NAME_SORTING = true;
    public static boolean DEFAULT_EXTENSION_SORTING = false;
    public static boolean DEFAULT_SMART_MATCH = true;
    public static String DEFAULT_IGNORE_MASK = "(^gems\\/|(.*\\/)?\\.svn\\/|(.*\\/)?\\.git\\/).*";
    public static boolean DEFAULT_SHOW_EXTENSION = true;
    public static boolean DEFAULT_SHOW_PATH = true;
    public static boolean DEFAULT_SHOW_SIZE = false;
    public static boolean DEFAULT_POPULARITY_SORTING = true;
    public static boolean DEFAULT_DISTANCE_SORTING = true;

    public boolean isCustomSorting();

    public boolean isDistanceSorting();
    public boolean isExtensionSorting();

    public boolean isPopularitySorting();

    public boolean isShowExt();

    public boolean isShowPath();

    public boolean isShowSize();

    public boolean isSmartMatch();

    public boolean isNameSorting();

    public void setDialogWidth(int w);
    public int getDialogWidth();

    public void setDialogHeight(int h);
    public int getDialogHeight();

    public int getSearchDelay();

    public int getMinPatternLength();

    public Pattern getIgnoreMask();
}
