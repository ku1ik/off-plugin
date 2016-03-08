package net.sickill.off.netbeans;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import net.sickill.off.common.IDE;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.text.NbDocument;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;

/**
 * @author sickill
 */
@NbBundle.Messages({"lblProject=Project ", "btnReindex=Reindex"})
public class NetbeansIDE extends IDE {

    private JButton btnReindex;
    private JComponent focusedComponentAfterIndexing;
    private JLabel label;
    private JComboBox<ProjectItem> projectChooser;
    final ProjectActionListener projectActionListener = new ProjectActionListener();
    final ProjectPopupMenuListener projectPopupMenuListener = new ProjectPopupMenuListener();
    final ReindexActionListener reindexActionListener = new ReindexActionListener();

    @Override
    public void onFocus() {

        projectChooser.removeActionListener(projectActionListener);
        projectChooser.removePopupMenuListener(projectPopupMenuListener);
        btnReindex.removeActionListener(reindexActionListener);

        Project current = NetbeansProject.getInstance().getCurrentProject();
        projectChooser.removeAllItems();

        Set<Project> projects = getAllOpenedProjectsSortedByName();

        for (Project p : projects) {
            ProjectItem item = new ProjectItem(p);
            projectChooser.addItem(item);
        }
        int itemCount = projectChooser.getItemCount();
        ProjectItem projectItemForCurrentProject = null;
        for (int i = 0; i < itemCount; i++) {
            ProjectItem item = projectChooser.getItemAt(i);
            if (item.getProject() == current) {
                projectItemForCurrentProject = item;
                break;
            }
        }

        // add listener
        projectChooser.addActionListener(projectActionListener);
        projectChooser.addPopupMenuListener(projectPopupMenuListener);
        btnReindex.addActionListener(reindexActionListener);
        // selecting a project, will trigger the listener and thus the indexing
        if (null != projectItemForCurrentProject) {
            projectChooser.setSelectedItem(projectItemForCurrentProject);
        } else {
            projectChooser.setSelectedItem(0);
        }
    }

    Component previousFocusOwner;

    @Override
    public void onIndexing(boolean indexing) {
        if (indexing) {
            previousFocusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        } else {
            if (null != focusedComponentAfterIndexing) {
                focusedComponentAfterIndexing.requestFocusInWindow();
            } else if (null != previousFocusOwner) {
                previousFocusOwner.requestFocusInWindow();
            }
            previousFocusOwner = null;
            focusedComponentAfterIndexing = null;
        }
    }

    @Override
    public void addCustomControls(JPanel panel) {
        projectChooser = new JComboBox<>();
        btnReindex = new JButton(Bundle.btnReindex());
        btnReindex.setMnemonic(KeyEvent.VK_R);
        label = new JLabel(Bundle.lblProject());
        label.setDisplayedMnemonic(KeyEvent.VK_P);
        label.setLabelFor(projectChooser);
        panel.add(projectChooser, BorderLayout.CENTER);
        panel.add(label, BorderLayout.WEST);
        panel.add(btnReindex, BorderLayout.EAST);
    }

    @Override
    public void openFile(String fullPath, int lineNo) {
        try {
            FileObject fo = FileUtil.toFileObject(FileUtil.normalizeFile(new File(fullPath)));

            DataObject data = DataObject.find(fo);
            data.getLookup().lookup(OpenCookie.class).open();

            if (lineNo > -1) {
                performGoto(lineNo);
            }
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    private Set<Project> getAllOpenedProjectsSortedByName() {
        Set<Project> projects = new TreeSet<>(new Comparator<Project>() {
            @Override
            public int compare(Project o1, Project o2) {
                String a = "" + ProjectUtils.getInformation(o1).getDisplayName();
                String b = "" + ProjectUtils.getInformation(o2).getDisplayName();
                return a.compareToIgnoreCase(b);
            }
        });
        final Project[] openProjects = OpenProjects.getDefault().getOpenProjects();
        if (openProjects != null && openProjects.length > 0) {
            projects.addAll(Arrays.asList(openProjects));
        }
        return projects;
    }

    private boolean performGoto(int lineNo) {
        //FIXME replace with better API calls
        JTextComponent editor = EditorRegistry.lastFocusedComponent();
        Document doc = editor.getDocument();
        editor.setCaretPosition(NbDocument.findLineOffset((StyledDocument) doc, lineNo - 1));
        return true;
    }

    @Override
    public void closeWindow() {
        ((NetbeansDialog) dialog).closeDialog();
    }

    private class ProjectActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (projectChooser.isPopupVisible()) {
                // do not change selection, if popup is visible -> it is handled separately
            } else {
                ProjectItem cbItem = (ProjectItem) projectChooser.getSelectedItem();
                if (NetbeansProject.getInstance().getSelectedProject() != cbItem.getProject()) {
                    NetbeansProject.getInstance().setSelectedProject(cbItem.getProject());
                }
            }
        }
    }

    private class ProjectPopupMenuListener implements PopupMenuListener {

        Object previouslySelected;

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            previouslySelected = null;
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            ProjectItem cbItem = (ProjectItem) projectChooser.getSelectedItem();
            if (previouslySelected != cbItem) {
                NetbeansProject.getInstance().setSelectedProject(cbItem.getProject());
            }
            previouslySelected = null;
        }

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            previouslySelected = (ProjectItem) projectChooser.getSelectedItem();
        }
    }

    private class ReindexActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            NetbeansProject instance = NetbeansProject.getInstance();
            if (null != instance) {
                // focus the search field after indexing
                focusedComponentAfterIndexing = off.getPatternInput();
                instance.fetchProjectFiles();
            }
        }
    }
}
