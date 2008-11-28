/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.sickill.taz;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 *
 * @author kill
 */
public class TazCellRenderer extends DefaultListCellRenderer {
	private static final long serialVersionUID = 4562757887321715315L;

	/**
	 * Gets the listCellRendererComponent attribute of the TazCellRenderer
	 * object
	 */
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		Component comp = super.getListCellRendererComponent(list, value, index,
				isSelected, cellHasFocus);
		TazListElement e = (TazListElement) value;
		((JLabel) comp).setText(e.getLabel());
		//if (TazPlugin.isShowIcon()) {
			((JLabel)comp).setIcon(e.getIcon());
		//}
		return comp;
	}

}// End of class TazCellRenderer
