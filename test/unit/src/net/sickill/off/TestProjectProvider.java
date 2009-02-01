/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off;

import java.util.Collection;
import net.sickill.off.OffListModel;
import net.sickill.off.ProjectFile;
import net.sickill.off.ProjectProvider;

/**
 *
 * @author kill
 */
class TestProjectProvider implements ProjectProvider {

    public TestProjectProvider() {
    }

    public Collection<ProjectFile> getProjectFiles() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getProjectRootPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setModel(OffListModel aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
