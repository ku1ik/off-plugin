/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.jedit;

import net.sickill.off.common.Settings;

/**
 *
 * @author kill
 */
public class JEditSettings extends Settings {

    @Override
    public boolean isCustomSorting() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isDistanceSorting() {
    	return true;
    }

    @Override
    public boolean isExtensionSorting() {
    	return true;
    }

    @Override
    public boolean isMatchFromStart() {
    	return true;
    }

    @Override
    public boolean isPopularitySorting() {
    	return true;
    }

    @Override
    public boolean isShowExt() {
        return true;
    }

    @Override
    public boolean isShowPath() {
        return true;
    }

    @Override
    public boolean isShowSize() {
        return false;
    }

    @Override
    public boolean isSmartMatch() {
    	return true;
    }

    @Override
    public boolean isNameSorting() {
    	return true;
    }

    @Override
    public boolean isClearOnOpen() {
        return false;
    }

    @Override
    public void setDialogWidth(int w) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getDialogWidth() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setDialogHeight(int h) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getDialogHeight() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public float getSearchDelay() {
    	return 0.2f;
    }

    @Override
    public int getMinPatternLength() {
    	return 3;
    }

    @Override
    public String getIgnoreMask() {
        return "(^|.*\\/).git\\/.*";
    }

    @Override
    public void setIgnoreMask(String mask) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setMatchFromStart(boolean selected) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setMinPatternLength(int value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setSearchDelay(float value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setSmartMatch(boolean selected) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setClearOnOpen(boolean selected) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setLessPriorityMask(String mask) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getLessPriorityMask() {
        return "";
    }

    @Override
    public void setNameSorting(boolean b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

//	void loadSettings() {
//		setMinPatternLength(jEdit.getIntegerProperty(MIN_PATTERN_LENGTH));
//		setSearchDelay(jEdit.getIntegerProperty(SEARCH_DELAY));
//		setSmartMatch(jEdit.getBooleanProperty(SMART_MATCH));
//		setSortResults(jEdit.getBooleanProperty(SORT_RESULTS));
//		setGroupResults(jEdit.getBooleanProperty(GROUP_RESULTS));
//		setClearOnOpen(jEdit.getBooleanProperty(CLEAR_ON_OPEN));
//		setShowIcon(jEdit.getBooleanProperty(SHOW_ICON));
//		setShowPath(jEdit.getBooleanProperty(SHOW_PATH));
//		setShowExt(jEdit.getBooleanProperty(SHOW_EXT));
//		setShowSize(jEdit.getBooleanProperty(SHOW_SIZE));
//	}



//	public static int getMinPatternLength() {
//		return minPatternLength;
//	}
//
//	public static void setMinPatternLength(int minPatternLength) {
//		OffPlugin.minPatternLength = minPatternLength;
//		jEdit.setIntegerProperty(OffPlugin.MIN_PATTERN_LENGTH, minPatternLength);
//	}
//
//	public static int getSearchDelay() {
//		return searchDelay;
//	}
//
//	public static void setSearchDelay(int searchDelay) {
//		OffPlugin.searchDelay = searchDelay;
//		jEdit.setIntegerProperty(OffPlugin.SEARCH_DELAY, searchDelay);
//	}
//
//	public static boolean isSmartMatch() {
//		return smartMatch;
//	}
//
//	public static void setSmartMatch(boolean smartMatch) {
//		OffPlugin.smartMatch = smartMatch;
//		jEdit.setBooleanProperty(OffPlugin.SMART_MATCH, smartMatch);
//	}
//
//	public static boolean isSortResults() {
//		return sortResults;
//	}
//
//	public static void setSortResults(boolean sortResults) {
//		OffPlugin.sortResults = sortResults;
//		jEdit.setBooleanProperty(OffPlugin.SORT_RESULTS, sortResults);
//	}
//
//	public static boolean isGroupResults() {
//		return groupResults;
//	}
//
//	public static void setGroupResults(boolean groupResults) {
//		OffPlugin.groupResults = groupResults;
//		jEdit.setBooleanProperty(OffPlugin.GROUP_RESULTS, groupResults);
//	}
//
//	public static boolean isClearOnOpen() {
//		return clearOnOpen;
//	}
//
//	public static void setClearOnOpen(boolean clearOnOpen) {
//		OffPlugin.clearOnOpen = clearOnOpen;
//		jEdit.setBooleanProperty(OffPlugin.CLEAR_ON_OPEN, clearOnOpen);
//	}
//
//	public static boolean isShowIcon() {
//		return showIcon;
//	}
//
//	public static void setShowIcon(boolean showIcon) {
//		OffPlugin.showIcon = showIcon;
//		jEdit.setBooleanProperty(OffPlugin.SHOW_ICON, showIcon);
//	}
//
//	public static boolean isShowPath() {
//		return showPath;
//	}
//
//	public static void setShowPath(boolean showPath) {
//		OffPlugin.showPath = showPath;
//		jEdit.setBooleanProperty(OffPlugin.SHOW_PATH, showPath);
//	}
//
//	public static boolean isShowExt() {
//		return showExt;
//	}
//
//	public static void setShowExt(boolean showExt) {
//		OffPlugin.showExt = showExt;
//		jEdit.setBooleanProperty(OffPlugin.SHOW_EXT, showExt);
//	}
//
//	public static boolean isShowSize() {
//		return showSize;
//	}
//
//	public static void setShowSize(boolean showSize) {
//		OffPlugin.showSize = showSize;
//		jEdit.setBooleanProperty(OffPlugin.SHOW_SIZE, showSize);
//	}

}
