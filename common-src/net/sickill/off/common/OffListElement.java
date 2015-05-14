package net.sickill.off.common;

import java.util.regex.Matcher;
import javax.swing.Icon;

/**
 *
 * @author kill
 */
public class OffListElement {
	String filename, path;
	ProjectFile file;
	Matcher matcher;
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
		String fn = file.getName();
		String label_ = "";
		int lastStart = 0;
		for (int i=1; i<=matcher.groupCount(); i++) {
			label_ += fn.substring(lastStart, matcher.start(i)) + "<b>" + fn.charAt(matcher.start(i)) + "</b>";
			lastStart = matcher.end(i);
		}
		label_ += fn.substring(lastStart);
		return label_;
	}

	private void buildFilename() {
		filename = withPath ? file.getName() : generateHighlightedLabel();
	}

	public String getFilename() {
		if (filename == null) {
			buildFilename();
		}
		return "<html>" + filename + "</html>";
	}

	private void buildPath() {
		path = file.getDirectory();
		if (!path.equals("")) {
			path = path.substring(0, path.length()-1) + " ";
		}
	}

	public String getPath() {
		if (path == null) {
			buildPath();
		}
		return path;
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
		return getFilename();
	}
}
