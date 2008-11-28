/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.taz;

import javax.swing.Icon;

/**
 *
 * @author kill
 */
public interface ProjectFile {
    public int getGroupPriority();

    public Icon getIcon();

    public String getName();

    public String getPathInProject();

    public long getSize();
}
