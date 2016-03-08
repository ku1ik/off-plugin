package net.sickill.off.common;

import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.sickill.off.netbeans.NetbeansSettings;

/**
 * @author sickill
 */
public class OffTextField extends JTextField implements DocumentListener, ActionListener {

    private static final long serialVersionUID = 5284529373291656641L;
    private KeyEventDispatcher toListDispatcher = new ToListDispatcher();
    private OffPanel off;
    private Color origColor;
    private int searchHistoryIndex = 0;

    public OffTextField(OffPanel off) {
        super();
        this.off = off;
        setup();
    }

    private void setup() {
        origColor = getForeground();
        getDocument().addDocumentListener(this);
        addActionListener(this);

        KeyboardFocusManager focusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        focusManager.addKeyEventDispatcher(toListDispatcher);
    }

    void close() {
        KeyboardFocusManager focusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        focusManager.removeKeyEventDispatcher(toListDispatcher);
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
        } else {
            setForeground(Color.RED);
        }
    }

    private class ToListDispatcher implements KeyEventDispatcher {

        @Override
        public boolean dispatchKeyEvent(KeyEvent evt) {
            final int keyCode = evt.getKeyCode();

            if (evt.getSource() == OffTextField.this && evt.getID() == KeyEvent.KEY_PRESSED
                    && (keyCode == KeyEvent.VK_UP
                    || keyCode == KeyEvent.VK_DOWN)
                    && evt.isControlDown()) {
                List<String> searchHistory = NetbeansSettings.getInstance().getSearchHistory();
                if (!searchHistory.isEmpty()) {
                    if (keyCode == KeyEvent.VK_UP) {
                        searchHistoryIndex = (searchHistoryIndex + 1) % searchHistory.size();
                    }
                    if (keyCode == KeyEvent.VK_DOWN) {
                        if (searchHistoryIndex == 0) {
                            searchHistoryIndex = searchHistory.size() - 1;
                        } else {
                            searchHistoryIndex = searchHistoryIndex - 1;
                        }
                    }
                    String text = searchHistory.get(searchHistoryIndex);
                    off.getPatternInput().setText(text);
                    evt.consume();
                }

            }

            if (evt.getSource() == OffTextField.this
                    && evt.getID() == KeyEvent.KEY_PRESSED && (keyCode == KeyEvent.VK_UP
                    || keyCode == KeyEvent.VK_DOWN
                    || keyCode == KeyEvent.VK_PAGE_UP
                    || keyCode == KeyEvent.VK_PAGE_DOWN
                    || evt.isControlDown() && (keyCode == KeyEvent.VK_SPACE
                    || keyCode == KeyEvent.VK_HOME
                    || keyCode == KeyEvent.VK_END))) {
                KeyboardFocusManager focusManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                focusManager.redispatchEvent(off.getResultsList(), evt);
                return true;
            }

            return false;
        }

    }

}
