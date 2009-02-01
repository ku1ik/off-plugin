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
class TestSettings extends Settings {
    private boolean matchFromStart = true;
    private boolean smartMatch = true;

    public TestSettings() {
    }

    @Override
    public boolean isCustomSorting() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isDistanceSorting() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isExtensionSorting() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isMatchFromStart() {
        return matchFromStart;
    }

    @Override
    public boolean isPopularitySorting() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isShowExt() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isShowPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isShowSize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isSmartMatch() {
        return smartMatch;
    }

    @Override
    public boolean isNameSorting() {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getIgnoreMask() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setIgnoreMask(String mask) {
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getLessPriorityMask() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
