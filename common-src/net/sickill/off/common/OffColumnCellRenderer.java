package net.sickill.off.common;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author kill
 */
public class OffColumnCellRenderer extends JPanel implements ListCellRenderer {

	JLabel[] labels = { new JLabel(), new JLabel(), new JLabel() };

	public OffColumnCellRenderer() {
		setLayout(new BorderLayout(4, 0));
		setBorder(new EmptyBorder(1, 1, 1, 1));
		for (JLabel l : labels) l.setOpaque(true);
		add(labels[0], BorderLayout.WEST);
		add(labels[1], BorderLayout.CENTER);
		add(labels[2], BorderLayout.EAST);
	}

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		OffListElement e = (OffListElement)value;

		labels[0].setIcon(e.getIcon());
		labels[1].setText(e.getFilename());
		labels[2].setText(e.getPath());

		if (isSelected) {
			for (JLabel l : labels) {
				setBackground(list.getSelectionBackground());
				l.setBackground(list.getSelectionBackground());
				l.setForeground(list.getSelectionForeground());
			}
			labels[2].setForeground(list.getForeground());
		} else {
			for (JLabel l : labels) {
				setBackground(list.getBackground());
				l.setBackground(list.getBackground());
				l.setForeground(list.getForeground());
			}
			labels[2].setForeground(Color.GRAY);
		}

		setEnabled(list.isEnabled());
		setFont(list.getFont());
		return this;
	}
}
