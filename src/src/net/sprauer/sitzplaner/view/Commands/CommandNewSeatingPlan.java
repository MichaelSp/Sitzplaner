package net.sprauer.sitzplaner.view.Commands;

import java.awt.event.ActionEvent;

import net.sprauer.sitzplaner.EA.EAFactory;
import net.sprauer.sitzplaner.exceptions.ClassNotLoadedException;
import net.sprauer.sitzplaner.exceptions.ClassRoomToSmallException;

public class CommandNewSeatingPlan extends AbstractCommand {
	private static final long serialVersionUID = -2671082852870438865L;

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			EAFactory.greedy(getModel());
			EAFactory.optimize(getModel());
			getView().setModel(getModel());
		} catch (ClassRoomToSmallException e1) {
			warningBox(e1.getMessage(), "Sitzplaner - Klasse");
		} catch (ClassNotLoadedException e1) {

		}
	}

	@Override
	public String getButtonCaption() {
		return "Neu berechnen";
	}

	@Override
	public String getToolTip() {
		return "Neue Sitzordnung aus den gegebenen Parametern berechnen";
	}
}
