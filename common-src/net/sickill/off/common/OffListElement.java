/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off.common;

import java.util.regex.Matcher;
import javax.swing.Icon;

/**
 *
 * @author kill
 */
public class OffListElement {
	ProjectFile file;
	Matcher matcher;
	String highlightedText;
	String label;
    int priority = 0;

	public OffListElement(Matcher matcher, ProjectFile file, String label) {
		this.matcher = matcher;
		this.file = file;
		this.label = label;
	}

	public ProjectFile getFile() {
		return file;
	}

	public String getHighlightedText() {
		return highlightedText;
	}

	public String getLabel() {
		return label;
	}

	public Icon getIcon() {
		return file.getIcon();
	}

    int getFilterDistance() {
        int dist = 0;
        for (int i=1; i<matcher.groupCount(); i++) {
            dist += matcher.start(i+1) - matcher.start(i) - 1;
        }
        return dist;
    }

    void setPriority(int priority) {
        this.priority = priority;
    }

    int getPriority() {
        return this.priority;
    }

    public String toString() {
        return getLabel();
    }
}
