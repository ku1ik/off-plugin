/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.taz;

/**
 *
 * @author kill
 */
public interface Settings {

    public boolean isGroupResults();

    public boolean isShowExt();

    public boolean isShowPath();

    public boolean isShowSize();

    public boolean isSmartMatch();

    public boolean isSortResults();

    public void setDialogWidth(int w);
    public int getDialogWidth();

    public void setDialogHeight(int h);
    public int getDialogHeight();

    public int getSearchDelay();

    public int getMinPatternLength();
}
