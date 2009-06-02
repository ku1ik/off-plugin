/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.jedit;

import net.sickill.off.common.*;
import javax.swing.Icon;

/**
 *
 * @author kill
 */
public class JEditProjectViewerFile extends ProjectFile {
    public JEditProjectViewerFile(AbstractProject pp) {
        super(pp);
    }

    public int getGroupPriority() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Icon getIcon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getFullPath() {
//				int start = project.getRootPath().length() + 1;
//				int end = file.getNodePath().length() - file.getName().length();
//				if (end > start) {
//					end -= 1;
//				}
         //.getNodePath().substring(start, end);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public long getSize() {
        //getFile().getLength());
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void rename(String newName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
