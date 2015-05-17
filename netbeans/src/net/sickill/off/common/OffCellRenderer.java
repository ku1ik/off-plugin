package net.sickill.off.common;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 * @author sickill
 */
public class OffCellRenderer extends DefaultListCellRenderer {

  private static final long serialVersionUID = 4562757887321715315L;

  @Override
  public Component getListCellRendererComponent(
    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus
  ) {
    Component comp = super.getListCellRendererComponent(
      list, value, index, isSelected, cellHasFocus
    );

    OffListElement e = (OffListElement) value;

    final JLabel label = (JLabel) comp;
    label.setIcon(e.getIcon());
    label.setText(e.getFilename());
    return label;
  }

}
