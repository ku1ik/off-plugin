/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off;

/**
 *
 * @author kill
 */
public interface ProjectProvider {

    public Iterable<ProjectFile> getProjectFiles();
    public String getProjectRootPath();
}
