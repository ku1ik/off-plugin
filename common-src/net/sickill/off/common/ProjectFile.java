/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.common;

import javax.swing.Icon;

/**
 *
 * @author kill
 */
public abstract class ProjectFile {
    protected AbstractProject project;

//    public abstract int getGroupPriority();
    public abstract Icon getIcon();
    public abstract String getName();
    public abstract String getFullPath();
    public abstract long getSize();
    public abstract void rename(String newName);
    public abstract Object getId();

    public ProjectFile(AbstractProject pp) {
        this.project = pp;
    }

    public String getDirectory() {
        String fullPath = getFullPath();
        int start = project.getProjectRootPath().length();
        int end = fullPath.length() - getName().length();
        return fullPath.substring(start, end);
    }

    public String getPathInProject() {
        return getDirectory() + getName();
    }

    String getExtension() {
        String name = getName();
        int index = name.lastIndexOf(".");
        return (index == -1) ? "" : name.substring(index + 1, name.length());
    }
}
