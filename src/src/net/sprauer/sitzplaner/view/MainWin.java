package net.sprauer.sitzplaner.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.sprauer.sitzplaner.view.panels.StatisticsPanel;
import net.sprauer.sitzplaner.view.panels.ToolsPanel;

public class MainWin extends JFrame {

	private static final long serialVersionUID = 885528719398690039L;
	private final JPanel clsRoomPanel;
	private final ToolsPanel toolsPanel;

	public MainWin() {
		super("Sitzplaner");
		getContentPane().setLayout(new BorderLayout(0, 0));
		clsRoomPanel = new JPanel();
		getContentPane().add(clsRoomPanel, BorderLayout.CENTER);
		clsRoomPanel.setLayout(new BorderLayout(8, 8));

		toolsPanel = new ToolsPanel();
		getContentPane().add(toolsPanel, BorderLayout.NORTH);

		StatisticsPanel statisticsPanel = new StatisticsPanel();
		getContentPane().add(statisticsPanel, BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void initView() {
		clsRoomPanel.add(ClassRoom.instance(), BorderLayout.CENTER);
		toolsPanel.initView();
		pack();
		setVisible(true);

	}
}
