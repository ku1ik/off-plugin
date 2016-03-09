package net.sickill.off.netbeans;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import org.netbeans.api.project.ProjectUtils;

/**
 *
 * @author markiewb
 */
class ProjectCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        Component component = super.getListCellRendererComponent(
                list, value, index, isSelected, cellHasFocus);
        if (component instanceof JLabel) {
            JLabel label = (JLabel) component;
            if (value instanceof ProjectItem) {
                ProjectItem item = (ProjectItem) value;
                label.setIcon(ProjectUtils.getInformation(item.getProject()).getIcon());
            }
        }
        return component;
    }

}
