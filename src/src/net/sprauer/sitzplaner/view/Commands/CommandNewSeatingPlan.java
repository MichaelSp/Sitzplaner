package net.sprauer.sitzplaner.view.Commands;

import java.awt.event.ActionEvent;

import net.sprauer.sitzplaner.EA.ClassRoomToSmallException;
import net.sprauer.sitzplaner.EA.EAFactory;
import net.sprauer.sitzplaner.EA.GeneString;
import net.sprauer.sitzplaner.view.ClassRoom;

public class CommandNewSeatingPlan extends AbstractCommand {
	private static final long serialVersionUID = -2671082852870438865L;

	CommandNewSeatingPlan(ClassRoom classView, GeneString classModel) {
		super(classView, classModel);
		putValue(NAME, "Neue Sitzordnung");
		putValue(SHORT_DESCRIPTION, "Neu berechnen");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		GeneString gen;
		try {
			EAFactory.greedy(classModel);
			EAFactory.optimize(classModel);
			classView.setModel(classModel);
		} catch (ClassRoomToSmallException e1) {
			warningBox("The class room is to small for all students.", "MainWindow");
		}
	}
}
