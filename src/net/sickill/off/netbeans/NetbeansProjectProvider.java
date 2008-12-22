/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.netbeans;

import java.util.Collection;
import net.sickill.off.*;
import net.sickill.off.netbeans.NetbeansProjectFile;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
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
public class NetbeansProjectProvider implements ProjectProvider {
    private static Collection<ProjectFile> projectFiles;

    public NetbeansProjectProvider() {
    }

    public Collection<ProjectFile> getProjectFiles() {
        //if (projectFiles == null) {
            Project p = OpenProjects.getDefault().getMainProject();
            List<FileObject> srcFolders = ProjectOperations.getDataFiles(p);
    //        ArrayList<ProjectFile> projectFiles = new ArrayList<ProjectFile>();
            HashMap<String, ProjectFile> projectFilesHash = new HashMap<String, ProjectFile>();

            //Logger logger = Logger.getLogger(NetbeansProjectProvider.class.getName());
    //        logger.log(Level.INFO, "dataFiles.size = " + srcFolders.size());

    //        String a = "";
            for (FileObject folder : srcFolders) {
                Enumeration<? extends FileObject> children = folder.getChildren(true);
                while (children.hasMoreElements()) {
                    FileObject fo = children.nextElement();
                    if (!fo.isFolder()) {
    //                    projectFiles.add(new NetbeansProjectFile(this, fo));
                        ProjectFile pf = new NetbeansProjectFile(this, fo);
                        if (!projectFilesHash.containsKey(pf.getFullPath())) {
                            projectFilesHash.put(pf.getFullPath(), pf);
                        }
    //                    a += fo.getPath()+";";
                    }
                }
            }
            //logger.log(Level.INFO, "dataFiles = " + a);
            projectFiles = projectFilesHash.values();
        //}
        return projectFiles;
    }

    public String getProjectRootPath() {
        return FileUtil.getFileDisplayName(OpenProjects.getDefault().getMainProject().getProjectDirectory());
    }
}
