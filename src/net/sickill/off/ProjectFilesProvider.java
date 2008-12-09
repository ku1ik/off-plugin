/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off;

/**
 *
 * @author kill
 */
public interface ProjectFilesProvider {

    public Iterable<ProjectFile> getProjectFiles();
    public String getProjectRoot();
}
