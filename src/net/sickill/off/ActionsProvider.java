/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off;

/**
 *
 * @author kill
 */
public interface ActionsProvider {
    public void openFile(ProjectFile pf);
    public void openFile(ProjectFile pf, int lineNo);
    public void closeWindow();
}
