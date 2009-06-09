/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.jedit;

import net.sickill.off.common.*;
import javax.swing.Icon;
import projectviewer.vpt.VPTFile;
import projectviewer.vpt.VPTNode;

/**
 *
 * @author kill
 */
public class JEditProjectViewerFile extends ProjectFile {
    VPTFile file;
    
    public JEditProjectViewerFile(AbstractProject pp, VPTFile node) {
        super(pp);
        file = node;
    }

//    public int getGroupPriority() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

    public Icon getIcon() {
        return file.getIcon(false);
    }

    public String getName() {
        return file.getName();
    }

    public String getFullPath() {
        return file.getNodePath();
    }

    public long getSize() {
        return file.getFile().length();
    }

    @Override
    public void rename(String newName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getId() {
        return file;
    }

    public VPTFile getFile() {
        return file;
    }
}
