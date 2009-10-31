/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.common;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author kill
 */
public class OffPanel extends JPanel implements KeyListener, IndexingListener, SearchStatusListener {
	// UI
	private OffTextField patternInput;
	private OffList resultsList;
	private OffListModel listModel;
	private StatusLabel statusBar;
	private boolean indexing = false;

	// providers
	private Settings settings;
	private AbstractProject project;
	private IDE ide;

	private Timer timer;

	public OffPanel(IDE i, Settings s, AbstractProject p) {
		this.ide = i;
		this.settings = s;
		this.project = p;
		this.ide.setPanel(this);
		build();
	}

	public OffTextField getPatternInput() {
		return patternInput;
	}

	public void setIndexing(boolean indexing) {
		this.indexing = indexing;
		if (indexing) {
			ide.onIndexing(true);
			statusBar.setIndexing(true);
		} else {
			ide.onIndexing(false);
			statusBar.setIndexing(false);
		}
	}

	private void build() {
		setLayout(new BorderLayout(0, 5));
		setBorder(new EmptyBorder(5, 5, 5, 5));

		patternInput = new OffTextField(this);

		// Below steps are used to receive notifications of the
		// KeyStrokes we are interested in unlike
		// ActionListener/KeyListener
		// which is fired irrespective of the kind of KeyStroke.

		JPanel pnlNorth = new JPanel(new BorderLayout());

		URL url = OffPanel.class.getResource("search.png");
		JLabel searchIcon = new JLabel(new ImageIcon(url));
		pnlNorth.add(searchIcon, BorderLayout.WEST);
		pnlNorth.add(patternInput, BorderLayout.CENTER);

		add(pnlNorth, BorderLayout.NORTH);

		// status bar
		JPanel pnlSouth = new JPanel(new BorderLayout(5, 5));
		JPanel pnlStatus = new JPanel(new BorderLayout());
		statusBar = new StatusLabel();
		pnlStatus.add(statusBar, BorderLayout.EAST);
		pnlSouth.add(pnlStatus, BorderLayout.SOUTH);
		ide.addCustomControls(pnlSouth);

		add(pnlSouth, BorderLayout.SOUTH);

		listModel = new OffListModel(settings, this, this);

		project.init(listModel);
		resultsList = new OffList(this, listModel);

		JScrollPane scroller = new JScrollPane(resultsList);
		add(scroller, BorderLayout.CENTER);

		// Add escape-key event handling to widgets
		addKeyListener(this);
		patternInput.addKeyListener(this);
		resultsList.addKeyListener(this);
	}

	public OffList getResultsList() {
		return resultsList;
	}

	public void openSelected() {
		// For enter keys pressed inside txtfilename
		ide.closeWindow();
		for (Object o : resultsList.getSelectedValues()) {
	//			int lineNo = getLineNumber();
			ProjectFile pf = ((OffListElement)o).getFile();
			listModel.incrementAccessCounter(pf);
			ide.openFile(pf);
		}
	}

	void startSearching() {
		if (timer == null) {
			timer = new Timer((int)(settings.getSearchDelay() * 1000.0), new SearchAction());
			timer.setRepeats(false);
			timer.start();
		} else {
			timer.restart();
		}
	}

	public void focusOnDefaultComponent() {
		ide.onFocus();
		if (settings.isClearOnOpen()) {
			patternInput.setText("");
		} else {
			patternInput.selectAll();
			patternInput.requestFocus();
		}
	}

	private String getFilePattern() {
		String[] parts = patternInput.getText().split(":", 2);
		return parts[0];
	}

	private int getLineNumber() {
		String[] parts = patternInput.getText().split(":", 2);
		int lineNo;
		try {
			lineNo = parts.length > 1 ? Integer.parseInt(parts[1]) : -1;
		} catch (NumberFormatException e) {
			lineNo = -1;
		}
		return lineNo;
	}

	private void search() {
		try {
			listModel.setFilter(getFilePattern());
			if (indexing) return;
			listModel.refilter();
			String numberOfResults = listModel.getSize() == OffListModel.MAX_RESULTS ? "more than " + OffListModel.MAX_RESULTS : "" + listModel.getSize();
			statusBar.setText("Found " + numberOfResults + " files");
		} catch (Exception e) {
		  e.printStackTrace();
		}
	}

	public void setSearchSuccess(boolean success) {
		if (success) {
			patternInput.setSearchSuccess(true);
			resultsList.setSelectedIndex(0);
		} else {
			patternInput.setSearchSuccess(false);
		}
	}


	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			ide.closeWindow();
			e.consume();
		}
	}

	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

	class SearchAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			search();
			timer = null;
		}
	}

}
