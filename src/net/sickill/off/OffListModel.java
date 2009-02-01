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
import javax.swing.JList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author kill
 */
public class OffListModel extends AbstractListModel { // implements ListDataListener {
	private static final long serialVersionUID = 7121724322112004624L;
	private List<OffListElement> matchingFiles;
	private Filter filter;
    private ProjectProvider projectFilesProvider;
    private Settings settings;
    private HashMap<String, Integer> accessFrequency = new HashMap<String, Integer>();
    private OffPanel off;

	protected OffListModel(Settings s, ProjectProvider projectFilesProvider) {
        this.settings = s;
        this.projectFilesProvider = projectFilesProvider;
        this.projectFilesProvider.setModel(this);
        this.filter = null;
        matchingFiles = new ArrayList<OffListElement>();
	}

    public void setProjectFilesProvider(ProjectProvider pfp) {
        this.projectFilesProvider = pfp;
    }

    public void setFilter(final String f) {
        this.filter = (f == null ? null : new Filter(f, settings));
        refresh();
    }

	public void refresh() {
        matchingFiles = new ArrayList<OffListElement>();
        if (filter != null) {
            boolean withPath = filter.toString().indexOf("/") != -1;
            for (ProjectFile file : projectFilesProvider.getProjectFiles()) {
                String name = withPath ? file.getPathInProject().toLowerCase() : file.getName().toLowerCase();
                passFilter(name, file);
            }

            // sort by filename
            if (settings.isNameSorting()) {
                Collections.sort(matchingFiles, new FileNameComparator());
            }

            // sort by file extension
            if (settings.isExtensionSorting()) {
                Collections.sort(matchingFiles, new FileExtensionComparator());
            }

            // custom sorting/grouping (ie. JEdit's project viewer groups)
//            if (settings.isCustomSorting()) {
                ///Collections.sort(matchedFiles, new FileGroupComparator());
//            }

            // sort by match distance if smart matching
            if (settings.isSmartMatch() && settings.isDistanceSorting()) {
                Collections.sort(matchingFiles, new DistanceComparator());
            }

            // sort by access frequency
            if (settings.isPopularitySorting()) {
                Collections.sort(matchingFiles, new PopularityComparator());
            }

            // put files on less-priority-mask to the bottom of list
            Collections.sort(matchingFiles, new PriorityComparator());
        }

        fireContentsChanged(this, 0, getSize());
	}

	private void passFilter(String name, ProjectFile file) {
		Matcher matcher = filter.matcher(name);
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
            if (!settings.getLessPriorityMask().equals("")) {
                if (settings.getLessPriorityMaskCompiled().matcher(file.getPathInProject().toLowerCase()).matches()) {
                    e.setPriority(-1);
                }
            }
			matchingFiles.add(e);
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
		return matchingFiles.get(index);
	}

	public int getSize() {
		return matchingFiles.size();
	}

/*	public void contentsChanged(ListDataEvent e) {
		setFilter(filter);
	}

	public void intervalAdded(ListDataEvent e) {
		setFilter(filter);
	}

	public void intervalRemoved(ListDataEvent e) {
		setFilter(filter);
	}
*/
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

	class PriorityComparator implements Comparator<OffListElement> {
		public int compare(OffListElement o1, OffListElement o2) {
            int a = o1.getPriority();
            int b = o2.getPriority();
			return (a > b ? -1 : (a == b) ? 0 : 1);
		}
	}
}
