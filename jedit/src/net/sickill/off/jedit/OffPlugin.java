package net.sickill.off.jedit;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JDialog;
import net.sickill.off.common.IDE;
import net.sickill.off.common.OffPanel;
import net.sickill.off.common.Settings;
import org.gjt.sp.jedit.EditPlugin;
import org.gjt.sp.jedit.View;
import org.gjt.sp.jedit.gui.DefaultFocusComponent;
import org.gjt.sp.jedit.jEdit;


public class OffPlugin extends EditPlugin implements DefaultFocusComponent {
	public final static String NAME = "Taz";

	public static final String SMART_MATCH = "taz.smart-match";
	public static final String MIN_PATTERN_LENGTH = "taz.min-pattern-length";
	public static final String SORT_RESULTS = "taz.sort-results";
	public static final String GROUP_RESULTS = "taz.group-results";
	public static final String CLEAR_ON_OPEN = "taz.clear-on-open";
	public static final String SHOW_ICON = "taz.show-icon";
	public static final String SHOW_PATH = "taz.show-path";
	public static final String SHOW_EXT = "taz.show-ext";
	public static final String SHOW_SIZE = "taz.show-size";
	public static final String SEARCH_DELAY = "taz.search-delay";
	
	private static int minPatternLength;
	private static int searchDelay;
	private static boolean smartMatch;
	private static boolean sortResults;
	private static boolean groupResults;
	private static boolean clearOnOpen;
	private static boolean showIcon;
	private static boolean showPath;
	private static boolean showExt;
	private static boolean showSize;
	
//	public static HashMap<String, Object> settings = new HashMap<String, Object>();
    private static Settings settings = new JEditSettings();
	
	static Hashtable<View, OffPanel> viewsWithOff = new Hashtable<View, OffPanel>();
	static Hashtable<View, JEditDialog> dialogs = new Hashtable<View, JEditDialog>();
	static WindowAdapter windowAdapter;

	public void start() {
//		loadSettings();
		windowAdapter = new TazWindowAdapter();
		super.start();
	}

	public void stop() {
		Enumeration<View> iter = viewsWithOff.keys();
		while (iter.hasMoreElements()) {
			View v = (View) iter.nextElement();
			v.removeWindowListener(windowAdapter);
		}
		viewsWithOff.clear();
		super.stop();
	}
	
//	void loadSettings() {
//		setMinPatternLength(jEdit.getIntegerProperty(MIN_PATTERN_LENGTH));
//		setSearchDelay(jEdit.getIntegerProperty(SEARCH_DELAY));
//		setSmartMatch(jEdit.getBooleanProperty(SMART_MATCH));
//		setSortResults(jEdit.getBooleanProperty(SORT_RESULTS));
//		setGroupResults(jEdit.getBooleanProperty(GROUP_RESULTS));
//		setClearOnOpen(jEdit.getBooleanProperty(CLEAR_ON_OPEN));
//		setShowIcon(jEdit.getBooleanProperty(SHOW_ICON));
//		setShowPath(jEdit.getBooleanProperty(SHOW_PATH));
//		setShowExt(jEdit.getBooleanProperty(SHOW_EXT));
//		setShowSize(jEdit.getBooleanProperty(SHOW_SIZE));
//	}

	/**
	 * @param view
	 *            The View we activated Taz from.
	 * @return A lightweight JPanel wrapper around the Taz instance for this
	 *         View.
	 */
	public static OffPanel getOffInstance(View view) {
		OffPanel off = (OffPanel)viewsWithOff.get(view);
		if (off == null) {
            IDE ide = new JEditIDE();
			off = new OffPanel(ide, settings, new JEditProjectViewerProject(view));
			viewsWithOff.put(view, off);
			view.addWindowListener(windowAdapter);
		}
		return off;
	}

 	public static JDialog showDialog(View view) { // TODO: change return value to void
        JEditDialog dialog = (JEditDialog)dialogs.get(view);
		if (dialog == null) {
			dialog = new JEditDialog(view, getOffInstance(view));
			dialogs.put(view, dialog);
		}
        dialog.showDialog();
		return dialog;
	}


	public void focusOnDefaultComponent() {
//		patternInput.selectAll();
//		patternInput.requestFocus();

//	/*	if (dialog != null)
//			dialog.setVisible(true);*/
	}

	class TazWindowAdapter extends WindowAdapter {
        // fired when JEdit View is closed
		public void windowClosed(WindowEvent evt) {
            viewsWithOff.remove(evt.getWindow());
			JDialog dialog = (JDialog)dialogs.remove(evt.getWindow());
			if (dialog != null) {
				dialog.dispose();
				dialog = null;
				System.gc();
			}
		}
	}

//	public static int getMinPatternLength() {
//		return minPatternLength;
//	}
//
//	public static void setMinPatternLength(int minPatternLength) {
//		OffPlugin.minPatternLength = minPatternLength;
//		jEdit.setIntegerProperty(OffPlugin.MIN_PATTERN_LENGTH, minPatternLength);
//	}
//
//	public static int getSearchDelay() {
//		return searchDelay;
//	}
//
//	public static void setSearchDelay(int searchDelay) {
//		OffPlugin.searchDelay = searchDelay;
//		jEdit.setIntegerProperty(OffPlugin.SEARCH_DELAY, searchDelay);
//	}
//
//	public static boolean isSmartMatch() {
//		return smartMatch;
//	}
//
//	public static void setSmartMatch(boolean smartMatch) {
//		OffPlugin.smartMatch = smartMatch;
//		jEdit.setBooleanProperty(OffPlugin.SMART_MATCH, smartMatch);
//	}
//
//	public static boolean isSortResults() {
//		return sortResults;
//	}
//
//	public static void setSortResults(boolean sortResults) {
//		OffPlugin.sortResults = sortResults;
//		jEdit.setBooleanProperty(OffPlugin.SORT_RESULTS, sortResults);
//	}
//
//	public static boolean isGroupResults() {
//		return groupResults;
//	}
//
//	public static void setGroupResults(boolean groupResults) {
//		OffPlugin.groupResults = groupResults;
//		jEdit.setBooleanProperty(OffPlugin.GROUP_RESULTS, groupResults);
//	}
//
//	public static boolean isClearOnOpen() {
//		return clearOnOpen;
//	}
//
//	public static void setClearOnOpen(boolean clearOnOpen) {
//		OffPlugin.clearOnOpen = clearOnOpen;
//		jEdit.setBooleanProperty(OffPlugin.CLEAR_ON_OPEN, clearOnOpen);
//	}
//
//	public static boolean isShowIcon() {
//		return showIcon;
//	}
//
//	public static void setShowIcon(boolean showIcon) {
//		OffPlugin.showIcon = showIcon;
//		jEdit.setBooleanProperty(OffPlugin.SHOW_ICON, showIcon);
//	}
//
//	public static boolean isShowPath() {
//		return showPath;
//	}
//
//	public static void setShowPath(boolean showPath) {
//		OffPlugin.showPath = showPath;
//		jEdit.setBooleanProperty(OffPlugin.SHOW_PATH, showPath);
//	}
//
//	public static boolean isShowExt() {
//		return showExt;
//	}
//
//	public static void setShowExt(boolean showExt) {
//		OffPlugin.showExt = showExt;
//		jEdit.setBooleanProperty(OffPlugin.SHOW_EXT, showExt);
//	}
//
//	public static boolean isShowSize() {
//		return showSize;
//	}
//
//	public static void setShowSize(boolean showSize) {
//		OffPlugin.showSize = showSize;
//		jEdit.setBooleanProperty(OffPlugin.SHOW_SIZE, showSize);
//	}
}// End of class TazPlugin
