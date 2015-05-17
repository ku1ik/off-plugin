package net.sickill.off.netbeans;

import net.sickill.off.common.*;
import java.beans.BeanInfo;
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
    }
    catch (DataObjectNotFoundException ex) {
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

  @Override
  public Icon getIcon() {
    return icon;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getFullPath() {
    return fullPath;
  }

  @Override
  public long getSize() {
    return size;
  }

  @Override
  public void rename(String newName) {
    updateName();
  }

  @Override
  public Object getId() {
    return fileObject;
  }

}
