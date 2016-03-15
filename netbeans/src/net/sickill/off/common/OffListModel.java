package net.sickill.off.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import javax.swing.AbstractListModel;
import org.openide.filesystems.FileObject;

/**
 * @author sickill
 */
public class OffListModel extends AbstractListModel<OffListElement> {

    private static final long serialVersionUID = 7121724322112004624L;
    private static final Logger logger = Logger.getLogger(OffListModel.class.getName());

    public static int MAX_RESULTS = 50;

    private Set<ProjectFile> allFiles;
    private String projectRootDir;
    private Collection<String> sourceGroups;
    private List<OffListElement> matchingFiles;
    private Filter filter;
    private Settings settings;
    private IndexingListener indexingListener;
    private SearchStatusListener statusListener;
    private Map<String, Integer> accessFrequency = new HashMap<>();
    private boolean isIndexing = false;
    private final Object mutex = new Object();

    public OffListModel(Settings s) {
        settings = s;
        filter = null;
        clear();
    }

    public void reinit(Collection<String> sourceGroups, String projectRootDir) {
        clear();
        this.sourceGroups.clear();
        this.sourceGroups.addAll(sourceGroups);
        this.projectRootDir = projectRootDir;
    }

    void setIndexingListener(IndexingListener indexingListener) {
        this.indexingListener = indexingListener;
    }

    void setStatusListener(SearchStatusListener statusListener) {
        this.statusListener = statusListener;
    }

    public void clear() {
        synchronized (mutex) {
            allFiles = new LinkedHashSet<>();
            sourceGroups = new ArrayList<>();
        }

        reset();
    }

    public void setIndexing(boolean indexing) {
        isIndexing = indexing;

        if (indexingListener != null) {
            indexingListener.setIndexing(indexing);
        }
    }

    public boolean isIndexing() {
        return isIndexing;
    }

    private List<OffListElement> removeDuplicates(List<OffListElement> matchingFiles) {
        //OffListElement implements hashCode, so we sort out duplicates
        return new ArrayList<>(new LinkedHashSet<>(matchingFiles));
    }

    private void reset() {
        matchingFiles = new ArrayList<>();
    }

    public void addFile(ProjectFile pf) {
        synchronized (mutex) {
            if (!allFiles.contains(pf)) {
                if (pf.getFullPath().startsWith(projectRootDir)) {
                    String fileRelativeToProjectRoot = pf.getFullPath().substring(projectRootDir.length());

                    if (!settings.getIgnoreWildcard().matches(fileRelativeToProjectRoot)) {
                        allFiles.add(pf);
                    }
                }
            }
        }
    }

    public void removeFile(FileObject fileObject) {
        synchronized (mutex) {
            for (ProjectFile f : allFiles) {
                if (f.getFullPath().equals(fileObject.getPath())) {
                    allFiles.remove(f);
                }
            }
        }
    }

    public void setFilter(final String f) {
        if (filter != null && f.equals(filter.toString())) {
            return;
        }

        if (f.length() < settings.getMinPatternLength()) {
            filter = null;
            reset();
            fireContentsChanged(this, 0, getSize());
            return;
        }

        filter = new Filter(f, settings);
    }

    public void refilter() {
        reset();

        if (filter != null) {
            logger.info("[OFF] refiltering model");

            String filterStr = filter.toString();
            boolean withPath = filterStr.contains("/") || filterStr.contains("\\");

            synchronized (mutex) {
                for (String sourceGroup : sourceGroups) {
                    String sourceRoot = sourceGroup.toLowerCase();
                    final int sourceRootLength = sourceRoot.length();
                    for (ProjectFile file : allFiles) {
                        /**
                         * <pre>
                         * projectroot = c:/projects/myproject/
                         * sourceroot  = c:/projects/myproject/src/main/java
                         * indexed file= c:/projects/myproject/src/main/java/com/foo/Bar.java
                         * searchText  =                                     c  /f  /Bar.java
                         * </pre> indexedFile minus sourceroot=com/foo/Bar.java
                         */
                        String indexedFile = file.getFullPath().toLowerCase();
                        // Is sourceRoot a candidate for filtering? c:/projects/myproject/src/main/java
                        if (indexedFile.startsWith(sourceRoot)) {
                            //extract relevant part: com/foo/Bar.java
                            String fileInSourceRoot = indexedFile.substring(sourceRootLength);
                            passFilter(file, withPath, withPath ? fileInSourceRoot : file.getFileName().toLowerCase(), projectRootDir);
                        }
                    }
                }
            }
            matchingFiles = removeDuplicates(matchingFiles);

            // sort by filename
            if (settings.isNameSorting()) {
                Collections.sort(matchingFiles, new FileNameComparator());
            }

            // sort by file extension
            if (settings.isExtensionSorting()) {
                Collections.sort(matchingFiles, new FileExtensionComparator());
            }

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

        if (statusListener != null) {
            statusListener.setSearchSuccess(getSize() > 0);
        }
    }

    private void passFilter(ProjectFile file, boolean withPath, String fileInSourceRoot, String projectRoot) {
        Matcher matcher = filter.matcher(fileInSourceRoot);

        if (matcher.matches()) {

            String fileInProjectRoot = file.getFullPath().substring(projectRoot.length());
            OffListElement e = new OffListElement(matcher, file, withPath, fileInProjectRoot);

            // lower priority
            if (!settings.getLessPriorityMask().isEmpty()) {
                if (settings.getLessPriorityWildcard().matches(fileInProjectRoot)) {
                    e.setPriority(-1);
                }
            }

            // higher priority
            if (!settings.getMorePriorityMask().isEmpty()) {
                if (settings.getMorePriorityWildcard().matches(fileInProjectRoot)) {
                    e.setPriority(1);
                }
            }

            matchingFiles.add(e);
        }
    }

    @Override
    public OffListElement getElementAt(int index) {
        if (index >= 0 && index < matchingFiles.size()) {
            return matchingFiles.get(index);
        }
        return null;
    }

    @Override
    public int getSize() {
        return matchingFiles.size() < MAX_RESULTS ? matchingFiles.size() : MAX_RESULTS;
    }

    public void incrementAccessCounter(String path) {

        if (accessFrequency.containsKey(path)) {
            accessFrequency.put(path, accessFrequency.get(path) + 1);
        } else {
            accessFrequency.put(path, 1);
        }
    }

    private int getPopularity(String fullPath) {
        Integer p = accessFrequency.get(fullPath);
        return p == null ? 0 : p;
    }

    class FileNameComparator implements Comparator<OffListElement> {

        @Override
        public int compare(OffListElement o1, OffListElement o2) {
            return o1.getPlainFileName().toLowerCase().compareTo(
                    o2.getPlainFileName().toLowerCase()
            );
        }

    }

    class FileExtensionComparator implements Comparator<OffListElement> {

        @Override
        public int compare(OffListElement o1, OffListElement o2) {
            return getExtension(o1).compareToIgnoreCase(getExtension(o2));
        }

        String getExtension(String fileNameWithExt) {
            String name = fileNameWithExt;
            int index = name.lastIndexOf('.');
            return index == -1 ? "" : name.substring(index + 1, name.length());
        }

        String getExtension(OffListElement o1) {
            return getExtension(o1.getPlainFileName()).toLowerCase();
        }

    }

    class PopularityComparator implements Comparator<OffListElement> {

        @Override
        public int compare(OffListElement o1, OffListElement o2) {
            int a = getPopularity(o1.getFullPath());
            int b = getPopularity(o2.getFullPath());
            return a > b ? -1 : (a == b) ? 0 : 1;
        }

    }

    class DistanceComparator implements Comparator<OffListElement> {

        @Override
        public int compare(OffListElement o1, OffListElement o2) {
            int a = o1.getFilterDistance();
            int b = o2.getFilterDistance();
            return a < b ? -1 : (a == b) ? 0 : 1;
        }

    }

    class PriorityComparator implements Comparator<OffListElement> {

        @Override
        public int compare(OffListElement o1, OffListElement o2) {
            int a = o1.getPriority();
            int b = o2.getPriority();
            return a > b ? -1 : (a == b) ? 0 : 1;
        }

    }

}
