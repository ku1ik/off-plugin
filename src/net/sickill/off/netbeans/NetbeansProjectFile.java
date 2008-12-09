/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.netbeans;

import net.sickill.off.*;
import java.beans.BeanInfo;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 *
 * @author kill
 */
class NetbeansProjectFile extends ProjectFile {
    FileObject fileObject;

    public NetbeansProjectFile(ProjectProvider pp, FileObject fo) {
        super(pp);
        this.fileObject = fo;
    }

    public FileObject getFileObject() {
        return fileObject;
    }

    public int getGroupPriority() {
        return 0;
    }

    public Icon getIcon() {
        try {
            DataObject dataObj = DataObject.find(fileObject);
            Node n = dataObj.getNodeDelegate();
            return new ImageIcon(n.getIcon(BeanInfo.ICON_COLOR_16x16));
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    public String getName() {
        return fileObject.getNameExt();
    }

    public String getFullPath() {
        return FileUtil.getFileDisplayName(fileObject);
    }

    public long getSize() {
        return fileObject.getSize();
    }
}
