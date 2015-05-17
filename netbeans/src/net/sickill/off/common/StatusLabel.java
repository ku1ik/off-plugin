package net.sickill.off.common;

import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

class StatusLabel extends JLabel {

  private Icon spinnerIcon = new ImageIcon(OffPanel.class.getResource("spinner.gif"));

  public StatusLabel() {
    super(" ");
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(super.getPreferredSize().width, 16);
  }

  public void setIndexing(boolean indexing) {
    if (indexing) {
      setText("Indexing project files, please wait...");
      setIcon(spinnerIcon);
    }
    else {
      setText(" ");
      setIcon(null);
    }
  }

}
