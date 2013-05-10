package net.sprauer.sitzplaner.view.Commands;

import java.awt.event.ActionEvent;

import net.sprauer.sitzplaner.EA.EAFactory;
import net.sprauer.sitzplaner.exceptions.ClassNotLoadedException;
import net.sprauer.sitzplaner.exceptions.ClassRoomToSmallException;

public class CommandNewSeatingPlan extends AbstractCommand {
	private static final long serialVersionUID = -2671082852870438865L;

	@Override
	public void actionPerformed(ActionEvent e) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					EAFactory.resetGeneration();
					EAFactory.nextGenerations();
				} catch (ClassRoomToSmallException e1) {
					warningBox("The class room is to small.", "Sitzplaner - Class");
				} catch (ClassNotLoadedException e1) {

				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}).start();

	}

	@Override
	public String getButtonCaption() {
		return "Calculate";
	}

	@Override
	public String getToolTip() {
		return "Calculate a new seating plan for the given configuration.";
	}
}
