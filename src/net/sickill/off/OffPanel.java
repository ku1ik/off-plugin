/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off;

import net.sickill.off.netbeans.ProjectItem;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import net.sickill.off.netbeans.NetbeansProject;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;

/**
 *
 * @author kill
 */
public class OffPanel extends JPanel implements ItemListener {
    // UI
	private OffTextField patternInput;
	private OffList resultsList;
	private OffListModel listModel;
	private JLabel statusBar;
    private JComboBox projectChooser;

    // providers
    private ActionsProvider actionsProvider;
    private Settings settings;
    private AbstractProject project;

    private Timer timer;

    public OffPanel(Settings s, AbstractProject p) {
        this.settings = s;
        this.project = p;
        build();
    }

    void setIndexing(boolean indexing) {
        if (indexing) {
            patternInput.setEnabled(false);
            statusBar.setText("Indexing project files, please wait...");
        } else {
            patternInput.setEnabled(true);
            statusBar.setText("Indexing finished");
        }
    }

    private void build() {
		setLayout(new BorderLayout());
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
		JPanel pnlSouth = new JPanel(new BorderLayout());
		statusBar = new JLabel(" ");
		pnlSouth.add(statusBar, BorderLayout.EAST);
		add(pnlSouth, BorderLayout.SOUTH);

		listModel = new OffListModel(settings, this);

        project.init(listModel);
		resultsList = new OffList(this, listModel);

		JScrollPane scroller = new JScrollPane(resultsList);
		add(scroller, BorderLayout.CENTER);

        // projects combo
        projectChooser = new JComboBox();
        add(projectChooser, BorderLayout.SOUTH);
        projectChooser.addItemListener(this);

		// Add escape-key event handling to widgets
		KeyHandler keyHandler = new KeyHandler();
		addKeyListener(keyHandler);
		patternInput.addKeyListener(keyHandler);
		resultsList.addKeyListener(keyHandler);
    }

    public void setActionsProvider(ActionsProvider ap) {
        this.actionsProvider = ap;
    }

    private void closeMainWindow() {
        actionsProvider.closeWindow();
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
            actionsProvider.openFile(pf);
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

    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            NetbeansProject.getInstance().setSelectedProject(((ProjectItem)e.getItem()).getProject());
            patternInput.requestFocus();
        }
    }

    public void focusOnDefaultComponent() {
        Project selected = NetbeansProject.getInstance().getSelectedProject();
        projectChooser.removeAllItems();
        projectChooser.removeItemListener(this);
        for (Project p : OpenProjects.getDefault().getOpenProjects()) {
            ProjectItem item = new ProjectItem(p);
            projectChooser.addItem(item);
            if (selected == p) {
                projectChooser.setSelectedItem(item);
            }
        }
        projectChooser.addItemListener(this);
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

    class SearchAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
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
