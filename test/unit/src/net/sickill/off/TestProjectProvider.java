/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off;

import java.util.ArrayList;
import java.util.Collection;
import net.sickill.off.OffListModel;
import net.sickill.off.ProjectFile;
import net.sickill.off.ProjectProvider;

/**
 *
 * @author kill
 */
class TestProjectProvider implements ProjectProvider {
    static String PROJECT_ROOT = "/home/kill/workspace/project-x/";
    private Collection<ProjectFile> col;

    public TestProjectProvider() {
        col = new ArrayList<ProjectFile>();
        String[] names = {                 "README",
                                           "Rakefile",
                                "app/models/user.rb",
                                "app/models/topic.rb",
                                       "lib/tags.rb",
                                      "spec/helper.rb",
                                      "spec/hlr.rb",
                           "app/views/users/index.html",
                           "app/controllers/users_controller.rb",
                          "app/views/topics/index.html",
                               "spec/models/user_test.rb",
                                      "spec/zone.cfg",
                        "app/views/elements/index.html",
                                "app/models/user_topic.rb",
        };

        for (String name : names) {
            col.add(new TestProjectFile(this, name));
        }
    }

    public Collection<ProjectFile> getProjectFiles() {
        return col;
    }

    public String getProjectRootPath() {
        return PROJECT_ROOT;
    }

    public void setModel(OffListModel aThis) {
    }

    public ProjectFile getFileByName(String n) {
        for (ProjectFile pf : col) {
            if (pf.getName().equals(n))
                return pf;
        }
        return null;
    }

}
