/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.taz;

/**
 *
 * @author kill
 */
public interface ProjectFilesProvider {

    public Iterable<ProjectFile> getProjectFiles();
    public String getProjectRoot();
}
