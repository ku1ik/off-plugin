package net.sickill.off.netbeans;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.DefaultKeyboardFocusManager;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
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
 * @author sickill
 */
public class NetbeansIDE extends IDE {

  private JComboBox<ProjectItem> projectChooser;

  @Override
  public void onFocus() {
    Project selected = NetbeansProject.getInstance().getSelectedProject();
    projectChooser.removeAllItems();
    

    for (Project p : OpenProjects.getDefault().getOpenProjects()) {
      ProjectItem item = new ProjectItem(p);
      projectChooser.addItem(item);

      if (selected == p) {
        projectChooser.setSelectedItem(item);
      }
    }
      projectChooser.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              if (projectChooser.isPopupVisible()) {
                // do not change selection, if popup is visible -> it is handled separately
              }else{
                  ProjectItem cbItem = (ProjectItem) projectChooser.getSelectedItem();
                  NetbeansProject.getInstance().setSelectedProject(cbItem.getProject());
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
                  NetbeansProject.getInstance().setSelectedProject(cbItem.getProject());
              }
              previouslySelected = null;
          }

          @Override
          public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
              previouslySelected = (ProjectItem) projectChooser.getSelectedItem();
          }
      });
  }

    Component previousFocusOwner;

    @Override
    public void onIndexing(boolean indexing) {
        if (indexing) {
            previousFocusOwner = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
            projectChooser.setEnabled(false);
        } else {
            projectChooser.setEnabled(true);
            if (null != previousFocusOwner) {
                previousFocusOwner.requestFocusInWindow();
            }
            previousFocusOwner = null;
        }
    }

  @Override
  public void addCustomControls(JPanel panel) {
    projectChooser = new JComboBox<>();
    panel.add(projectChooser, BorderLayout.CENTER);
    final JLabel label = new JLabel("Project ");
    label.setDisplayedMnemonic(KeyEvent.VK_P);
    label.setLabelFor(projectChooser);
    panel.add(label, BorderLayout.WEST);
  }

  @Override
  public void openFile(ProjectFile pf, int lineNo) {
    try {
      DataObject data = DataObject.find(((NetbeansProjectFile) pf).getFileObject());
      data.getLookup().lookup(OpenCookie.class).open();

      if (lineNo > -1) {
        performGoto(lineNo);
      }
    }
    catch (DataObjectNotFoundException ex) {
      Exceptions.printStackTrace(ex);
    }
  }

  @Override
  public void openFile(ProjectFile pf) {
    openFile(pf, -1);
  }

  private boolean performGoto(int lineNo) {
    JTextComponent editor = EditorRegistry.lastFocusedComponent();
    Document doc = editor.getDocument();
    editor.setCaretPosition(NbDocument.findLineOffset((StyledDocument) doc, lineNo - 1));
    return true;
  }

  @Override
  public void closeWindow() {
    ((NetbeansDialog) dialog).closeDialog();
  }

}
