/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author kill
 */
public class OffListModel extends AbstractListModel implements ListDataListener {
	private static final long serialVersionUID = 7121724322112004624L;
	private List<OffListElement> matchedFiles;
	private String filter;
	private boolean emptyFilter;
    private ProjectProvider projectFilesProvider;
    private Settings settings;
    private OffPanel taz;

	protected OffListModel(Settings s, ProjectProvider projectFilesProvider) {
        this.settings = s;
        this.projectFilesProvider = projectFilesProvider;
		resetFilter();
	}

    public void setProjectFilesProvider(ProjectProvider pfp) {
        this.projectFilesProvider = pfp;
    }

	private void resetFilter() {
		this.filter = null;
		matchedFiles = new ArrayList<OffListElement>();
		this.emptyFilter = true;
	}

	public void setFilter(final String filter) {
//		try {
			this.filter = filter;
			matchedFiles = new ArrayList<OffListElement>();
			if (filter != null && filter.length() > 0 && !filter.equals("*")) {
				Pattern preparedFilter = prepareFilter(filter);
				for (ProjectFile file : projectFilesProvider.getProjectFiles()) {
					String name = filter.indexOf("/") == -1 ? file.getName().toLowerCase() : file.getFullPath().toLowerCase();
					passFilter(preparedFilter, name, file);
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

            Logger logger = Logger.getLogger(OffListModel.class.getName());
            logger.log(Level.INFO, "projectFiles = " + projectFilesProvider.getProjectFiles());
            logger.log(Level.INFO, "matchedFiles = " + matchedFiles);

			fireContentsChanged(this, 0, getSize());
//		} catch (Exception e) {
//            Logger logger = Logger.getLogger(TazListModel.class.getName());
//            logger.log(Level.SEVERE, e.g);
			//Log.log(Log.ERROR, this.getClass(), e);
//		}
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
	private void passFilter(Pattern regex, String name, ProjectFile file) {
		if (this.emptyFilter) {
			// Log.log(Log.DEBUG, this.getClass(), "passFilter: false(empty)");
			return;
		}

		Matcher matcher = regex.matcher(name);
		if (matcher.matches()) {
			String label = file.getName();
			if (!settings.isShowExt()) {
				label = label.replaceFirst("\\.[^\\.]+$", "");
			}
			if (settings.isShowPath()) {
				String pathInProject = file.getDirectory();
				if (!pathInProject.equals("")) {
					label += " [" + pathInProject + "]";
				}
			}
			if (settings.isShowSize()) {
				label += " - " + formatSize(file.getSize());
			}
			OffListElement e = new OffListElement(matcher, file, label);
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

	class CaseInsensitiveComparator implements Comparator<OffListElement> {
		public int compare(OffListElement o1, OffListElement o2) {
			return o1.getLabel().toLowerCase().compareTo(
					o2.getLabel().toLowerCase());
		}
	}

	class FileGroupComparator implements Comparator<OffListElement> {
		public int compare(OffListElement o1, OffListElement o2) {
			//FileGroup fg1 = o1.getFile().getFileGroup();
			//FileGroup fg2 = o2.getFile().getFileGroup();
			//int a = fg1 != null ? fg1.getOrder() : -1000;
			//int b = fg2 != null ? fg2.getOrder() : -1000;
			//return (a > b ? -1 : (a == b) ? 0 : 1);
            return 0;
		}
	}
}
