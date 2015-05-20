package net.sickill.off.common;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JList;

/**
 * @author sickill
 */
public class OffList extends JList<OffListElement> implements MouseListener, KeyListener {

  private static final long serialVersionUID = -3358863446749199157L;
  private OffPanel off;

  public OffList(OffPanel off, OffListModel listModel) {
    super(listModel);
    this.off = off;
    setCellRenderer(new OffColumnCellRenderer());
    addKeyListener(this);
    addMouseListener(this);
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      off.openSelected();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) { }

  @Override
  public void keyTyped(KeyEvent e) { }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (e.getClickCount() == 2) {
      off.openSelected();
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) { }

  @Override
  public void mouseExited(MouseEvent e) { }

  @Override
  public void mousePressed(MouseEvent e) { }

  @Override
  public void mouseReleased(MouseEvent e) { }

}
