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
    	return true;
    }

    @Override
    public boolean isNameSorting() {
    	return true;
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
    	return 0.2f;
    }

    @Override
    public int getMinPatternLength() {
    	return 3;
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
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setNameSorting(boolean b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
