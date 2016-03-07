package net.sickill.off.netbeans;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
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

    @Override
    public void onFocus() {
        Project current = NetbeansProject.getInstance().getCurrentProject();
        projectChooser.removeAllItems();

        for (Project p : OpenProjects.getDefault().getOpenProjects()) {
            ProjectItem item = new ProjectItem(p);
            projectChooser.addItem(item);

            if (current == p) {
                projectChooser.setSelectedItem(item);
            }
        }
        projectChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (projectChooser.isPopupVisible()) {
                    // do not change selection, if popup is visible -> it is handled separately
                } else {
                    ProjectItem cbItem = (ProjectItem) projectChooser.getSelectedItem();
                    setProject(cbItem);
                }
            }
        });

        projectChooser.addPopupMenuListener(new PopupMenuListener() {
            Object previouslySelected;

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                previouslySelected = null;
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                ProjectItem cbItem = (ProjectItem) projectChooser.getSelectedItem();
                if (previouslySelected != cbItem) {
                    setProject(cbItem);
                }
                previouslySelected = null;
            }

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                previouslySelected = (ProjectItem) projectChooser.getSelectedItem();
            }
        });

        btnReindex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NetbeansProject instance = NetbeansProject.getInstance();
                if (null != instance) {
                    // focus the search field after indexing
                    focusedComponentAfterIndexing = off.getPatternInput();
                    instance.fetchProjectFiles();
                }
            }
        });
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

    private void setProject(ProjectItem cbItem) {
        final NetbeansProject instance = NetbeansProject.getInstance();
        if (null != instance && null != cbItem && null != cbItem.getProject()) {
            if (instance.getSelectedProject() != cbItem.getProject()) {
                instance.setSelectedProject(cbItem.getProject());
            }
        }
    }
}
