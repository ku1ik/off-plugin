package net.sickill.off.netbeans;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import net.sickill.off.common.IDE;
import net.sickill.off.common.ProjectFile;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.cookies.OpenCookie;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.text.NbDocument;
import org.openide.util.Exceptions;

/**
 *
 * @author kill
 */
public class NetbeansIDE extends IDE implements ItemListener {
    private JComboBox projectChooser;

    @Override
    public void onFocus() {
        Project selected = NetbeansProject.getInstance().getSelectedProject();
        projectChooser.removeAllItems();
        projectChooser.removeItemListener(this);
        for (Project p : OpenProjects.getDefault().getOpenProjects()) {
            ProjectItem item = new ProjectItem(p);
            projectChooser.addItem(item);
            if (selected == p) {
                projectChooser.setSelectedItem(item);
            }
        }
        projectChooser.addItemListener(this);
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            NetbeansProject.getInstance().setSelectedProject(((ProjectItem)e.getItem()).getProject());
            off.getPatternInput().requestFocus();
        }
    }

    @Override
    public void onIndexing(boolean indexing) {
        projectChooser.setEnabled(!indexing);
    }

    @Override
    public void addCustomControls(JPanel panel) {
        projectChooser = new JComboBox();
        panel.add(projectChooser, BorderLayout.CENTER);
        projectChooser.addItemListener(this);
        panel.add(new JLabel("Project "), BorderLayout.WEST);
    }

    public void openFile(ProjectFile pf, int lineNo) {
        try {
            DataObject data = DataObject.find(((NetbeansProjectFile)pf).getFileObject());
            data.getLookup().lookup(OpenCookie.class).open();
            if (lineNo > -1)
                performGoto(lineNo);
        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    public void openFile(ProjectFile pf) {
        openFile(pf, -1);
    }

    private boolean performGoto(int lineNo) {
        JTextComponent editor = EditorRegistry.lastFocusedComponent();
        Document doc = editor.getDocument();
        editor.setCaretPosition(NbDocument.findLineOffset((StyledDocument)doc, lineNo-1));
        return true;
    }

    public void closeWindow() {
        ((NetbeansDialog)dialog).closeDialog();
    }

}
