package net.sickill.off.jedit.options;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.sickill.off.common.Settings;
import net.sickill.off.jedit.JEditSettings;
import org.gjt.sp.jedit.AbstractOptionPane;

// minimum pattern length (2)
// group files (using projectviewer groups)
// clear search field on open
// use tokens highlighting
// exact match (without adding *)
// search results: show icon/extension/directory/size

public class OffOptionsPane extends AbstractOptionPane {
	private static final long serialVersionUID = 3972719262788345594L;

	JTextField minPatternLength;
	JTextField searchDelay;
//	JCheckBox sortByExtension;
	JCheckBox clearOnOpen;
//	JCheckBox exactMatch;
	//JCheckBox tokensHighlighting;
	ButtonGroup matchMode;
	JRadioButton matchModeSmart, matchModeExact;
	
	public OffOptionsPane() {
		super("Open File Fast");
	}

	public void _init() {
        Settings s = JEditSettings.getInstance();
		minPatternLength = new JTextField("" + s.getMinPatternLength(), 2);
		searchDelay = new JTextField("" + s.getSearchDelay(), 3);
//		sortByExtension = new JCheckBox("Group files by extension");
//		sortByExtension.getModel().setSelected(s.isExtensionSorting());
		clearOnOpen = new JCheckBox("Clear input field on open");
		clearOnOpen.getModel().setSelected(s.isClearOnOpen());
		matchMode = new ButtonGroup();
		boolean smart = s.isSmartMatch();
		matchModeSmart = new JRadioButton("smart matching", smart);
		matchModeExact = new JRadioButton("exact matching", !smart);
		matchMode.add(matchModeSmart);
		matchMode.add(matchModeExact);
//		tokensHighlighting = new JCheckBox("");
//		showIcon = new JCheckBox("show icon");
//		showIcon.getModel().setSelected(s.isShowIcon());
//		showPath = new JCheckBox("show project path");
//		showPath.getModel().setSelected(s.isShowPath());
//		showExt = new JCheckBox("show file extension");
//		showExt.getModel().setSelected(s.isShowExt());
//		showSize = new JCheckBox("show file size");
//		showSize.getModel().setSelected(s.isShowSize());
		
		JPanel patternLengthPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0 , 0));
		patternLengthPanel.add(new JLabel("Minimum pattern length: "));
		patternLengthPanel.add(minPatternLength);
		addComponent(patternLengthPanel);
		
		JPanel delayPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0 , 0));
		delayPanel.add(new JLabel("Search delay (after last keystroke): "));
		delayPanel.add(searchDelay);
		addComponent(delayPanel);
		
		addComponent(clearOnOpen);
//		addComponent(sortFiles);
//		addComponent(sortByExtension);
		
		// group "mode"
		JPanel modePane = new JPanel(new GridLayout(0, 1));
		TitledBorder modeBorder = new TitledBorder("Matching mode:");
		modePane.setBorder(modeBorder);
		modePane.add(matchModeSmart);
		modePane.add(matchModeExact);
		addComponent(modePane, GridBagConstraints.HORIZONTAL);
		
		// group "show" 
//		JPanel showPane = new JPanel(new GridLayout(0, 1));
//		TitledBorder showBorder = new TitledBorder("On results list:");
//		showPane.setBorder(showBorder);
//		showPane.add(showIcon);
//		showPane.add(showPath);
//		showPane.add(showExt);
//		showPane.add(showSize);
//		addComponent(showPane, GridBagConstraints.HORIZONTAL);
	}
	
	public void _save() {
        Settings s = JEditSettings.getInstance();
        s.setMinPatternLength(Integer.parseInt(minPatternLength.getText()));
		s.setSearchDelay(Float.parseFloat(searchDelay.getText()));
//		s.setSortResults(sortFiles.isSelected());
//		s.setGroupResults(sortByExtension.isSelected());
		s.setClearOnOpen(clearOnOpen.isSelected());
		s.setSmartMatch(matchModeSmart.isSelected());
//		s.setShowIcon(showIcon.isSelected());
//		s.setShowPath(showPath.isSelected());
//		s.setShowExt(showExt.isSelected());
//		s.setShowSize(showSize.isSelected());
	}

} // End of TazOptionPane
