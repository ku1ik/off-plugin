/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.common;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
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
public class OffPanel extends JPanel implements KeyListener {
    // UI
	private OffTextField patternInput;
	private OffList resultsList;
	private OffListModel listModel;
	private JLabel statusBar;

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

    void setIndexing(boolean indexing) {
        if (indexing) {
            patternInput.setEnabled(false);
            ide.onIndexing(true);
            statusBar.setText("Indexing project files, please wait...");
        } else {
            patternInput.setEnabled(true);
            ide.onIndexing(false);
            statusBar.setText(" ");
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
		statusBar = new JLabel(" ");
		pnlStatus.add(statusBar, BorderLayout.EAST);
        pnlSouth.add(pnlStatus, BorderLayout.SOUTH);
        ide.addCustomControls(pnlSouth);

        add(pnlSouth, BorderLayout.SOUTH);

		listModel = new OffListModel(settings, this);

        project.init(listModel);
		resultsList = new OffList(this, listModel);

		JScrollPane scroller = new JScrollPane(resultsList);
		add(scroller, BorderLayout.CENTER);

        pnlSouth.add(new JLabel("Project "), BorderLayout.WEST);
        // projects combo

		// Add escape-key event handling to widgets
//		KeyHandler keyHandler = new KeyHandler();
		addKeyListener(this);
		patternInput.addKeyListener(this);
		resultsList.addKeyListener(this);
    }

    private void closeMainWindow() {
        ide.closeWindow();
    }

    public OffList getResultsList() {
		return resultsList;
	}

    public void openSelected() {
		// For enter keys pressed inside txtfilename
        closeMainWindow();
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
        if (listModel.setFilter(getFilePattern())) {
            patternInput.setSearchSuccess(true);
            resultsList.setSelectedIndex(0);
        } else {
            patternInput.setSearchSuccess(false);
        }

		statusBar.setText("Found " + listModel.getSize() + " files");
	}


    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            closeMainWindow();
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
