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
    private FileObject fileObject;
    private String fullPath;
    private String name;
    private long size;
    private Icon icon;

    public NetbeansProjectFile(AbstractProject pp, FileObject fo) {
        super(pp);
        fileObject = fo;
        fullPath = fileObject.getPath();
        size = fileObject.getSize();
        updateName();

        try {
            DataObject dataObj = DataObject.find(fileObject);
            Node n = dataObj.getNodeDelegate();
            icon = new ImageIcon(n.getIcon(BeanInfo.ICON_COLOR_16x16));
        } catch (DataObjectNotFoundException ex) {
            icon = null;
            Exceptions.printStackTrace(ex);
        }
    }

    public void updateName() {
        name = fileObject.getNameExt();
    }

    public FileObject getFileObject() {
        return fileObject;
    }

    public int getGroupPriority() {
        return 0;
    }

    public Icon getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getFullPath() {
        return fullPath;
    }

    public long getSize() {
        return size;
    }

    @Override
    public void rename(String newName) {
        updateName();
    }

    public Object getId() {
        return fileObject;
    }
}
