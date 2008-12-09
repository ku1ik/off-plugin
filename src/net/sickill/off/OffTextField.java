/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off;

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
 *
 * @author kill
 */
public class OffTextField extends JTextField implements DocumentListener, ActionListener {

	private static final long serialVersionUID = 5284529373291656641L;
	private OffPanel taz;
	private Color origColor;

	public OffTextField(OffPanel taz) {
		super();
		this.taz = taz;
		setup();
	}

	private void setup() {
		origColor = getForeground();
		getDocument().addDocumentListener(this);
		addActionListener(this);

		Action down_Action = new AbstractAction("DownArrow") {
			public void actionPerformed(ActionEvent e) {
				taz.getResultsList().moveListDown();
			}
		};

		Action up_Action = new AbstractAction("UpArrow") {
			public void actionPerformed(ActionEvent e) {
				taz.getResultsList().moveListUp();
			}
		};

		Action page_up_Action = new AbstractAction("PageUp") {
			public void actionPerformed(ActionEvent e) {
				taz.getResultsList().moveToStartOfList();
			}
		};

		Action page_down_Action = new AbstractAction("PageDown") {
			public void actionPerformed(ActionEvent e) {
				taz.getResultsList().moveToEndOfList();
			}
		};


		InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

		KeyStroke up_arrow = KeyStroke.getKeyStroke("UP");
		KeyStroke down_arrow = KeyStroke.getKeyStroke("DOWN");

		KeyStroke page_up = KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_PAGE_UP, 0);
		KeyStroke page_down = KeyStroke.getKeyStroke(
				java.awt.event.KeyEvent.VK_PAGE_DOWN, 0);

		inputMap.put(up_arrow, up_Action.getValue(Action.NAME));
		inputMap.put(down_arrow, down_Action.getValue(Action.NAME));
		inputMap.put(page_down, page_down_Action.getValue(Action.NAME));
		inputMap.put(page_up, page_up_Action.getValue(Action.NAME));

		ActionMap actionMap = getActionMap();
		actionMap.put(up_Action.getValue(Action.NAME), up_Action);
		actionMap.put(down_Action.getValue(Action.NAME), down_Action);
		actionMap.put(page_up_Action.getValue(Action.NAME), page_up_Action);
		actionMap.put(page_down_Action.getValue(Action.NAME), page_down_Action);
	}

	protected void processKeyEvent(KeyEvent evt) {
		if (isEnabled()) {
			if (evt.getID() == KeyEvent.KEY_PRESSED) {
				if (evt.getKeyCode() == KeyEvent.VK_UP && evt.isControlDown()) {
					super.processKeyEvent(evt);
					return; // Can't call evt.consume() & continue the flow
							// because HistoryTextField does not check for
							// isConsumed() & we will see funny results of
							// double action execution.
				} else if (evt.getKeyCode() == KeyEvent.VK_DOWN
						&& evt.isControlDown()) {
					super.processKeyEvent(evt);
					return;
				} else if (evt.getKeyCode() == KeyEvent.VK_TAB
						&& evt.isControlDown()) {
					super.processKeyEvent(evt);
					return;
				} else if (evt.getKeyCode() == KeyEvent.VK_UP
						&& evt.getModifiers() == 0) {
					// Log.log(Log.DEBUG,this.getClass(),"Inside !consumed " +"
					// Key pressed? "+((evt.getID() == KeyEvent.KEY_PRESSED)));
					processKeyBinding(KeyStroke.getKeyStroke("UP"), evt,
							JComponent.WHEN_IN_FOCUSED_WINDOW,
							(evt.getID() == KeyEvent.KEY_PRESSED));
					evt.consume();
					return;
				} else if (evt.getKeyCode() == KeyEvent.VK_DOWN
						&& evt.getModifiers() == 0) {
					// Log.log(Log.DEBUG,this.getClass(),"Inside !consumed " +"
					// Key pressed? "+((evt.getID() == KeyEvent.KEY_PRESSED)));
					evt.consume();
					processKeyBinding(KeyStroke.getKeyStroke("DOWN"), evt,
							JComponent.WHEN_IN_FOCUSED_WINDOW,
							(evt.getID() == KeyEvent.KEY_PRESSED));
					return;
				}
			}
			super.processKeyEvent(evt);
		}
	}// End of processKeyEvent

	public void changedUpdate(DocumentEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				taz.startSearching();
			}
		});
	}

	public void insertUpdate(DocumentEvent e) {
		changedUpdate(e);
	}

	public void removeUpdate(DocumentEvent e) {
		changedUpdate(e);
	}

	public void actionPerformed(ActionEvent e) {
		taz.openSelected();
	}

	public void setSearchSuccess(boolean success) {
		if (success) {
			if (getForeground() == Color.red) {
				setForeground(origColor);
			}
		} else {
			setForeground(Color.red);
		}
	}
}