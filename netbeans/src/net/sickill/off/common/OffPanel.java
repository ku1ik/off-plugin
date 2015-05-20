package net.sickill.off.common;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

/**
 * @author sickill
 */
public class OffPanel extends JPanel implements KeyListener, IndexingListener, SearchStatusListener {

  private static final Logger logger = Logger.getLogger(OffPanel.class.getName());

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

  @Override
  public void setIndexing(boolean indexing) {
    this.indexing = indexing;

    if (indexing) {
      ide.onIndexing(true);
      statusBar.setIndexing(true);
    }
    else {
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

    listModel = project.getModel();

    listModel.setIndexingListener(this);
    listModel.setStatusListener(this);

    if (listModel.isIndexing()) {
      setIndexing(true);
    }

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

    for (OffListElement element : resultsList.getSelectedValuesList()) {
      ProjectFile pf = element.getFile();
      listModel.incrementAccessCounter(pf);
      ide.openFile(pf);
    }
  }

  void startSearching() {
    if (timer == null) {
      timer = new Timer((int) (settings.getSearchDelay() * 1000.0), new SearchAction());
      timer.setRepeats(false);
      timer.start();
    }
    else {
      timer.restart();
    }
  }

  public void focusOnDefaultComponent() {
    ide.onFocus();

    if (settings.isClearOnOpen()) {
      patternInput.setText("");
    }
    else {
      patternInput.selectAll();
      patternInput.requestFocus();
    }
  }

  private String getFilePattern() {
    String[] parts = patternInput.getText().split(":", 2);
    return parts[0];
  }

  private void search() {
    try {
      listModel.setFilter(getFilePattern());

      if (indexing) {
        return;
      }

      listModel.refilter();

      StringBuilder status = new StringBuilder();
      status.append("Found ");

      if (listModel.getSize() == OffListModel.MAX_RESULTS) {
        status.append("more than ");
        status.append(OffListModel.MAX_RESULTS);
      }
      else {
        status.append(listModel.getSize());
      }

      status.append(" files");
      statusBar.setText(status.toString());
    }
    catch (Exception e) {
      logger.log(Level.WARNING, "Error during search", e);
    }
  }

  @Override
  public void setSearchSuccess(boolean success) {
    if (success) {
      patternInput.setSearchSuccess(true);
      resultsList.setSelectedIndex(0);
    }
    else {
      patternInput.setSearchSuccess(false);
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
      ide.closeWindow();
      e.consume();
    }
  }

  @Override
  public void keyTyped(KeyEvent e) { }

  @Override
  public void keyReleased(KeyEvent e) { }

  public void close() {
    patternInput.close();
  }

  class SearchAction implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      search();
      timer = null;
    }

  }

}
