/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.off;

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
}
