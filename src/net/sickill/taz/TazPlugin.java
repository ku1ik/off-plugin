/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.taz;

/**
 *
 * @author kill
 */
class TazPlugin {

    static int getMinPatternLength() {
        return 3;
    }

    static int getSearchDelay() {
        return 300;
    }

    static boolean isGroupResults() {
        return false;
    }

    static boolean isShowExt() {
        return true;
    }

    static boolean isShowPath() {
        return true;
    }

    static boolean isShowSize() {
        return false;
    }

    static boolean isSmartMatch() {
        return true;
    }

    static boolean isSortResults() {
        return true;
    }

}
