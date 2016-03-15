package net.sickill.off.netbeans;

import net.sickill.off.common.*;
import java.beans.BeanInfo;
import java.util.Objects;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;

/**
 * @author sickill
 */
class NetbeansProjectFile implements ProjectFile {

    private String fullPath;
    private String name;
    private Icon icon;

    public NetbeansProjectFile(FileObject fileObject) {
        fullPath = fileObject.getPath();
        name = fileObject.getNameExt();

        try {
            DataObject dataObj = DataObject.find(fileObject);
            Node n = dataObj.getNodeDelegate();
            icon = new ImageIcon(n.getIcon(BeanInfo.ICON_COLOR_16x16));
        } catch (DataObjectNotFoundException ex) {
            icon = null;
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final NetbeansProjectFile other = (NetbeansProjectFile) obj;
        if (!Objects.equals(this.fullPath, other.fullPath)) {
            return false;
        }
        return true;
    }

    @Override
    public Icon getIcon() {
        return icon;
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public String getFullPath() {
        return fullPath;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.fullPath);
        return hash;
    }

    @Override
    public String toString() {
        return "NetbeansProjectFile{" + "fullPath=" + fullPath + '}';
    }

}
