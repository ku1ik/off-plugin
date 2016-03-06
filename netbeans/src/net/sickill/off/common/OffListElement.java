package net.sickill.off.common;

import java.util.regex.Matcher;
import javax.swing.Icon;

/**
 * @author sickill
 */
public class OffListElement {

    String filenameAsHtml;
    Matcher matcher;
    int priority = 0;
    boolean withPath;
    private final String fileRelativeToProjectRoot;
    private String fileNamePlain;
    private String fullPath;
    private Icon icon;
    private int hashCode;

    public OffListElement(Matcher matcher, ProjectFile file, boolean withPath, String fileRelativeToProjectRoot) {
        this.icon = file.getIcon();
        this.fullPath = file.getFullPath();
        this.hashCode = fullPath.hashCode();
        this.fileNamePlain = file.getFileName();
        this.matcher = matcher;
        this.withPath = withPath;
        this.filenameAsHtml = withPath ? fileNamePlain : Highlighter.highlight(fileNamePlain, matcher);
        this.filenameAsHtml = "<html>" + filenameAsHtml + "</html>";
        this.fileRelativeToProjectRoot = fileRelativeToProjectRoot;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OffListElement other = (OffListElement) obj;
        if (this.hashCode != other.hashCode) {
            return false;
        }
        return true;
    }

    public String getFileRelativeToProjectRoot() {
        return fileRelativeToProjectRoot;
    }

    public String getPlainFileName() {
        return fileNamePlain;
    }

    public String getHTMLHighlightedFileName() {
        return filenameAsHtml;
    }

    public String getFullPath() {
        return fullPath;
    }

    public Icon getIcon() {
        return icon;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    int getFilterDistance() {
        int dist = 0;

        for (int i = 1; i < matcher.groupCount(); i++) {
            dist += matcher.start(i + 1) - matcher.start(i) - 1;
        }

        return dist;
    }

    void setPriority(int priority) {
        this.priority = priority;
    }

    int getPriority() {
        return this.priority;
    }

    @Override
    public String toString() {
        return getHTMLHighlightedFileName();
    }

}
