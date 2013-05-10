package net.sprauer.sitzplaner.view.panels;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;

import net.sprauer.sitzplaner.EA.Configuration;

public class SettingsCellRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 4745935496614578652L;

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		final Configuration set = (Configuration) value;

		label.setText(set.toString());
		label.setIcon(new Icon() {

			@Override
			public void paintIcon(Component c, Graphics g, int x, int y) {
				final int border = 2;
				g.setColor(set.getColor());
				g.fillRect(border, border, getIconWidth() - 2 * border, getIconHeight() - 2 * border);
			}

			@Override
			public int getIconWidth() {
				return 20;
			}

			@Override
			public int getIconHeight() {
				return 20;
			}
		});
		return this;
	}
}
