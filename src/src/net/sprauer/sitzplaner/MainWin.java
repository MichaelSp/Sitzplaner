package net.sprauer.sitzplaner;

import java.awt.BorderLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import net.sprauer.sitzplaner.view.ClassRoom;
import net.sprauer.sitzplaner.view.Commands.Factory;

public class MainWin extends JFrame {

	private static final long serialVersionUID = 885528719398690039L;
	private final JPanel clsRoomPanel;
	private final JPanel toolsPanel;
	private final JButton btnNewSeatingPlan;
	private final JButton btnLoadClass;
	private final JButton btnSaveClass;
	private final JSeparator separator;
	private final JSeparator separator_1;
	private final JButton btnNewClass;

	MainWin() {
		super("Sitzplaner");
		clsRoomPanel = new JPanel();
		getContentPane().add(clsRoomPanel, BorderLayout.CENTER);
		clsRoomPanel.setLayout(new BorderLayout(0, 0));

		toolsPanel = new JPanel();
		clsRoomPanel.add(toolsPanel, BorderLayout.NORTH);

		separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		toolsPanel.add(separator_1);

		btnNewClass = new JButton();
		toolsPanel.add(btnNewClass);

		btnLoadClass = new JButton();
		toolsPanel.add(btnLoadClass);

		btnSaveClass = new JButton();
		toolsPanel.add(btnSaveClass);

		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		toolsPanel.add(separator);

		btnNewSeatingPlan = new JButton();
		toolsPanel.add(btnNewSeatingPlan);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	void initView(ClassRoom classRoom) {
		clsRoomPanel.add(classRoom, BorderLayout.CENTER);
		btnNewSeatingPlan.setAction(Factory.CommandNewSeatingPlan);
		btnLoadClass.setAction(Factory.CommandLoadClass);
		btnSaveClass.setAction(Factory.CommandSaveClass);
		btnNewClass.setAction(Factory.CommandNewClass);

		pack();
		setVisible(true);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				btnNewSeatingPlan.doClick();
			}
		}, 200);
	}
}
