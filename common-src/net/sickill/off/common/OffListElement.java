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
	String label;
	int priority = 0;
	boolean withPath;

	public OffListElement(Matcher matcher, ProjectFile file, boolean withPath) {
		this.matcher = matcher;
		this.file = file;
		this.withPath = withPath;
	}

	public ProjectFile getFile() {
		return file;
	}

	private String generateHighlightedLabel() {
		String filename = file.getName();
		String label_ = "";
		int lastStart = 0;
		for (int i=1; i<=matcher.groupCount(); i++) {
			label_ += filename.substring(lastStart, matcher.start(i)) + "<b>" + filename.charAt(matcher.start(i)) + "</b>";
			lastStart = matcher.end(i);
		}
		label_ += filename.substring(lastStart);
		return label_;
	}

	private void generateLabel() {
		label = withPath ? file.getName() : generateHighlightedLabel();
	//        label = file.getName();
	//        if (!settings.isShowExt()) {
	//            label = label.replaceFirst("\\.[^\\.]+$", "");
	//        }
	//        if (settings.isShowPath()) {
			String pathInProject = file.getDirectory();
			if (!pathInProject.equals("")) {
				label += " [" + pathInProject.substring(0, pathInProject.length()-1) + "]";
			}
	//        }
	//        if (settings.isShowSize()) {
	//            label += " - " + formatSize(file.getSize());
	//        }
	}

	public String getLabel() {
		if (label == null) {
			generateLabel();
		}
		return "<html>" + label + "</html>";
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
