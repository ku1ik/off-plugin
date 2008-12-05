/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.taz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author kill
 */
public class TazListModel extends AbstractListModel implements ListDataListener {
	private static final long serialVersionUID = 7121724322112004624L;

	//private Collection<VPTNode> projectFiles;
	//private VPTProject project;
	private List<TazListElement> matchedFiles;
	private String filter;
	private boolean emptyFilter;
    private ProjectFilesProvider projectFilesProvider;
    private Settings settings;

	// {{{ FilteredTableModel() constructor
	protected TazListModel(Settings s, ProjectFilesProvider projectFilesProvider) {
        this.settings = s;
        this.projectFilesProvider = projectFilesProvider;
		resetFilter();
	} // }}}

/*	private Collection<VPTNode> getProjectFiles() {
		setProject(ProjectViewer.getActiveProject(jEdit.getActiveView()));
		return projectFiles;
	}
*/
/*	public void setProject(VPTProject project) {
		this.project = project;
		if (project != null) {
			this.projectFiles = project.getOpenableNodes();
		} else {
			this.projectFiles = new ArrayList<VPTNode>();
		}
	}
*/
	// {{{ setList() method
	/**
	 * Set the JList that uses this model. It is used to restore the selection
	 * after the filter has been applied If it is null,
	 *
	 * @param list
	 *            the list that uses the model
	 */
/*	public void setList(JList list) {
		if (list.getModel() != this)
			throw new IllegalArgumentException("The given list " + list
					+ " doesn't use this model " + this);
		this.list = list;
	} // }}}
*/
	// {{{ resetFilter() method
	private void resetFilter() {
		this.filter = null;
		matchedFiles = new ArrayList<TazListElement>();
		this.emptyFilter = true;
	} // }}}

	public void setFilter(final String filter) {
		try {
			this.filter = filter;
			matchedFiles = new ArrayList<TazListElement>();
			if (filter != null && filter.length() > 0 && !filter.equals("*")) {
				Pattern preparedFilter = prepareFilter(filter);
				for (ProjectFile file : projectFilesProvider.getProjectFiles()) {
					passFilter(file, preparedFilter);
				}

				// sort
				if (settings.isSortResults()) {
					Collections.sort(matchedFiles, new CaseInsensitiveComparator());
				}
				// group
				if (settings.isGroupResults()) {
					Collections.sort(matchedFiles, new FileGroupComparator());
				}
			} else {
				resetFilter();
			}

			fireContentsChanged(this, 0, getSize());
		} catch (Exception e) {
			//Log.log(Log.ERROR, this.getClass(), e);
		}
	} // }}}

	// {{{ prepareFilter() method
	private Pattern prepareFilter(String filter) {
		//Log.log(Log.DEBUG, this.getClass(), "filter: " + filter);
		this.emptyFilter = false;
		filter = filter.toLowerCase();
		String regexFilter;
		if (settings.isSmartMatch()) {
			String[] chars = filter.split("");
			regexFilter = "";
			for (String s : chars) {
				if (!s.equals("")) {
					regexFilter += "(" + s + ")" + "*";
				}
			}
		} else {
			regexFilter = filter + "*";
		}
		regexFilter = regexFilter.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*?");
		return Pattern.compile(regexFilter);
	}

	// {{{ passFilter() method
	private void passFilter(ProjectFile file, Pattern regex) {
		if (this.emptyFilter) {
			// Log.log(Log.DEBUG, this.getClass(), "passFilter: false(empty)");
			return;
		}

		Matcher matcher = regex.matcher(file.getName().toLowerCase());
		if (matcher.matches()) {
			String label = file.getName();
			if (!settings.isShowExt()) {
				label = label.replaceFirst("\\.[^\\.]+$", "");
			}
			if (settings.isShowPath()) {
				String pathInProject = file.getPathInProject();
				if (!pathInProject.equals("")) {
					label += " [" + pathInProject + "]";
				}
			}
			if (settings.isShowSize()) {
				label += " - " + formatSize(file.getSize());
			}
			TazListElement e = new TazListElement(matcher, file, label);
			matchedFiles.add(e);
		}
	}

	private String formatSize(long size) {
		if (size < 1024) { // in bytes
			return ""+size+" B";
		} else if (size < 1024*1024) { // in kilobytes
			return ""+String.format("%.1f", size/1024.0)+" KB";
		} else { // in megabytes
			return ""+String.format("%.1f", size/(1024.0*1024.0))+" MB";
		}
	}

	// }}}

	// {{{ getElementAt() method
	public Object getElementAt(int index) {
		return matchedFiles.get(index);
	} // }}}

	// {{{ getSize() method
	public int getSize() {
		return matchedFiles.size();
	} // }}}

	// {{{ contentsChanged() method
	public void contentsChanged(ListDataEvent e) {
		setFilter(filter);
	} // }}}

	// {{{ intervalAdded() method
	public void intervalAdded(ListDataEvent e) {
		setFilter(filter);
	} // }}}

	// {{{ intervalRemoved() method
	public void intervalRemoved(ListDataEvent e) {
		setFilter(filter);
	} // }}}

	class CaseInsensitiveComparator implements Comparator<TazListElement> {
		public int compare(TazListElement o1, TazListElement o2) {
			return o1.getLabel().toLowerCase().compareTo(
					o2.getLabel().toLowerCase());
		}
	}

	class FileGroupComparator implements Comparator<TazListElement> {
		public int compare(TazListElement o1, TazListElement o2) {
			//FileGroup fg1 = o1.getFile().getFileGroup();
			//FileGroup fg2 = o2.getFile().getFileGroup();
			//int a = fg1 != null ? fg1.getOrder() : -1000;
			//int b = fg2 != null ? fg2.getOrder() : -1000;
			//return (a > b ? -1 : (a == b) ? 0 : 1);
            return 0;
		}
	}
}
