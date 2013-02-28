package net.sprauer.sitzplaner;

import java.awt.BorderLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.sprauer.sitzplaner.view.ClassRoom;
import net.sprauer.sitzplaner.view.Commands.CommandFactory;

public class MainWin extends JFrame {

	private static final long serialVersionUID = 885528719398690039L;
	private final JPanel clsRoomPanel;
	private final JPanel toolsPanel;
	private final JButton btnNewSeatingPlan;
	private final JButton btnLoadClass;
	private final JButton btnSaveClass;

	MainWin() {
		super("Sitzplaner");
		clsRoomPanel = new JPanel();
		getContentPane().add(clsRoomPanel, BorderLayout.CENTER);
		clsRoomPanel.setLayout(new BorderLayout(0, 0));

		toolsPanel = new JPanel();
		clsRoomPanel.add(toolsPanel, BorderLayout.NORTH);

		btnLoadClass = new JButton();
		toolsPanel.add(btnLoadClass);

		btnSaveClass = new JButton();
		toolsPanel.add(btnSaveClass);

		btnNewSeatingPlan = new JButton();
		toolsPanel.add(btnNewSeatingPlan);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	void initView(ClassRoom classRoom) {
		clsRoomPanel.add(classRoom, BorderLayout.CENTER);
		btnNewSeatingPlan.setAction(CommandFactory.CommandNewSeatingPlan);
		btnLoadClass.setAction(CommandFactory.CommandLoadClass);
		btnSaveClass.setAction(CommandFactory.CommandSaveClass);

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
