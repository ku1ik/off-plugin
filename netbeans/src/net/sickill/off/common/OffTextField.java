package net.sickill.off.common;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author sickill
 */
public class OffTextField extends JTextField implements DocumentListener, ActionListener {

  private static final long serialVersionUID = 5284529373291656641L;
  private OffPanel off;
  private Color origColor;

  public OffTextField(OffPanel off) {
    super();
    this.off = off;
    setup();
  }

  private void setup() {
    origColor = getForeground();
    getDocument().addDocumentListener(this);
    addActionListener(this);

    Action downAction = new AbstractAction("DownArrow") {
      @Override
      public void actionPerformed(ActionEvent e) {
        off.getResultsList().moveListDown();
      }
    };

    Action upAction = new AbstractAction("UpArrow") {
      @Override
      public void actionPerformed(ActionEvent e) {
        off.getResultsList().moveListUp();
      }
    };

    Action pageUpAction = new AbstractAction("PageUp") {
      @Override
      public void actionPerformed(ActionEvent e) {
        off.getResultsList().moveToStartOfList();
      }
    };

    Action pageDownAction = new AbstractAction("PageDown") {
      @Override
      public void actionPerformed(ActionEvent e) {
        off.getResultsList().moveToEndOfList();
      }
    };

    InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

    KeyStroke upArrow = KeyStroke.getKeyStroke("UP");
    KeyStroke downArrow = KeyStroke.getKeyStroke("DOWN");

    KeyStroke pageUp = KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_UP, 0);
    KeyStroke pageDown = KeyStroke.getKeyStroke(KeyEvent.VK_PAGE_DOWN, 0);

    inputMap.put(upArrow, upAction.getValue(Action.NAME));
    inputMap.put(downArrow, downAction.getValue(Action.NAME));
    inputMap.put(pageDown, pageDownAction.getValue(Action.NAME));
    inputMap.put(pageUp, pageUpAction.getValue(Action.NAME));

    ActionMap actionMap = getActionMap();
    actionMap.put(upAction.getValue(Action.NAME), upAction);
    actionMap.put(downAction.getValue(Action.NAME), downAction);
    actionMap.put(pageUpAction.getValue(Action.NAME), pageUpAction);
    actionMap.put(pageDownAction.getValue(Action.NAME), pageDownAction);
  }

  @Override
  protected void processKeyEvent(KeyEvent evt) {
    if (isEnabled()) {
      if (evt.getID() == KeyEvent.KEY_PRESSED) {
        if (evt.getKeyCode() == KeyEvent.VK_UP && evt.isControlDown()) {
          super.processKeyEvent(evt);

          // Can't call evt.consume() & continue the flow
          // because HistoryTextField does not check for
          // isConsumed() & we will see funny results of
          // double action execution.
          return;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_DOWN && evt.isControlDown()) {
          super.processKeyEvent(evt);
          return;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_TAB && evt.isControlDown()) {
          super.processKeyEvent(evt);
          return;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_UP && evt.getModifiers() == 0) {
          processKeyBinding(
            KeyStroke.getKeyStroke("UP"),
            evt,
            JComponent.WHEN_IN_FOCUSED_WINDOW,
            evt.getID() == KeyEvent.KEY_PRESSED
          );

          evt.consume();
          return;
        }
        else if (evt.getKeyCode() == KeyEvent.VK_DOWN && evt.getModifiers() == 0) {
          evt.consume();

          processKeyBinding(
            KeyStroke.getKeyStroke("DOWN"),
            evt,
            JComponent.WHEN_IN_FOCUSED_WINDOW,
            evt.getID() == KeyEvent.KEY_PRESSED
          );

          return;
        }
      }

      super.processKeyEvent(evt);
    }
  }

  @Override
  public void changedUpdate(DocumentEvent e) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        off.startSearching();
      }
    });
  }

  @Override
  public void insertUpdate(DocumentEvent e) {
    changedUpdate(e);
  }

  @Override
  public void removeUpdate(DocumentEvent e) {
    changedUpdate(e);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    off.openSelected();
  }

  public void setSearchSuccess(boolean success) {
    if (success) {
      if (getForeground() == Color.RED) {
        setForeground(origColor);
      }
    }
    else {
      setForeground(Color.RED);
    }
  }

}
