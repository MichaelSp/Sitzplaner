package net.sprauer.sitzplaner.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

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
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 150, 500, 0 };
		gridBagLayout.rowHeights = new int[] { 97, 28, 248, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 10.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		toolsPanel = new ToolsPanel();
		GridBagConstraints gbc_toolsPanel = new GridBagConstraints();
		gbc_toolsPanel.gridwidth = 2;
		gbc_toolsPanel.anchor = GridBagConstraints.NORTHWEST;
		gbc_toolsPanel.insets = new Insets(6, 6, 6, 6);
		gbc_toolsPanel.gridx = 0;
		gbc_toolsPanel.gridy = 0;
		getContentPane().add(toolsPanel, gbc_toolsPanel);

		clsRoomPanel = new JPanel();
		GridBagConstraints gbc_clsRoomPanel = new GridBagConstraints();
		gbc_clsRoomPanel.gridwidth = 2;
		gbc_clsRoomPanel.fill = GridBagConstraints.BOTH;
		gbc_clsRoomPanel.insets = new Insets(6, 6, 6, 6);
		gbc_clsRoomPanel.gridx = 0;
		gbc_clsRoomPanel.gridy = 1;
		getContentPane().add(clsRoomPanel, gbc_clsRoomPanel);
		clsRoomPanel.setLayout(new BorderLayout(8, 8));

		StatisticsPanel statisticsPanel = new StatisticsPanel();
		GridBagConstraints gbc_statisticsPanel = new GridBagConstraints();
		gbc_statisticsPanel.gridwidth = 2;
		gbc_statisticsPanel.weightx = 1.0;
		gbc_statisticsPanel.fill = GridBagConstraints.BOTH;
		gbc_statisticsPanel.insets = new Insets(6, 6, 6, 6);
		gbc_statisticsPanel.gridx = 0;
		gbc_statisticsPanel.gridy = 2;
		getContentPane().add(statisticsPanel, gbc_statisticsPanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void initView() {
		clsRoomPanel.add(ClassRoom.instance(), BorderLayout.CENTER);
		toolsPanel.initView();
		pack();
		setVisible(true);

	}
}
