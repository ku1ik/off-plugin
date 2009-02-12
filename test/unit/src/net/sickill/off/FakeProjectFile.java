/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off;

import javax.swing.Icon;

/**
 *
 * @author kill
 */
class FakeProjectFile extends ProjectFile {
    private String pathInProject;
    private String name;

    public FakeProjectFile(AbstractProject pp, String path) {
        super(pp);
        this.pathInProject = path;
        this.name = path.substring(path.lastIndexOf("/") + 1);
    }

    @Override
    public int getGroupPriority() {
        return 0;
    }

    @Override
    public Icon getIcon() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFullPath() {
        return FakeProject.PROJECT_ROOT + pathInProject;
    }

    @Override
    public long getSize() {
        return 123456;
    }

}
