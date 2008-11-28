/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.taz.jedit;

import net.sickill.taz.*;
import javax.swing.Icon;

/**
 *
 * @author kill
 */
public class JEditProjectViewerFile implements ProjectFile {

    public int getGroupPriority() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Icon getIcon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getPathInProject() {
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

}
