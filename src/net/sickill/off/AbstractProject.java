/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off;

import java.util.Collection;

/**
 *
 * @author kill
 */
public interface AbstractProject {

    public void init(OffListModel model);
//    public Collection<ProjectFile> getProjectFiles();
    public String getProjectRootPath();
}
