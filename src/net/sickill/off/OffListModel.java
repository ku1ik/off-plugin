package net.sickill.off;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
    private HashMap<String, Integer> accessFrequency = new HashMap<String, Integer>();

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
        this.filter = filter;
        matchedFiles = new ArrayList<OffListElement>();
        if (filter != null && filter.length() > 0 && !filter.equals("*")) {
            Pattern mask = settings.getIgnoreMask();
            Pattern regexp = prepareRegexp(filter);
            for (ProjectFile file : projectFilesProvider.getProjectFiles()) {
                String name = filter.indexOf("/") == -1 ? file.getName().toLowerCase() : file.getPathInProject().toLowerCase();
                passFilter(regexp, mask, name, file);
            }

            // sort by filename
            if (settings.isNameSorting()) {
                Collections.sort(matchedFiles, new FileNameComparator());
            }

            // sort by file extension
            if (settings.isExtensionSorting()) {
                Collections.sort(matchedFiles, new FileExtensionComparator());
            }

            // custom sorting/grouping (ie. JEdit's project viewer groups)
            if (settings.isCustomSorting()) {
                ///Collections.sort(matchedFiles, new FileGroupComparator());
            }

            // sort by match distance if smart matching
            if (settings.isSmartMatch() && settings.isDistanceSorting()) {
                Collections.sort(matchedFiles, new DistanceComparator());
            }

            // sort by access frequency
            if (settings.isPopularitySorting()) {
                Collections.sort(matchedFiles, new PopularityComparator());
            }
        } else {
            resetFilter();
        }

        fireContentsChanged(this, 0, getSize());
	}

	private Pattern prepareRegexp(String filter) {
		this.emptyFilter = false;
		filter = filter.toLowerCase().replaceAll("\\*{2,}", "*");
		String regex;
		if (settings.isSmartMatch()) {
			String[] chars = filter.split("");
			regex = "";
			for (String c : chars) {
				if (!c.equals("")) {
                    if (c.equals("."))
                        c = "\\.";

                    if (c.equals("*")) {
                        regex += ".*?";
                    } else {
                        regex += c + "([^\\/]*?)";
                    }
				}
			}
		} else {
			regex = (filter + "*").replaceAll("\\.", "\\\\.").replaceAll("\\*", "[^\\/]*?");
		}
		return Pattern.compile(regex);
	}

	private void passFilter(Pattern regex, Pattern mask, String name, ProjectFile file) {
		if (this.emptyFilter || (mask != null && mask.matcher(file.getPathInProject().toLowerCase()).matches())) {
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
            
            //label += " {"+ getPopularity(file.getFullPath()) +"}";
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

	public Object getElementAt(int index) {
		return matchedFiles.get(index);
	}

	public int getSize() {
		return matchedFiles.size();
	}

	public void contentsChanged(ListDataEvent e) {
		setFilter(filter);
	}

	public void intervalAdded(ListDataEvent e) {
		setFilter(filter);
	}

	public void intervalRemoved(ListDataEvent e) {
		setFilter(filter);
	}

    void incrementAccessCounter(ProjectFile pf) {
        String path = pf.getFullPath();
        if (accessFrequency.containsKey(path)) {
            accessFrequency.put(path, accessFrequency.get(path)+1);
        } else {
            accessFrequency.put(path, 1);
        }
    }

    private int getPopularity(String fullPath) {
        Integer p = accessFrequency.get(fullPath);
        return p == null ? 0 : p;
    }

	class FileNameComparator implements Comparator<OffListElement> {
		public int compare(OffListElement o1, OffListElement o2) {
			return o1.getLabel().toLowerCase().compareTo(
					o2.getLabel().toLowerCase());
		}
	}

	class FileExtensionComparator implements Comparator<OffListElement> {
		public int compare(OffListElement o1, OffListElement o2) {
			return o1.getFile().getExtension().toLowerCase().compareTo(
					o2.getFile().getExtension().toLowerCase());
		}
	}

	class PopularityComparator implements Comparator<OffListElement> {
		public int compare(OffListElement o1, OffListElement o2) {
			int a = getPopularity(o1.getFile().getFullPath());
			int b = getPopularity(o2.getFile().getFullPath());
			return (a > b ? -1 : (a == b) ? 0 : 1);
		}
	}

    class DistanceComparator implements Comparator<OffListElement> {
		public int compare(OffListElement o1, OffListElement o2) {
			int a = o1.getFilterDistance();
			int b = o2.getFilterDistance();
			return (a < b ? -1 : (a == b) ? 0 : 1);
		}
	}
}
