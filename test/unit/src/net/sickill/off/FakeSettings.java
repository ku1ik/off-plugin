/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off;

import net.sickill.off.Settings;

/**
 *
 * @author kill
 */
class FakeSettings extends Settings {
    private boolean matchFromStart = true;
    private boolean smartMatch = true;
    private boolean nameSorting = true;
    private boolean distanceSorting = true;
    private boolean popularitySorting = true;
    private boolean isShowExt = Settings.DEFAULT_SHOW_EXTENSION;
    private boolean isShowPath = Settings.DEFAULT_SHOW_PATH;
    private boolean isShowSize = Settings.DEFAULT_SHOW_SIZE;
    private String lessPriorityMask = Settings.DEFAULT_LESS_PRIORITY_MASK;
    private String ignoreMask = "";

    public FakeSettings() {
    }

    @Override
    public boolean isCustomSorting() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isDistanceSorting() {
        return distanceSorting;
    }

    @Override
    public boolean isExtensionSorting() {
        return false;
    }

    @Override
    public boolean isMatchFromStart() {
        return matchFromStart;
    }

    @Override
    public boolean isPopularitySorting() {
        return popularitySorting;
    }

    @Override
    public boolean isShowExt() {
        return isShowExt;
    }

    @Override
    public boolean isShowPath() {
        return isShowPath;
    }

    @Override
    public boolean isShowSize() {
        return isShowSize;
    }

    @Override
    public boolean isSmartMatch() {
        return smartMatch;
    }

    @Override
    public boolean isNameSorting() {
        return nameSorting;
    }

    @Override
    public boolean isClearOnOpen() {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getMinPatternLength() {
        return 3;
    }

    @Override
    public String getIgnoreMask() {
        return ignoreMask;
    }

    @Override
    public void setIgnoreMask(String mask) {
        ignoreMask = mask;
        compileIgnoreMask();
    }

    @Override
    public void setMatchFromStart(boolean selected) {
        matchFromStart = selected;
    }

    @Override
    public boolean getMatchFromStart() {
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
        smartMatch = selected;
    }

    @Override
    public void setClearOnOpen(boolean selected) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setLessPriorityMask(String mask) {
        lessPriorityMask = mask;
    }

    @Override
    public String getLessPriorityMask() {
        return lessPriorityMask;
    }

    @Override
    public void setNameSorting(boolean b) {
        nameSorting = b;
    }

}
