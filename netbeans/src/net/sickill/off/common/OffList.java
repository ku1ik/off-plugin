package net.sickill.off.common;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JList;

/**
 *
 * @author kill
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

    /** Description of the Method */
    void moveListDown() {
        int selectedIndex = getSelectedIndex();
        int listSize = getModel().getSize();

        if (listSize > 1 && selectedIndex >= 0
                && (selectedIndex + 1) < listSize) {
            selectedIndex++;
            setSelectedIndex(selectedIndex);
            ensureIndexIsVisible(selectedIndex);
        }
    }

    /** Description of the Method */
    void moveListUp() {
        int selectedIndex = getSelectedIndex();
        int listSize = getModel().getSize();

        if (listSize > 1 && (selectedIndex - 1) >= 0) {
            selectedIndex--;
            setSelectedIndex(selectedIndex);
            ensureIndexIsVisible(selectedIndex);
        }
    }

    void moveToStartOfList() {
        if (getModel().getSize() > 1) {
            setSelectedIndex(0);
            ensureIndexIsVisible(0);
        }
    }

    void moveToEndOfList() {
        int listSize = getModel().getSize();
        if (listSize > 1) {
            int endIndex = listSize - 1;
            setSelectedIndex(endIndex);
            ensureIndexIsVisible(endIndex);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            off.openSelected();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            off.openSelected();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}
