/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.netbeans;

import net.sickill.off.*;
import net.sickill.off.netbeans.NetbeansProjectFile;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.netbeans.spi.project.support.ProjectOperations;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author kill
 */
public class NetbeansProjectFilesProvider implements ProjectFilesProvider {

    public NetbeansProjectFilesProvider() {
    }

    public Iterable<ProjectFile> getProjectFiles() {
        Project p = OpenProjects.getDefault().getMainProject();
        List<FileObject> srcFolders = ProjectOperations.getDataFiles(p);
        ArrayList<ProjectFile> projectFiles = new ArrayList<ProjectFile>();

        Logger logger = Logger.getLogger(NetbeansProjectFilesProvider.class.getName());
//        logger.log(Level.INFO, "dataFiles.size = " + srcFolders.size());

//        String a = "";
        for (FileObject folder : srcFolders) {
            Enumeration<? extends FileObject> children = folder.getChildren(true);
            while (children.hasMoreElements()) {
                FileObject fo = children.nextElement();
                if (!fo.isFolder()) {
                    projectFiles.add(new NetbeansProjectFile(this, fo));
//                    a += fo.getPath()+";";
                }
            }
        }
        //logger.log(Level.INFO, "dataFiles = " + a);
        return projectFiles;
    }

    public String getProjectRoot() {
        Project p = OpenProjects.getDefault().getMainProject();
        return FileUtil.getFileDisplayName(p.getProjectDirectory());
    }

}
