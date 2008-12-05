/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.taz;

import net.sickill.taz.netbeans.NetbeansProjectFilesProvider;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
public class Taz extends JPanel {
	private TazTextField patternInput;
	private TazList resultsList;
	private JLabel statusBar;
	//private JDialog dialog = null;
	private TazListModel listModel;
    private Timer timer;
	private String previousFilePattern = "";
    private ActionsProvider actionsProvider;
    private Settings settings;
    private ProjectFilesProvider projectFiles;

    public Taz(ProjectFilesProvider pf) {
        this.projectFiles = pf;
        setupTaz();
    }

    public Settings getSettings() {
        return this.settings;
    }

    public void setSettings(Settings s) {
        this.settings = s;
    }

    public void setActionsProvider(ActionsProvider a) {
        this.actionsProvider = a;
    }

    private void setupTaz() {
		setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));

		patternInput = new TazTextField(this);

		// Below steps are used to receive notifications of the
		// KeyStrokes we are interested in unlike
		// ActionListener/KeyListener
		// which is fired irrespective of the kind of KeyStroke.

		JPanel pnlNorth = new JPanel(new BorderLayout());

		URL url = Taz.class.getResource("search.png");
		JLabel searchIcon = new JLabel(new ImageIcon(url));
		pnlNorth.add(searchIcon, BorderLayout.WEST);
		pnlNorth.add(patternInput, BorderLayout.CENTER);

		this.add(pnlNorth, BorderLayout.NORTH);

		this.listModel = new TazListModel(this.settings, this.projectFiles);

		resultsList = new TazList(this, listModel);

		//this.listModel.setList(resultsList);

		JScrollPane scroller = new JScrollPane(resultsList);
		this.add(scroller, BorderLayout.CENTER);

		// status bar
		JPanel pnlSouth = new JPanel(new BorderLayout());
		statusBar = new JLabel(" ");
		pnlSouth.add(statusBar, BorderLayout.EAST);
		this.add(pnlSouth, BorderLayout.SOUTH);

		// Add escape-key event handling to widgets
		KeyHandler keyHandler = new KeyHandler();
		this.addKeyListener(keyHandler);
		patternInput.addKeyListener(keyHandler);
		resultsList.addKeyListener(keyHandler);

    }

    private void closeMainWindow() {
        actionsProvider.closeWindow();
    }

    public TazList getResultsList() {
		return resultsList;
	}

    public void openSelected() {
		// For enter keys pressed inside txtfilename

		int selectedIndex = resultsList.getSelectedIndex();
		int listSize = resultsList.getModel().getSize();

		//Log.log(Log.DEBUG, this.getClass(), "open selected...");

		if (selectedIndex != -1 && listSize != 0 && selectedIndex < listSize) {
			int lineNo = getLineNumber();
			closeMainWindow();
            actionsProvider.openFile(((TazListElement) resultsList.getSelectedValue()).getFile(), lineNo);
		}
	}

	void startSearching() {
//		Log.log(Log.DEBUG, this, "startSearching");
		if (timer == null) {
//			Log.log(Log.DEBUG, this, "starting new timer");
			timer = new Timer(settings.getSearchDelay(), new SearchAction());
			timer.setRepeats(false);
			timer.start();
		} else {
//			Log.log(Log.DEBUG, this, "restarting old timer");
			timer.restart();
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
		String filePattern = getFilePattern();
		if (filePattern.equals(previousFilePattern)) {
			return;
		} else {
			previousFilePattern = filePattern;

			if (filePattern.equals("*") || filePattern.length() < settings.getMinPatternLength()) {
				this.listModel.setFilter("*");
				patternInput.setSearchSuccess(true);
				resultsList.setSelectedIndex(0);
			} else {
				this.listModel.setFilter(filePattern);
				if (this.listModel.getSize() > 0) {
					patternInput.setSearchSuccess(true);
					resultsList.setSelectedIndex(0);
				} else {
					patternInput.setSearchSuccess(false);
				}
			}
		}

		statusBar.setText("Found " + listModel.getSize() + " files");
		//Log.log(Log.DEBUG, this.getClass(), "found: "
		//		+ this.listModel.getSize());
	}

    class SearchAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //Log.log(Log.DEBUG, this, "actionPerformed");
            search();
            timer = null;
        }
    }


	class KeyHandler extends KeyAdapter {
		public void keyPressed(KeyEvent evt) {
			if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
				closeMainWindow();
				evt.consume();
			}
		}
	}
}
