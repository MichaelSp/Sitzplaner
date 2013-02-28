package net.sprauer.sitzplaner.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class Blackboard extends JPanel {

	private static final Color DEFAULT_BLACKBOARD_COLOR = new Color(0, 60, 0);
	private static final long serialVersionUID = -8750925029559567754L;

	public Blackboard() {
		setBackground(DEFAULT_BLACKBOARD_COLOR);
	}

	public void init(Dimension size) {
		setSize(size.width - Parameter.blackboardBorder - Parameter.legendWidth, Parameter.blackboardHeight);
		setLocation(Parameter.blackboardBorder / 2 + Parameter.legendWidth, size.height - Parameter.blackboardHeight);
	}

	public void highlight(boolean b) {
		setBackground(b ? Color.gray : DEFAULT_BLACKBOARD_COLOR);
	}

}